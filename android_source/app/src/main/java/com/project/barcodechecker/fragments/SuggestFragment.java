package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.SuggestAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 31/10/2017.
 */

public class SuggestFragment  extends Fragment {
    private RecyclerView rcvComment;
    private SuggestAdapter adapter;
    private List<Product> list;
    private Button btnComment;
    private EditText edtComment;
    private TextView txtErrorCmt;
    public SuggestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_suggest, container, false);
        rcvComment = (RecyclerView) v.findViewById(R.id.cardView_suggest);
        rcvComment.setHasFixedSize(true);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getActivity());
        myLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list = new ArrayList<>();
        adapter = new SuggestAdapter(getContext(),this.list);
        rcvComment.setAdapter(adapter);
        rcvComment.setLayoutManager(myLayoutManager);
        ItemClickSupport.addTo(rcvComment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
//                startActivity(intent);
                searchProductAndTranferToProductDetail(list.get(position).getId());
            }
        });
        return v;
    }
    private ProgressDialog mProgressDialog;

    protected void showLoading(){

        mProgressDialog = new ProgressDialog(getContext());

        mProgressDialog.setMessage("Đang tải...");
        mProgressDialog.show();
    }

    protected void hideLoading() {
        mProgressDialog.dismiss();
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
    public void setData(List<Product> list){
        this.list = list;
        adapter = new SuggestAdapter(getContext(),this.list);
        rcvComment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
