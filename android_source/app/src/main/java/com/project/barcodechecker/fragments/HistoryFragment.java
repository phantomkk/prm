package com.project.barcodechecker.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.databaseHelper.ProductDatabaseHelper;
import com.project.barcodechecker.models.History;
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
        listView.setDivider(null);
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
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
                    startActivity(intent);
                }

            }
        });

        return v;
    }


    public void showConfirmDeleteDialog(final int position) {
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment(getString(R.string.delete_history_dialog_title),
               getString(R.string.delete_history_dialog_message),
                new ConfirmDialogFragment.ConfirmDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                historyDatabaseHelper.deleteProduct(list.get(position).getId());
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
