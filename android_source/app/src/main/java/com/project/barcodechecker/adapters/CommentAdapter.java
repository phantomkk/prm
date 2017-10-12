package com.project.barcodechecker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.utils.AppConst;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lucky on 12-Oct-17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat(AppConst.DATE_NO_TIME);
    private List<Comment> list;
    private Context context;

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(v);
    }

    public CommentAdapter(Context context, List<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment c = getItem(position);
        Picasso.with(context).load(c.getAvatar()).error(R.drawable.ic_insert_emoticon_black_24dp).into(holder.imgAvatar);
        holder.txtContent.setText(c.getComment());
        holder.txtName.setText(c.getName());
        holder.txtDate.setText(sdf.format(c.getDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Comment getItem(int position) {
        return list.get(position);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView txtName, txtContent,txtDate;
        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
