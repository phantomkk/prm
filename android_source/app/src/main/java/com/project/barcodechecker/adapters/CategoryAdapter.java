package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.RatingCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.MenuItem;

import java.util.List;

/**
 * Created by lucky on 07-Oct-17.
 */

public class CategoryAdapter extends ArrayAdapter<MenuItem> {
    private int resourceID;

    public CategoryAdapter(Context context, int resourceID, List<MenuItem> list) {
        super(context, resourceID, list);
        this.resourceID = resourceID;
    }

    private static class ViewHolder {
        ImageView imgIcon;
        TextView txtName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MenuItem item = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceID, parent,false);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.img_category_item_category);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name_item_category);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imgIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), item.getIcon()));
        viewHolder.txtName.setText(item.getName());
        return convertView;
    }
}
