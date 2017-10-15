package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.History;
import com.project.barcodechecker.models.Product;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by lucky on 07-Oct-17.
 */

public class HistoryAdapter extends ArrayAdapter<History>  {
    private int resourceID;
    private int lastPosition = -1;
    public HistoryAdapter(@NonNull Context context, @LayoutRes int resourceID,@NonNull  List<History> list) {
        super(context, resourceID, list);
        this.resourceID = resourceID;
    }

    private static class ViewHolder{
        TextView txtProductCode;
        TextView txtProductName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        History history = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID, parent, false);
            viewHolder.txtProductCode = (TextView)convertView.findViewById(R.id.txt_code_item_history);
            viewHolder.txtProductName = (TextView) convertView.findViewById(R.id.txt_name_item_history);
            result = convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
//              Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.txtProductName.setText(history.getProductName());
        viewHolder.txtProductCode.setText(history.getProductCode());
        return convertView;
    }
}
