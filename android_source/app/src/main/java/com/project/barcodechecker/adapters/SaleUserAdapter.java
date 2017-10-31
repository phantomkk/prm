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
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 30/10/2017.
 */

public class SaleUserAdapter extends ArrayAdapter<Sale> {
    private int resourceID;
    private int lastPosition = -1;
    private Context context;
    private User user;

    public SaleUserAdapter(@NonNull Context context, @LayoutRes int resourceID, @NonNull List<Sale> list) {
        super(context, resourceID, list);
        this.resourceID = resourceID;
        this.context = context;
    }

    private static class ViewHolder {
        CircleImageView imgAvatar;
        TextView tvName;
        TextView tvPrcie;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sale sale = getItem(position);
        SaleUserAdapter.ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new SaleUserAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.saler_name);
            viewHolder.tvPrcie = (TextView) convertView.findViewById(R.id.sale_product_price);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SaleUserAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        searchUser(sale.getUserId(), viewHolder.imgAvatar, viewHolder.tvName);
        viewHolder.tvPrcie.setText(sale.getPrice() + "đ");

        return convertView;
    }

    public void searchUser(int id, final ImageView imageView, final TextView textView) {
        UserService userService = APIServiceManager.getUserService();
        userService.getByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Picasso.with(context).load(user.getAvatar()).into(imageView);
                    textView.setText(user.getName());
                } else {
                    imageView.setBackground(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                imageView.setBackground(null);
            }
        });
    }

}
