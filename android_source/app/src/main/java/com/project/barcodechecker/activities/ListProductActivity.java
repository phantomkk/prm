package com.project.barcodechecker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.ProductAdapter;
import com.project.barcodechecker.api.services.CategoryService;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends BaseActivity {
    private GridView gvProducts;
    private List<Product> list;
    private ProductAdapter adapter;
    private CategoryService service;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_list_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Danh sách sản phẩm");
        initView();
        Intent intent = getIntent();
        Bundle b = (Bundle) intent.getExtras();
        int position = (int) b.get(AppConst.CATEGORY_PARAM);
        service = APIServiceManager.getCategoryService();
        showLoading();
        service.getProductByCategoryId(position).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapter = new ProductAdapter(ListProductActivity.this, R.layout.item_grid_product, list);
//                    Log.d("ListProductActivity", "list size:" + list.get(22).getName() + "");
                    gvProducts.setAdapter(adapter);
                    gvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ListProductActivity.this, DetailActivity.class);
                            intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
                            startActivity(intent);
                        }
                    });
//                    adapter.notifyDataSetChanged();
                } else {
                    logError(ListProductActivity.class.getSimpleName(), "onCreate", "ELSE API");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                logError("ListProductActivity", "OnCreate", "Failure api" + t.getMessage());
                hideLoading();
            }
        });
        list = new ArrayList<>();
    }

    public void initView() {
        gvProducts = (GridView) findViewById(R.id.grd_list_product);
    }


}
