package com.project.barcodechecker.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
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
import java.util.Collections;
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
    private ButtonPostCommentClickListener postCommentClickListener;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postCommentClickListener = (ButtonPostCommentClickListener) getActivity();
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtComment.getText().toString().trim();
                postCommentClickListener.postCmtClickListener(content, edtComment, txtErrorCmt);
            }
        });

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

        InputMethodManager imr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return v;
    }

    public void setData(List<Comment> list){
        this.list = list;
        Collections.sort(list);
        adapter = new CommentAdapter(getContext(), this.list);
        rcvComment.setAdapter(adapter);
        rcvComment.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

    public void updateData(Comment comment){
        list.add(0,comment);
        Collections.sort(list);
        adapter.notifyDataSetChanged();
    }

    ///listener


    public interface ButtonPostCommentClickListener{
        void postCmtClickListener(String content, EditText edtCmt, TextView txtError);
    }
}
