package com.project.barcodechecker.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.barcodechecker.R;

public class ErrorFragment extends Fragment {
private Button btnReload;
    private TextView txtError;

    public ErrorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_error, container, false);
        btnReload = (Button) v.findViewById(R.id.btn_reload_err_frag);
        btnReload.setOnClickListener(clickListener);
        return v;
        // Inflate the layout for this fragment
    }


    ////Listener
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
