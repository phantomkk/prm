package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 11/10/2017.
 */

public class HistoryBaseAdapter extends BaseAdapter {
    private List<Product> mAppList;
    Context mContext;
    private int resourceID;
    private int lastPosition = -1;

    class ViewHolder {
        TextView txtProductCode;
        TextView txtProductName;
        public ViewHolder(View view) {
            txtProductCode = (TextView) view.findViewById(R.id.txt_code_item_history);
            txtProductName = (TextView) view.findViewById(R.id.txt_name_item_history);
            view.setTag(this);
        }
    }

    public HistoryBaseAdapter(ArrayList<Product> mAppList, Context mContext) {
        this.mAppList = mAppList;
        this.mContext = mContext;
    }


    public HistoryBaseAdapter( Context mContextArray, List<Product> mAppList) {
        this.mAppList = mAppList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.item_history, null);
            new ViewHolder(convertView);
        }else{
            convertView = (View) convertView.getTag();
            result = convertView;
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Product product = mAppList.get(position);
//        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;
        holder.txtProductName.setText(product.getName());
        holder.txtProductCode.setText(product.getCode());
        return convertView;
    }


}
