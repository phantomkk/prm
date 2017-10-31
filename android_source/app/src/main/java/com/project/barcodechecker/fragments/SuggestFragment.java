package com.project.barcodechecker.fragments;

import android.app.Fragment;
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

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.SuggestAdapter;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

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
    private Product product;
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
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
                startActivity(intent);
            }
        });
        return v;
    }

    public void setData(List<Product> list){
        this.list = list;
        adapter = new SuggestAdapter(getContext(),this.list);
        rcvComment.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
