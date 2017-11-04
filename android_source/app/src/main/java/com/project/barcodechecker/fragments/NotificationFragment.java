package com.project.barcodechecker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.barcodechecker.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Lenovo on 21/10/2017.
 */

public class NotificationFragment extends Fragment {
    private ImageView img;
    public NotificationFragment() {
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
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        img =(ImageView) v.findViewById(R.id.img_repare);
        Picasso.with(getContext()).load("http://www.spyderrobotics.com/images/repair.png").into(img);
        return v;
    }
}
