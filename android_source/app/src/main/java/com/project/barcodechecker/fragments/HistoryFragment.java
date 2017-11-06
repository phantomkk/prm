package com.project.barcodechecker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.databaseHelper.ProductDatabaseHelper;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends LoadingFragment{
    private ListView lvHistory;
    private List<Product> list;
    ListView listView;
    ProductDatabaseHelper historyDatabaseHelper;
    HistoryAdapter adapter;
    private ProductService pService;

    private static HistoryFragment instance= new HistoryFragment();
    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        instance = new HistoryFragment();
        return instance;
    }

    public static HistoryFragment getInstance(){
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) v.findViewById(R.id.list_history_frag_history);
        historyDatabaseHelper = new ProductDatabaseHelper(getContext());
        list = historyDatabaseHelper.getAllProducts();
        adapter = new HistoryAdapter(getContext(),R.layout.item_history,list);
        listView.setAdapter(adapter);
//        listView.setDivider(null);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmDeleteDialog(position);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = list.get(position);
                if (product.getName().equals(getString(R.string.string_not_fount))){
                    DialogFragment messageDialogFragment = MessageDialogFragment.newInstance(getString(R.string.not_found_product_dialog_message),
                            new MessageDialogFragment.MessageDialogListener() {
                                @Override
                                public void onDialogPositiveClick(DialogFragment dialog) {
                                    dialog.dismiss();
                                }
                            });
                    messageDialogFragment.show(getFragmentManager(),getString(R.string.not_found_product_dialog_tag));
                }else{
                    searchProductAndTranferToProductDetail(list.get(position).getId());
//                    Intent intent = new Intent(getActivity(), DetailActivity.class);
//                    intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
//                    startActivity(intent);
                }

            }
        });

        return v;
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
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(AppConst.PRODUCT_PARAM, product);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getContext(), "Loading fail",
                        Toast.LENGTH_LONG).show();
                hideLoading();
            }
        });
    }
    public void showConfirmDeleteDialog(final int position) {
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment(getString(R.string.delete_history_dialog_title),
               getString(R.string.delete_history_dialog_message),
                new ConfirmDialogFragment.ConfirmDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                historyDatabaseHelper.deleteProduct(list.get(position).getIdDatabase());
                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {
                dialog.dismiss();
            }
        });
        confirmDialogFragment.show(getFragmentManager(),getString(R.string.delete_history_dialog_tag));
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


}
