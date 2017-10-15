package com.project.barcodechecker.fragments;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.api.services.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.databaseHelper.HistoryDatabaseHelper;
import com.project.barcodechecker.models.History;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends LoadingFragment{
    private ListView lvHistory;
    private List<History> list;
    SwipeMenuListView listView;
    HistoryDatabaseHelper historyDatabaseHelper;
    HistoryAdapter adapter;
    private ProductService pService;

    private static HistoryFragment instance= new HistoryFragment();
    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    public static HistoryFragment getInstance(){
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (SwipeMenuListView) v.findViewById(R.id.list_history_frag_history);
        historyDatabaseHelper = new HistoryDatabaseHelper(getContext());
        list = historyDatabaseHelper.getAllHistories();
        adapter = new HistoryAdapter(getContext(),R.layout.item_history,list);

        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getActivity().getApplicationContext());
                item1.setBackground(R.color.color_red_500);
                item1.setWidth(dp2px(60));
                item1.setIcon(R.drawable.ic_action_delete);
                menu.addMenuItem(item1);
            }
        };
        //set icon for listview
        listView.setMenuCreator(creator);
        //hide devider of item in list
        listView.setDivider(null);
        //show confirm when item click
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showConfirmDeleteDialog(position);
                        break;
                }
                return false;
            }
        });
        //add event when click item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showLoading();
                pService = APIServiceManager.getPService();
                pService.getProductByCode("9597394955974").enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        closeLoading();
                        if (response.isSuccessful()) {
                            //showMessageDialog("Name = " + response.body().getName());
                            Product product = response.body();
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra(AppConst.PRODUCT_PARAM, product);
                            startActivity(intent);
                        } else {
                          //  showMessageDialog("Successful but else");
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        closeLoading();
                      //  showMessageDialog("Fail");
                    }
                });

            }
        });

        return v;
    }
//    public void showMessageDialog(String message) {
//        DialogFragment fragment = MessageDialogFragment.newInstance("Scan Results", message, );
//        fragment.show(getFragmentManager(), "scan_results");
//    }
//
//    public void closeMessageDialog() {
//        closeDialog("scan_results");
//    }




    public void showConfirmDeleteDialog(final int position) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete")
                .setMessage("Are you sure to delete this history?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        historyDatabaseHelper.deleteNote(list.get(position).getId());
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
