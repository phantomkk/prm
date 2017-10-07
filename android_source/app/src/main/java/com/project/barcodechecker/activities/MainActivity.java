package com.project.barcodechecker.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.fragments.HistoryFragment;
import com.project.barcodechecker.fragments.ListFragment;
import com.project.barcodechecker.fragments.ScanFragment;
import com.project.barcodechecker.fragments.SearchFragment;
import com.project.barcodechecker.fragments.SettingFragment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.utils.APIUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {
    private ProductService pService;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout mainFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        pService = APIUtils.getPService();
        pService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    String demo = "";
                    for (Product p : response.body()) {
                        demo = demo.concat(p.toString());
                    }
//                    ((TextView)findViewById(R.id.txt_show_products))
//                            .setText(demo);
                    Log.d("Log", demo);

                } else {
                    Log.d("ELSE", "Successful but else");
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("ERROR", "ERROR roi");
            }
        });
        //hello luc
    }

    private void setView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_main_actv);
        mainFrame = (FrameLayout) findViewById(R.id.main_frame_main_actv);
        bottomNavigationView.setSelectedItemId(R.id.action_scan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = ScanFragment.newInstance();
                switch (item.getItemId()) {
                    case R.id.action_history:
                        selectedFragment = HistoryFragment.newInstance();
                        break;
                    case R.id.action_search:
                        selectedFragment = SearchFragment.newInstance();
                        break;
                    case R.id.action_scan:
                        selectedFragment = ScanFragment.newInstance();
                        break;
                    case R.id.action_list:
                        selectedFragment = ListFragment.newInstance();
                        break;
                    case R.id.action_setting:
                        selectedFragment = SettingFragment.newInstance();
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
}