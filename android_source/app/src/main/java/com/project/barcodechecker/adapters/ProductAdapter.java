package com.project.barcodechecker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by lucky on 12-Oct-17.
 */

public class ProductAdapter extends BaseAdapter {
    private List<Product> list;
    private Context context;
    private OnMyProductClickListener onMyProductClickListener;

    public ProductAdapter(Context context, List<Product> list, OnMyProductClickListener onMyProductClickListener){
        this.context = context;
        this.list = list;
        this.onMyProductClickListener = onMyProductClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Product getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertView == null){
            gridView = inflater.inflate(R.layout.item_grid_product, parent, false);
            Product p = getItem(position);
            ImageView img = (ImageView)gridView.findViewById(R.id.img_product_item_product);
            Picasso.with(context).load(p.getImgDefault()).into(img);
            TextView txtName = (TextView) gridView.findViewById(R.id.txt_name_item_product);
            TextView txtPrice = (TextView) gridView.findViewById(R.id.txt_price_item_product);
            txtName.setText(p.getName());
            txtPrice.setText(String.format(Locale.US, "%1f đ" ,p.getPrice()));
        }else{
            gridView = (View) convertView;
        }
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyProductClickListener.onItemClick(v, position);
            }
        });
        return gridView;
    }

    public interface OnMyProductClickListener{
        void onItemClick(View v, int position);
    }
}
