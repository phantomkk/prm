package com.project.barcodechecker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.ProductAdapter;
import com.project.barcodechecker.api.services.CategoryService;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.customize.MyGridView;
import com.project.barcodechecker.dialog.DialogErrorConnection;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends BaseActivity {
    private MyGridView gvProducts;
    private List<Product> list;
    private ProductAdapter adapter;
    private CategoryService service;
int position = -1;
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
        position = (int) b.get(AppConst.CATEGORY_PARAM);
        service = APIServiceManager.getCategoryService();
        loadProduct();
    }

    public void loadProduct(){
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
                            searchProductAndTranferToProductDetail(list.get(position).getId());
//                            Intent intent = new Intent(ListProductActivity.this, DetailActivity.class);
//                            intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
//                            startActivity(intent);
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
                showDialogConnError();
            }
        });
        list = new ArrayList<>();
    }
    private Product product;
    public void searchProductAndTranferToProductDetail(int id) {
        showLoading();
        ProductService productService = APIServiceManager.getPService();
        productService.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    product = response.body();
                    Intent intent = new Intent(ListProductActivity.this, DetailActivity.class);
                    intent.putExtra(AppConst.PRODUCT_PARAM, product);
                    startActivity(intent);
                } else {
                    Toast.makeText(ListProductActivity.this, "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ListProductActivity.this, "Loading fail",
                        Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });
    }
    public void initView() {
        gvProducts = (MyGridView) findViewById(R.id.grd_list_product);
    }
    AlertDialog dialog =null;
    public void showDialogConnError(){
        final DialogErrorConnection dialog = new DialogErrorConnection(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProduct();
            }
        });
        dialog.show();
    }

}
