package com.project.barcodechecker.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.ViewPagerAdapter;
import com.project.barcodechecker.animation.DepthPageTransformer;
import com.project.barcodechecker.animation.ZoomOutPageTransformer;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.CommentService;
import com.project.barcodechecker.fragments.AccoutFragment;
import com.project.barcodechecker.fragments.BaseScannerActivity;
import com.project.barcodechecker.fragments.FragmentFactory;
import com.project.barcodechecker.fragments.HistoryFragment;
import com.project.barcodechecker.fragments.CategoryFragment;
import com.project.barcodechecker.fragments.ScanBlankFragment;
import com.project.barcodechecker.fragments.ScanFragment;
import com.project.barcodechecker.fragments.SearchFragment;
import com.project.barcodechecker.fragments.SettingFragment;
import com.project.barcodechecker.fragments.UserFragment;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AccoutFragment.OnLoginListener{
    private ProductService pService;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrame;
    private ActionBar actionbar;
    private Class<?> mClss;
    private android.support.v4.app.Fragment selectedFragment;
    Toolbar toolbar;
    ViewPager viewPager;
    private ProgressDialog progressDialog;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    HistoryFragment historyFragment;
    SearchFragment searchFragment;
    ScanFragment scanFragment;
    CategoryFragment categoryFragment;
    SettingFragment settingFragment;
    MenuItem prevMenuItem;
    ViewPagerAdapter viewPagerAdapter;
    ImageButton mBack;
    TextView mTitle;
    CircleImageView mAvatar;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBack = (ImageButton) findViewById(R.id.btn_back_toolbar);
        mBack.setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.txt_toolbar_title);
        mTitle.setText("");
        mAvatar = (CircleImageView) findViewById(R.id.profile_image);
        mAvatar.setVisibility(View.GONE);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        setupViewPager(viewPager);
        setView(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        }
        viewPager.setCurrentItem(2);
        u = CoreManager.getUser(this);
        if (u != null) {
            Picasso.with(this).load(u.getAvatar()).into(mAvatar);
        } else {
            mAvatar.setImageResource(R.drawable.avatar);
        }

    }


    private void setView(final Context context) {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main_actv);
        bottomNavigationView.setSelectedItemId(R.id.action_scan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_history:
                        mTitle.setText(R.string.tab_history);
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_search:
                        mTitle.setText(R.string.tab_search);
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_scan:
                        mTitle.setText("");
                        viewPager.setCurrentItem(2);
                        toolbar.setVisibility(View.VISIBLE);
                        mAvatar.setVisibility(View.GONE);
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case R.id.action_list:
                        mTitle.setText(R.string.tab_category);
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.action_setting:
                        User u = CoreManager.getUser(context);
                        if (u == null) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setMessage("Bạn cần phải đăng nhập để sử dụng tính năng này.")
                                    .setPositiveButton("Đăng nhập", positiveListener)
                                    .setNegativeButton("Hủy", null)
                                    .show();
                        } else {
                            if (!isLoginFirst) {
                                isLoginFirst=true;
                                mTitle.setText(R.string.tab_user);
                                viewPagerAdapter.addFragment(new UserFragment());
                                viewPagerAdapter.notifyDataSetChanged();
                            }
                            viewPager.setCurrentItem(4);
                            toolbar.setVisibility(View.GONE);
                            toolbar.setBackgroundResource(R.color.colorPrimary);
                            mAvatar.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                return false;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        mTitle.setText(R.string.tab_history);
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        break;
                    case 1:
                        mTitle.setText(R.string.tab_search);
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        break;
                    case 2:
                        mTitle.setText("");
                        mAvatar.setVisibility(View.GONE);
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mTitle.setText(R.string.tab_category);
                        toolbar.setVisibility(View.VISIBLE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        mAvatar.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        mTitle.setText(R.string.tab_user);
                        toolbar.setVisibility(View.GONE);
                        toolbar.setBackgroundResource(R.color.colorPrimary);
                        mAvatar.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Utils.USE_VIEW_DETAIL);
        }
    };
    private boolean isLoginFirst = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == Utils.USE_VIEW_DETAIL){
            isLoginFirst = true;
            u=CoreManager.getUser(this);
            Picasso.with(this).load(u.getAvatar()).into(mAvatar);
            mTitle.setText(R.string.tab_user);
            viewPagerAdapter.addFragment(new UserFragment());
            viewPagerAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(4);
            toolbar.setVisibility(View.GONE);
            mAvatar.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        historyFragment = HistoryFragment.newInstance();
        searchFragment = new SearchFragment();
        scanFragment = new ScanFragment();
        categoryFragment = new CategoryFragment();
        viewPagerAdapter.addFragment(historyFragment);
        viewPagerAdapter.addFragment(searchFragment);
        viewPagerAdapter.addFragment(scanFragment);
        viewPagerAdapter.addFragment(categoryFragment);
//        viewPagerAdapter.addFragment(new UserFragment());
        viewPager.setAdapter(viewPagerAdapter);
//        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner",
                            Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void destoyFragmentUser() {
        mAvatar.setImageResource(R.drawable.avatar);
        viewPagerAdapter.deleteFragment(4);
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(2);
        CoreManager.setUser(this, null);
    }

    @Override
    public void changeAvatar(Uri uri) {
        mAvatar.setImageURI(uri);
    }


}
