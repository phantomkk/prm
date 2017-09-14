package com.project.barcodechecker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.models.Product;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.APIUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {
    ProductService pService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pService = APIUtils.getPService();
        pService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    String demo = "";
                    for(Product p: response.body()){
                        demo = demo.concat(p.toString());
                    }
                    ((TextView)findViewById(R.id.txt_show_products))
                            .setText(demo);

                }else{
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
    public void heleo(){
        //asdfasdfjoasdfnsodfn
    }
}
