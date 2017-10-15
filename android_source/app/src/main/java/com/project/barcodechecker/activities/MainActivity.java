package com.project.barcodechecker.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.fragments.BaseScannerActivity;
import com.project.barcodechecker.fragments.FragmentFactory;
import com.project.barcodechecker.fragments.HistoryFragment;
import com.project.barcodechecker.fragments.CategoryFragment;
import com.project.barcodechecker.fragments.ScanBlankFragment;
import com.project.barcodechecker.fragments.ScanFragment;
import com.project.barcodechecker.fragments.SearchFragment;
import com.project.barcodechecker.fragments.SettingFragment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.APIServiceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private ProductService pService;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrame;
    private ActionBar actionbar;
    private Class<?> mClss;
    private static final int ZXING_CAMERA_PERMISSION = 1;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Scan");
        hideButtonBack(true);
        setView();
        pService = APIServiceManager.getPService();
        showLoading();
        pService.getProductById(10).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    String demo = response.body().getName() ;
                    Log.d("Log", demo);
                    Toast.makeText(MainActivity.this, demo, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ELSE", "Successful but else");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                hideLoading();
            }
        });
//        pService.getProducts().enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                if (response.isSuccessful()) {
//                    String demo = response.body().size() +"";
////                    for (Product p : response.body()) {
////                        demo = demo.concat(p.toString());
////                    }
////                    ((TextView)findViewById(R.id.txt_show_products))
////                            .setText(demo);
//                    Log.d("Log", demo);
//
//                } else {
//                    Log.d("ELSE", "Successful but else");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                Log.d("ERROR", "ERROR roi");
//            }
//        });
        //hello luc
    }


    private void setView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main_actv);
        mainFrame = (FrameLayout) findViewById(R.id.main_frame_main_actv);
        bottomNavigationView.setSelectedItemId(R.id.action_scan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = ScanBlankFragment.newInstance();
                switch (item.getItemId()) {
                    case R.id.action_history:
                        selectedFragment = FragmentFactory.getFragment(HistoryFragment.class);
                        getSupportActionBar().setTitle("History");
                        Log.v("LOG", "history");
                        break;
                    case R.id.action_search:
                        selectedFragment = FragmentFactory.getFragment(SearchFragment.class);
                        //setToolbarTitle("Search");
                        break;
                    case R.id.action_scan:
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            mClss = ScanBlankFragment.class;
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
                        } else {
                            mClss=ScanFragment.class;
                            selectedFragment = FragmentFactory.getFragment(mClss);
                           // setToolbarTitle("Scan");
                        }
                        break;
                    case R.id.action_list:
                        selectedFragment = FragmentFactory.getFragment(CategoryFragment.class);
                       // setToolbarTitle("List category");
                        break;
                    case R.id.action_setting:
                        selectedFragment = FragmentFactory.getFragment(SettingFragment.class);
                        //setToolbarTitle("Setting");
                        break;

                }
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_frame_main_actv, selectedFragment);
                transaction.commit();
                return false;
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        mClss = ScanFragment.class;
                        actionbar.setTitle("Scan");
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.main_frame_main_actv, FragmentFactory.getFragment(mClss));
                        transaction.commit();
                    }
                } else {
                    showMessage("Please grant camera permission to use the QR Scanner");
                }
                return;
        }
    }
}
