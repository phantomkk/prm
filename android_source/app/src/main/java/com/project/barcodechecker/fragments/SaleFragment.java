package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.CommentAdapter;
import com.project.barcodechecker.adapters.Sale2Adapter;
import com.project.barcodechecker.adapters.SaleAdapter;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private Product product;
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
        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
        list.add(new Sale("haha",134,"https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt.jpg"));
        if(type==1){
            adapter = new SaleAdapter(getContext(),R.layout.item_sale_product,this.list);
        }else{
            adapter = new Sale2Adapter(getContext(),R.layout.item_sale_product_2,this.list);
        }
        rcvComment.setAdapter(adapter);
        Utils.setListViewHeightBasedOnItems(rcvComment);
        return v;
    }

    public void setData(List<Sale> list){
        this.list = list;
        adapter = new SaleAdapter(getContext(),R.layout.item_sale_product,this.list);
        rcvComment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



    public interface ButtonPostCommentClickListener{
        void postCmtClickListener(String content, EditText edtCmt, TextView txtError);
    }
}
