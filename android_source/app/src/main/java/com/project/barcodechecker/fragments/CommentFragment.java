package com.project.barcodechecker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.CommentAdapter;
import com.project.barcodechecker.models.Comment;

import java.util.List;

public class CommentFragment extends Fragment {
    private RecyclerView rcvComment;
    private CommentAdapter adapter;
    private List<Comment> list;
    public CommentFragment() {
        // Required empty public constructor
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
        
        return v;
    }

    public void setData(List<Comment> list){
        this.list = list;
        adapter = new CommentAdapter(getContext(), this.list);
        rcvComment.setAdapter(adapter);
        rcvComment.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

}
