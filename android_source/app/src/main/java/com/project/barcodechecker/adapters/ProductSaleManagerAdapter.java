package com.project.barcodechecker.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.dialog.EditProductPriceDialog;
import com.project.barcodechecker.fragments.ConfirmDialogFragment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 04/11/2017.
 */

public class ProductSaleManagerAdapter extends ArrayAdapter<Sale>{
    private int resourceID;
    private int lastPosition = -1;
    private Context context;
    private Sale sale;
    private DecimalFormat formatter = new DecimalFormat("###,###,###.##VNĐ");
    public ProductSaleManagerAdapter(@NonNull Context context, @LayoutRes int resourceID, @NonNull List<Sale> list, ProductSaleListenner productSaleListenner) {
        super(context, resourceID, list);
        this.resourceID = resourceID;
        this.context = context;
        this.productSaleListenner = productSaleListenner;
    }
    private ProductSaleListenner productSaleListenner;


    public interface ProductSaleListenner{
        public void deleteSale(Sale sale, int position);
        public void editPriceSale(Sale sale, int position);
    }

    private static class ViewHolder {
        ImageView imgProduct;
        TextView tvName;
        TextView tvPrcie,tvDate,tvCategory;
        ImageButton btnEdit, btnDelete;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Sale sale = getItem(position);
        ProductSaleManagerAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ProductSaleManagerAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.imgProduct = (ImageView) convertView.findViewById(R.id.img_product_item_product);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.txt_name_item_product);
            viewHolder.tvPrcie = (TextView) convertView.findViewById(R.id.txt_price_item_product);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.txt_date_product);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
            viewHolder.btnEdit = (ImageButton) convertView.findViewById(R.id.btn_edit);
            viewHolder.btnDelete = (ImageButton) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ProductSaleManagerAdapter.ViewHolder) convertView.getTag();
        }
        if (sale.getProduct() != null) {
            viewHolder.tvName.setText(sale.getProduct().getName());
            Picasso.with(context).load(sale.getProduct().getImgDefault()).into(viewHolder.imgProduct);
        }
        viewHolder.tvPrcie.setText(Utils.formatPrice(sale.getPrice()));
        String d = sale.getDateCreate() == null ? "" : (sale.getDateCreate().replace("T00:00:00",""));
        viewHolder.tvDate.setText("Ngày đăng: "+d);

        viewHolder.tvCategory.setText("Loại: "+ Utils.getCategoryString(sale.getProduct().getCategoryID()));
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSaleListenner.editPriceSale(getItem(position),position);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSaleListenner.deleteSale(sale,position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = getItem(position).getProduct();
                if (product == null) {
                    searchProductAndTranferToProductDetail(getItem(position).getProductId());
                } else {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(AppConst.PRODUCT_PARAM, getItem(position).getProduct());
                    context.startActivity(intent);
                }
            }
        });
        return convertView;

    }



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
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(context, "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    public void deleteSale(int position){

    }
}