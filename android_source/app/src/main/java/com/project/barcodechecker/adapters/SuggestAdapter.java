package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 31/10/2017.
 */

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.MyViewHolder> {
    private Context context;
    private List<Product> list;

    public SuggestAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_suggest, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        View v = LayoutInflater.from(context).inflate(R.layout.item_product_suggest, parent, false);
        return new SuggestAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product p = list.get(position);
        holder.name.setText(p.getName());
        holder.price.setText(p.getPrice().toString());
        Picasso.with(context).load(p.getImgDefault()).into(holder.productImage);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView name;
        public TextView price;


        public MyViewHolder(View v) {
            super(v);
            productImage = (ImageView) v.findViewById(R.id.img_product_item_product);
            name = (TextView) v.findViewById(R.id.txt_name_item_product);
            price = (TextView) v.findViewById(R.id.txt_price_item_product);

        }
    }
}
