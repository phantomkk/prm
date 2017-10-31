package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.activities.ListProductActivity;
import com.project.barcodechecker.activities.UserDetailActivity;
import com.project.barcodechecker.adapters.CommentAdapter;
import com.project.barcodechecker.adapters.Sale2Adapter;
import com.project.barcodechecker.adapters.SaleAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 30/10/2017.
 */

public class SaleFragment extends Fragment {
    private ListView rcvComment;
    private ArrayAdapter<Sale> adapter;
    private List<Sale> list;
    private Button btnComment;
    private EditText edtComment;
    private TextView txtErrorCmt;
    private int type;

    public SaleFragment(int type) {
        // Required empty public constructor
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sale_product, container, false);
        rcvComment = (ListView) v.findViewById(R.id.list_sale_product);
        list = new ArrayList<>();
//        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
//        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
//        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
        if (type == 1) {
            adapter = new SaleAdapter(getContext(), R.layout.item_sale_product, this.list);
        } else {
            adapter = new Sale2Adapter(getContext(), R.layout.item_sale_product_2, this.list);
        }
        rcvComment.setAdapter(adapter);
        Utils.setListViewHeightBasedOnItems(rcvComment);
        return v;
    }

    public void setData(final List<Sale> list, final int type) {
        this.list = list;
        if (type == 1) {
            adapter = new SaleAdapter(getContext(), R.layout.item_sale_product, this.list);
        } else {
            adapter = new Sale2Adapter(getContext(), R.layout.item_sale_product_2, this.list);
        }
        rcvComment.setAdapter(adapter);
        rcvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type ==1){
                    searchUserAndTranferToUserDetail(list.get(position).getUserId());
                }else{
                    searchProductAndTranferToProductDetail(list.get(position).getProductId());
                }
            }
        });
        Utils.setListViewHeightBasedOnItems(rcvComment);
        adapter.notifyDataSetChanged();
    }
    private User user;
    private Product product;

    public void searchUserAndTranferToUserDetail(int id) {
        UserService userService = APIServiceManager.getUserService();
        userService.getByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    intent.putExtra(AppConst.USER_PARAM, user);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Loading fail",
                            Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void searchProductAndTranferToProductDetail(int id) {
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
                    Toast.makeText(getActivity(), "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getActivity(), "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
