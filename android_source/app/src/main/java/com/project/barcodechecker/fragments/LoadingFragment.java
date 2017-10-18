package com.project.barcodechecker.fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

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
