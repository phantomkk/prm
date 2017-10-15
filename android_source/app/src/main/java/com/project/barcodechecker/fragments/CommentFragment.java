package com.project.barcodechecker.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.LoginActivity;
import com.project.barcodechecker.adapters.CommentAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.CommentService;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    private RecyclerView rcvComment;
    private CommentAdapter adapter;
    private List<Comment> list;
    private Button btnComment;
    private EditText edtComment;
    private TextView txtErrorCmt;
    private Product product;
    public CommentFragment() {
        // Required empty public constructor
    }
    public void setProduct(Product product){
        this.product = product;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        rcvComment = (RecyclerView) v.findViewById(R.id.rcv_comment);
        btnComment = (Button) v.findViewById(R.id.btn_comment_frag_cmt);
        edtComment = (EditText) v.findViewById(R.id.edt_content_frag_cmt);
        txtErrorCmt = (TextView) v.findViewById(R.id.txt_error_comment_frag_cmt);

        btnComment.setOnClickListener(commentClickListener);
        InputMethodManager imr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return v;
    }

    public void setData(List<Comment> list){
        this.list = list;
        adapter = new CommentAdapter(getContext(), this.list);
        rcvComment.setAdapter(adapter);
        rcvComment.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

    ///listener
    View.OnClickListener commentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edtComment.setText(edtComment.getText().toString().trim());
            String content = edtComment.getText().toString().trim();
            if(content.length()==0){
                txtErrorCmt.setVisibility(View.VISIBLE);
                return;
            }else{
                txtErrorCmt.setVisibility(View.INVISIBLE);
            }
            User u = CoreManager.getUser();
            if(u == null){
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("Bạn cần phải đăng nhập để bình luận!")
                        .setPositiveButton("Đăng nhập", positiveListener)
                        .setNegativeButton("Thôi kệ lười đăng nhập lắm", null)
                        .show();

            }else{
                Log.e("USER", u.getId()+"");
                Date date = Calendar.getInstance().getTime();
                final Comment comment = new Comment();
                comment.setUserID(u.getId());
                comment.setComment(content);
                comment.setUser(u);
                comment.setProductID(product==null ? 1 : product.getId());
                comment.setDateCreated(new SimpleDateFormat(AppConst.DATE_AND_TIME_SQL,Locale.US).format(date));
                CommentService commentService = APIServiceManager.getCommentService();
                commentService.postComment(comment).enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        if(response.isSuccessful()){
                            list.add(comment);
                            adapter.notifyDataSetChanged();
                        }else{
                            edtComment.setText("EROR");
                            Log.e("ERROR", "CommentFragment ELSE" );
                        }
                        edtComment.setText("");
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Log.e("ERROR", "CommentFragment commentClickListener: " + t.getCause());
                        edtComment.setText("");
                    }
                });
            }
        }
    };
    final DialogInterface.OnClickListener positiveListener =new DialogInterface.OnClickListener(){

        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    };
}
