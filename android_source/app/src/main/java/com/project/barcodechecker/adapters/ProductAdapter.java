package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.MenuItem;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by lucky on 12-Oct-17.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private List<Product> list;
    private Context context;
    private int resourceID;

    public ProductAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.context = context;
        this.resourceID = resource;
    }

//    public ProductAdapter(Context context, List<Product> list, OnMyProductClickListener onMyProductClickListener){
//        this.context = context;
//        this.list = list;
//        this.onMyProductClickListener = onMyProductClickListener;
//    }
private static class ViewHolder{
    ImageView imgProduct;
    TextView txtProductName;
    TextView txtProductPrice;
}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Product product = list.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID,null);
            viewHolder.imgProduct =(ImageView)convertView.findViewById(R.id.img_product_item_product);
            Picasso.with(context).load(product.getImgDefault()).into(viewHolder.imgProduct);
            viewHolder.txtProductName= (TextView) convertView.findViewById(R.id.txt_name_item_product);
            viewHolder.txtProductPrice= (TextView) convertView.findViewById(R.id.txt_price_item_product);
            viewHolder.txtProductName.setText(product.getName());
            viewHolder.txtProductPrice.setText(Utils.formatPrice(product.getPrice()));
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View gridView;
//        if(convertView == null){
//            gridView = inflater.inflate(R.layout.item_grid_product, parent, false);
//            Product p = getItem(position);
//            ImageView img = (ImageView)gridView.findViewById(R.id.img_product_item_product);
//            Picasso.with(context).load(p.getImgDefault()).into(img);
//            TextView txtName = (TextView) gridView.findViewById(R.id.txt_name_item_product);
//            TextView txtPrice = (TextView) gridView.findViewById(R.id.txt_price_item_product);
//            txtName.setText(p.getName());
//            txtPrice.setText(String.format(Locale.US, "%1f đ" ,p.getPrice()));
//        }else{
//            gridView = (View) convertView;
//        }
//        gridView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onMyProductClickListener.onItemClick(v, position);
//            }
//        });
        return convertView;
    }

//    public interface OnMyProductClickListener{
//        void onItemClick(View v, int position);
//    }
}
