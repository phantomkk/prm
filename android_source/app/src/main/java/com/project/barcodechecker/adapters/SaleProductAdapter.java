package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 31/10/2017.
 */

public class SaleProductAdapter extends ArrayAdapter<Sale> {
    private int resourceID;
    private int lastPosition = -1;
    private Context context;
    private Product product;
    private DecimalFormat formatter = new DecimalFormat("###,###,###.##VNĐ");

    public SaleProductAdapter(@NonNull Context context, @LayoutRes int resourceID, @NonNull List<Sale> list) {
        super(context, resourceID, list);
        this.resourceID = resourceID;
        this.context = context;
    }

    private static class ViewHolder {
        ImageView imgAvatar;
        TextView tvName;
        TextView tvPrcie;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sale sale = getItem(position);
        SaleProductAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new SaleProductAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.imgAvatar = (ImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.saler_name);
            viewHolder.tvPrcie = (TextView) convertView.findViewById(R.id.sale_product_price);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SaleProductAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
//        searchProduct(sale.getProductId(),viewHolder.imgAvatar, viewHolder.tvName);
//        if (sale.getName() != null) {
//            viewHolder.tvName.setText(sale.getName());
//        }
        if (sale.getProduct()!=null) {
            viewHolder.tvName.setText(sale.getProduct().getName());
            Picasso.with(context).load(sale.getProduct().getImgDefault()).into(viewHolder.imgAvatar);
        }
        viewHolder.tvPrcie.setText(formatter.format(sale.getPrice()));

        return convertView;
    }


    public void searchProduct(int id, final ImageView imageView, final TextView textView) {
        ProductService productService = APIServiceManager.getPService();
        productService.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    product = response.body();
                    Picasso.with(context).load(product.getImgDefault()).into(imageView);
                    textView.setText(product.getName());
                } else {
                    imageView.setBackground(null);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                imageView.setBackground(null);
            }
        });
    }
}
