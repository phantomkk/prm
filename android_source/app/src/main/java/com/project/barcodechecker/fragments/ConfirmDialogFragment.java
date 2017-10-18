package com.project.barcodechecker.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Lenovo on 17/10/2017.
 */

public class ConfirmDialogFragment extends DialogFragment{
    public interface ConfirmDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    private String mTitle;
    private String mMessage;
    private ConfirmDialogListener mListener;
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public ConfirmDialogFragment(String mTitle, String mMessage, ConfirmDialogListener mListener) {
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        this.mListener = mListener;
    }

    public ConfirmDialogFragment() {
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle).setMessage(mMessage)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(ConfirmDialogFragment.this);
                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ConfirmDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }
}
