package com.project.barcodechecker.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.adapters.ProductSaleManagerAdapter;
import com.project.barcodechecker.adapters.SearchAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.SaleService;
import com.project.barcodechecker.databaseHelper.ProductDatabaseHelper;
import com.project.barcodechecker.dialog.EditProductPriceDialog;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 21/10/2017.
 */

public class ListProductFragment extends LoadingFragment implements ProductSaleManagerAdapter.ProductSaleListenner,
        EditProductPriceDialog.EditProductPriceListener {
    private List<Sale> list;
    private ListView listView;
    private ProductSaleManagerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Product product;
    private ProgressBar progressBar;
    private boolean isFirst = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_product, container, false);
        listView = (ListView) v.findViewById(R.id.lv_search_fragment);
        progressBar = (ProgressBar) v.findViewById(R.id.process_sale);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        list = new ArrayList<>();
        adapter = new ProductSaleManagerAdapter(getContext(), R.layout.item_product_sale_manager, list, this);
        listView.setAdapter(adapter);

        setValue();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refeshValue();
            }
        });


        return v;
//        return inflater.inflate(R.layout.fragment_list_product, container, false);
    }


    public void setValue(){
        List<Sale> sales = new ArrayList<>();
        SaleService saleService = APIServiceManager.getSaleService();
        saleService.getSaleByUserID(CoreManager.getUser(getContext()).getId()).enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if (response.isSuccessful()) {
                    List<Sale> saleList = new ArrayList<Sale>();
                    saleList = response.body();
                    list.clear();
                    list.addAll(saleList);
                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getActivity(), "Update Success",
//                            Toast.LENGTH_LONG).show();
                    if (list.isEmpty()){
                        listView.setVisibility(View.GONE);
                    }else{
                        listView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.e(DetailActivity.class.getSimpleName(), "loadSaleError");
//                    Toast.makeText(getActivity(), "Update Fail.Please try again!",
//                            Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                Log.e(DetailActivity.class.getSimpleName(), "loadSale Failure API " + t.getMessage());
//                Toast.makeText(getActivity(), "Update error.Please try again later!",
//                        Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void refeshValue() {
        List<Sale> sales = new ArrayList<>();
        SaleService saleService = APIServiceManager.getSaleService();
        saleService.getSaleByUserID(CoreManager.getUser(getContext()).getId()).enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                if (response.isSuccessful()) {
                    List<Sale> saleList = new ArrayList<Sale>();
                    saleList = response.body();
                    list.clear();
                    list.addAll(saleList);
                    if (list.isEmpty()){
                        listView.setVisibility(View.GONE);
                    }else{
                        listView.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Update Success",
                                Toast.LENGTH_LONG).show();
                } else {
                    Log.e(DetailActivity.class.getSimpleName(), "loadSaleError");
                    Toast.makeText(getActivity(), "Update Fail.Please try again!",
                            Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                Log.e(DetailActivity.class.getSimpleName(), "loadSale Failure API " + t.getMessage());
                Toast.makeText(getActivity(), "Update error.Please try again later!",
                        Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void showConfirmDeleteDialog(final Sale sale, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Sản Phẩm").setMessage("Bạn có chắc muốn xóa sản phẩm đang bán?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(sale, position);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void delete(Sale sale, final int position) {
        showLoading();
        Sale sale1=sale;
        SaleService saleService = APIServiceManager.getSaleService();
        saleService.deleteSale(CoreManager.getUser(getContext()).getId(), sale.getProduct().getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Delete Success",
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.e(DetailActivity.class.getSimpleName(), "DeleteSale");
                    Toast.makeText(getActivity(), "Delete Fail.Please try again!", Toast.LENGTH_LONG).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(DetailActivity.class.getSimpleName(), "DeleteSale Failure API " + t.getMessage());
                Toast.makeText(getActivity(), "Delete error.Please try again later!", Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });

    }

    @Override
    public void deleteSale(Sale sale, final int position) {
        showConfirmDeleteDialog(sale, position);
    }

    @Override
    public void editPriceSale(Sale sale, int position) {
        EditProductPriceDialog dialog = new EditProductPriceDialog(getContext(),sale,position,this);
        dialog.show();
    }
    private Sale newSale;
    @Override
    public void editProductPrice(final Sale sale, final int position, float newPrice) {
        showLoading();
        newSale = sale;
        newSale.setPrice(newPrice);
        SaleService saleService = APIServiceManager.getSaleService();
        saleService.editSale(CoreManager.getUser(getContext()).getId(), sale.getProduct().getId(), newSale).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if (response.isSuccessful()) {
//                    List<Sale> sales2 = list;
//                    sales2.remove(position);
//                    sales2.add(position,newSale);
////                    list.clear();
                    list.remove(position);
                    list.add(position,newSale);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Update Price Success",
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.e(DetailActivity.class.getSimpleName(), "udateSaleError");
                    Toast.makeText(getActivity(), "Upate Fail.Please try again!", Toast.LENGTH_LONG).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                Log.e(DetailActivity.class.getSimpleName(), "UpdateSale Failure API " + t.getMessage());
                Toast.makeText(getActivity(), "Update error.Please try again later!", Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });
    }
}
