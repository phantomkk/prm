package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;

/**
 * Created by Lenovo on 15/10/2017.
 */

public class LoadingFragment extends Fragment{
    private ProgressDialog mProgressDialog;

    protected void showLoading(){

        mProgressDialog = new ProgressDialog(getContext());

        mProgressDialog.setMessage("Working ...");
        mProgressDialog.show();
    }


    protected void closeLoading() {
        mProgressDialog.dismiss();
    }
}
