package com.project.barcodechecker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.UserDetailActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucky on 12-Oct-17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat(AppConst.DATE_NO_TIME, Locale.US);
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
//        if(c.getName()) != null) {
            Picasso.with(context).load(c.getUserAvatar()).into(holder.imgAvatar);
//        }else{
//            Picasso.with(context).load(R.drawable.ic_insert_emoticon_black_24dp).into(holder.imgAvatar);
//        }

        holder.txtContent.setText(c.getComment());
        holder.txtName.setText(c.getName());
        final int userId =c.getUserID();
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserAndTranferToUserDetail(userId);
            }
        });
//        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.DATE_AND_TIME, Locale.US);
//        Date d = null;
//        try {
//            d = sdf.parse(c.getDateCreated());
//        } catch (ParseException e) {
//            Log.e("ERROR", "CommentAdapter onBindViewHolder");
//            e.printStackTrace();
//        }
        holder.txtDate.setText(c.getDateCreated());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private Comment getItem(int position) {
        return list.get(position);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatar;
        private TextView txtName, txtContent, txtDate;

        private CommentViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar_item_cmt);
            txtName = (TextView) itemView.findViewById(R.id.txt_name_item_cmt);
            txtContent = (TextView) itemView.findViewById(R.id.txt_content_item_cmt);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date_item_cmt);
        }
    }
    private User user;
    public void searchUserAndTranferToUserDetail(int id) {
        UserService userService = APIServiceManager.getUserService();
        userService.getByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    intent.putExtra(AppConst.USER_PARAM, user);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
