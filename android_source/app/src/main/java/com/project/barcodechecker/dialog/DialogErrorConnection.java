package com.project.barcodechecker.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.project.barcodechecker.R;

/**
 * Created by lucky on 05-Nov-17.
 */

public class DialogErrorConnection extends Dialog {

    public Activity activity;
    public Dialog d;
    public Button btnReload;
    public View.OnClickListener btnReloadListener;

    public DialogErrorConnection(Activity activity, View.OnClickListener btnReloadListener) {
        super(activity); 
        this.activity = activity;
        this.btnReloadListener = btnReloadListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_error);
        setCancelable(false);
        btnReload = (Button) findViewById(R.id.btn_reload_err_frag);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                btnReloadListener.onClick(v);
            }
        });
    }

}
