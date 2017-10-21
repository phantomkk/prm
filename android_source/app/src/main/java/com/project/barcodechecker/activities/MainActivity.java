package com.project.barcodechecker.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
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
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.fragments.BaseScannerActivity;
import com.project.barcodechecker.fragments.FragmentFactory;
import com.project.barcodechecker.fragments.HistoryFragment;
import com.project.barcodechecker.fragments.CategoryFragment;
import com.project.barcodechecker.fragments.ScanBlankFragment;
import com.project.barcodechecker.fragments.ScanFragment;
import com.project.barcodechecker.fragments.SearchFragment;
import com.project.barcodechecker.fragments.SettingFragment;
import com.project.barcodechecker.fragments.UserFragment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.utils.CoreManager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBack = (ImageButton) findViewById(R.id.btn_back_toolbar);
        mBack.setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.txt_toolbar_title);
        mTitle.setText("Scan Camera");
        mAvatar = (CircleImageView) findViewById(R.id.profile_image);
        mAvatar.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        setView();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        }
        viewPager.setCurrentItem(2);
        CoreManager.getUser(this);

    }




    private void setView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main_actv);
        bottomNavigationView.setSelectedItemId(R.id.action_scan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_history:
//                        selectedFragment = FragmentFactory.getFragment(HistoryFragment.class);
                        mTitle.setText("History");
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_search:
                        selectedFragment = FragmentFactory.getFragment(SearchFragment.class);
                        mTitle.setText("Search");
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_scan:
                        mTitle.setText("Scan Camera");
                        viewPager.setCurrentItem(2);
                        toolbar.setVisibility(View.VISIBLE);
                        mAvatar.setVisibility(View.GONE);
//                        selectedFragment = FragmentFactory.getFragment(ScanFragment.class);

                        break;
                    case R.id.action_list:
//                         selectedFragment = FragmentFactory.getFragment(CategoryFragment.class);
                        mTitle.setText("List category");
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.action_setting:
//                        selectedFragment = FragmentFactory.getFragment(SettingFragment.class);
                        mTitle.setText("Accout");
                        viewPager.setCurrentItem(4);
                        toolbar.setVisibility(View.GONE);
                        mAvatar.setVisibility(View.VISIBLE);
                        break;

                }
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.main_frame_main_actv, selectedFragment);
//                transaction.commit();
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        mTitle.setText("History");
                        mAvatar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mTitle.setText("Search");
                        mAvatar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mTitle.setText("Scan Camera");
                        mAvatar.setVisibility(View.GONE);
                        toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mTitle.setText("List category");
                        toolbar.setVisibility(View.VISIBLE);
                        mAvatar.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        mTitle.setText("Accout");
                        toolbar.setVisibility(View.GONE);
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


    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        historyFragment = HistoryFragment.newInstance();
        searchFragment = new SearchFragment();
        scanFragment = new ScanFragment();
        categoryFragment = new CategoryFragment();
        settingFragment = new SettingFragment();
        viewPagerAdapter.addFragment(historyFragment);
        viewPagerAdapter.addFragment(searchFragment);
        viewPagerAdapter.addFragment(scanFragment);
        viewPagerAdapter.addFragment(categoryFragment);
        viewPagerAdapter.addFragment(new UserFragment());
        viewPager.setAdapter(viewPagerAdapter);
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
}
