package com.project.barcodechecker.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.SearchAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends LoadingFragment {
    private EditText edtSearch;
    private ProductService productService;
    private ProgressDialog progressDialog;
    private List<Product> list;
    private SearchAdapter adapter;
    private RecyclerView rcvSearch;
    ProgressBar progressBar;
    private static SearchFragment instance = new SearchFragment();

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        edtSearch = ((EditText) v.findViewById(R.id.edt_search_frag_search));
        rcvSearch = (RecyclerView) v.findViewById(R.id.rcv_search_fragment);
        progressBar = (ProgressBar) v.findViewById(R.id.process_loading);
        list = new ArrayList<>();
        adapter = new SearchAdapter(getContext(), list);
        rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvSearch.setAdapter(adapter);
        productService = APIServiceManager.getPService();
        ItemClickSupport.addTo(rcvSearch).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
                startActivity(intent);
            }
        });
        edtSearch.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
//                            searchProcess();
                        }
                        return false; // pass on to other listeners.
                    }
                });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchProcess();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return v;
    }

    Call<Product> call;
    Call<List<Product>> callList;
    Callback<Product> callback = new Callback<Product>() {
        @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            list.clear();
            list.add(response.body());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Product> call, Throwable t) {

        }
    };

    public void searchProcess() {
        String searchValue = edtSearch.getText().toString().trim();
        //showLoading();
        progressBar.setVisibility(View.VISIBLE);
        if (searchValue.matches("\\d+")) {
            if (call != null && call.isExecuted()) {
                call.cancel();
            }
            call = productService.getProductByCode(searchValue);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        list.clear();
                        list.add(response.body());
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("ERROR", "ELSE SEARCH FRAGMENT: searchProcess search code product");
                    }
                    // closeLoading();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    //closeLoading();
                    Log.e("ERROR", "FAILURE SEARCH FRAGMENT: searchProcess Search code");
                    progressBar.setVisibility(View.GONE);

                }
            });
        } else {
            if (callList != null && callList.isExecuted()) {
                callList.cancel();
            }
            callList = productService.searchProduct(searchValue);
            callList.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        list.clear();
                        list.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("ERROR", "ELSE SEARCH FRAGMENT: searchProcess search list product");
                    }
//                    closeLoading();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    //   closeLoading();
                    Log.e("ERROR", "FAILURE SEARCH FRAGMENT: searchProcess search list product");
                    progressBar.setVisibility(View.GONE);

                }
            });

        }
    }

    public static SearchFragment getInstance() {
        return instance;
    }
}
