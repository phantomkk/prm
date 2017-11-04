package com.project.barcodechecker.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.icu.util.Calendar;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.databaseHelper.ProductDatabaseHelper;
import com.project.barcodechecker.models.History;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragment extends LoadingFragment implements MessageDialogFragment.MessageDialogListener,
        ZXingScannerView.ResultHandler{
    private static ScanFragment instance = new ScanFragment();
    public static ScanFragment newInstance() {
        ScanFragment fragment = new ScanFragment();
        return fragment;
    }
    public static ScanFragment getInstance() {
        return instance;
    }

    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";
    private ZXingScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<Integer> mSelectedIndices;
    private int mCameraId = 0;
    private ProductService pService;
    private ImageButton mBtnFlash, mCamera, mFocus, mFormat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);

        mBtnFlash =(ImageButton) v.findViewById(R.id.btn_torch);
        mCamera =(ImageButton) v.findViewById(R.id.btn_camera);
        mFocus =(ImageButton) v.findViewById(R.id.btn_focus);
        mFormat =(ImageButton) v.findViewById(R.id.btn_format);
        mScannerView =(ZXingScannerView) v.findViewById(R.id.scanner_view);
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = state.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = state.getInt(CAMERA_ID, 0);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = 0;
        }
        mBtnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlash){
                    mBtnFlash.setBackgroundResource(R.drawable.ic_flash_off);
                    mFlash=false;
                }else{
                    mBtnFlash.setBackgroundResource(R.drawable.ic_flash_on);
                    mFlash=true;
                }
                mScannerView.setFlash(mFlash);
            }
        });
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScannerView.stopCamera();
                if(mCameraId == 0){
                    mCameraId=1;
                    mCamera.setBackgroundResource(R.drawable.ic_camera_font);
                }else{
                    mCameraId=0;
                    mCamera.setBackgroundResource(R.drawable.ic_camera_back);
                }
                setCamera();
            }
        });
        mFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAutoFocus){
                    mFocus.setBackgroundResource(R.drawable.ic_focus_off);

                }else{
                    mFocus.setBackgroundResource(R.drawable.ic_focus_on);
                }
                mAutoFocus = !mAutoFocus;
                mScannerView.setAutoFocus(mAutoFocus);
            }
        });

        setupFormats();
        mFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormatSelectorDialogFragment fragment = FormatSelectorDialogFragment.newInstance(new FormatSelectorDialogFragment.FormatSelectorDialogListener() {
                    @Override
                    public void onFormatsSaved(ArrayList<Integer> selectedIndices) {
                        mSelectedIndices = selectedIndices;
                        setupFormats();
                    }
                }, mSelectedIndices);
                fragment.show(getFragmentManager(), "format_selector");
            }
        });
        return v;
    }

    public void setCamera(){
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
//        setHasOptionsMenu(true);
    }

//    @Override
//    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem menuItem;
//        if(mAutoFocus) {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_on);
//        } else {
//            menuItem = menu.add(Menu.NONE, R.id.menu_auto_focus, 0, R.string.auto_focus_off);
//        }
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_formats, 0, R.string.formats);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//
//        menuItem = menu.add(Menu.NONE, R.id.menu_camera_selector, 0, R.string.select_camera);
//        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_NEVER);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle presses on the action bar items
//        switch (item.getItemId()) {
//            case R.id.menu_auto_focus:
//                mAutoFocus = !mAutoFocus;
//                if(mAutoFocus) {
//                    item.setTitle(R.string.auto_focus_on);
//                } else {
//                    item.setTitle(R.string.auto_focus_off);
//                }
//                mScannerView.setAutoFocus(mAutoFocus);
//                return true;
//            case R.id.menu_formats:
//                FormatSelectorDialogFragment fragment = FormatSelectorDialogFragment.newInstance(this, mSelectedIndices);
//                fragment.show(getFragmentManager(), "format_selector");
//                return true;
//            case R.id.menu_camera_selector:
//                mScannerView.stopCamera();
//                CameraSelectorDialogFragment cFragment = CameraSelectorDialogFragment.newInstance(this, mCameraId);
//                cFragment.show(getFragmentManager(), "camera_selector");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        //setHasOptionsMenu(isVisible());
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    @Override
    public void handleResult(final Result rawResult) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity().getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {}
        showLoading();
        final String code = rawResult.getText();
        pService = APIServiceManager.getPService();
        pService.getProductByCode(code).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                hideLoading();
                if (response.isSuccessful()) {
                    Product product = response.body();
                    ProductDatabaseHelper historyDatabaseHelper = new ProductDatabaseHelper(getContext());
                    historyDatabaseHelper.addProduct(product);
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(AppConst.PRODUCT_PARAM, product);
                    startActivity(intent);
                } else {
                    showMessageDialog("Successful but else");
                    Product product = new Product();
                    product.setCode(code);
                    product.setName(getString(R.string.string_not_fount));
                    ProductDatabaseHelper historyDatabaseHelper = new ProductDatabaseHelper(getContext());
                    historyDatabaseHelper.addProduct(product);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                hideLoading();
                showMessageDialog("Fail");
            }
        });
    }

    public void showMessageDialog(String message) {
        DialogFragment fragment = MessageDialogFragment.newInstance(message, this);
        fragment.show(getFragmentManager(), "scan_results");
    }

    public void closeMessageDialog() {
        closeDialog("scan_results");
    }

    public void closeFormatsDialog() {
        closeDialog("format_selector");
    }

    public void closeDialog(String dialogName) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(dialogName);
        if(fragment != null) {
            fragment.dismiss();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.resumeCameraPreview(this);
    }



    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        closeMessageDialog();
        closeFormatsDialog();
    }

}
