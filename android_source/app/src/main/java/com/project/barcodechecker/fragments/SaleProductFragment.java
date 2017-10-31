package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.SaleProductAdapter;
import com.project.barcodechecker.adapters.SaleUserAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.customize.MyGridView;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 01/11/2017.
 */

public class SaleProductFragment extends Fragment {

    private MyGridView rcvComment;
    private ArrayAdapter<Sale> adapter;
    private List<Sale> list;
    private Button btnComment;
    private EditText edtComment;
    private TextView txtErrorCmt;

    public SaleProductFragment() {

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
        rcvComment = (MyGridView) v.findViewById(R.id.list_sale_product);
        list = new ArrayList<>();
        adapter = new SaleProductAdapter(getContext(), R.layout.item_sale_product, this.list);
        rcvComment.setHapticFeedbackEnabled(true);
        rcvComment.setAdapter(adapter);
//        Utils.setGridViewHeightBasedOnItems(rcvComment);
        return v;
    }

    public void setData(final List<Sale> list) {
        this.list = list;
        adapter = new SaleProductAdapter(getContext(), R.layout.item_sale_product, this.list);
        rcvComment.setAdapter(adapter);
        rcvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchProductAndTranferToProductDetail(list.get(position).getProductId());
            }
        });
        rcvComment.setHapticFeedbackEnabled(true);
//        Utils.setGridViewHeightBasedOnItems(rcvComment);
        adapter.notifyDataSetChanged();
    }

    private User user;
    private Product product;

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
