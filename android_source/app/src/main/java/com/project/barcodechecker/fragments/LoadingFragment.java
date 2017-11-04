package com.project.barcodechecker.fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.project.barcodechecker.utils.AppConst;

import java.io.IOException;

import retrofit2.Response;

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

    protected void hideLoading() {
        mProgressDialog.dismiss();
    }

    public void logError(String activity, String method, String message) {
        Log.e("LOG_ERROR", activity + "." + method + "(): " + message);
    }

    public void logError(String message) {
        Log.e("LOG_ERROR", message);
    }

    public void logError(Response response, String activity) throws IOException {
        if (response.isSuccessful()) {
            Log.e(AppConst.LOG_ERROR, "Response success: " + activity + ": ");
        } else {
            if (response.errorBody() != null) {
                Log.e(AppConst.LOG_ERROR, "Response error code " + response.code()+ "Error message: " +
                        response.errorBody().string());
            } else {
                Log.e("LOG_ERROR", "Response error body null");
            }
        }
    }
}
