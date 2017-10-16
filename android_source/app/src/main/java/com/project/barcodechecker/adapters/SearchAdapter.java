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

import java.util.List;

/**
 * Created by lucky on 16-Oct-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private Context context;
    private List<Product> list;

    public SearchAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new SearchHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        Product p = list.get(position);
        holder.txtName.setText(p.getName());
        holder.txtCode.setText(p.getCode());
        Picasso.with(context).load(p.getImgDefault()).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtCode;
        private ImageView imgProduct;

        public SearchHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_name_search_frag);
            txtCode = (TextView) itemView.findViewById(R.id.txt_code_search_frag);
            imgProduct = (ImageView) itemView.findViewById(R.id.img_product_search_frag);
        }
    }
}
