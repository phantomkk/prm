package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.barcodechecker.R;

/**
 * Created by Lenovo on 11/10/2017.
 */

public class ScanBlankFragment extends Fragment {
    private static ScanBlankFragment instance = new ScanBlankFragment();
    public static ScanBlankFragment newInstance() {
        ScanBlankFragment fragment = new ScanBlankFragment();
        return fragment;
    }

    public static ScanBlankFragment getInstance() {
        return instance;
    }

    public static void setInstance(ScanBlankFragment instance) {
        ScanBlankFragment.instance = instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_blank, container, false);
    }
}
