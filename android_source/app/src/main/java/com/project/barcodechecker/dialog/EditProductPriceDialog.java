package com.project.barcodechecker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 04/11/2017.
 */

public class EditProductPriceDialog extends Dialog {
    private Sale sale;
    private TextView txtCode, txtName, txtPrice, txtCategory;
    private ImageView imgProduct;
    private TextInputLayout priceWrapper;
    private EditText edtPrice;
    private Button btnAddSale;
    private Context context;
    private int position;
    private EditProductPriceListener mCallBack;


    public interface EditProductPriceListener {
        public void editProductPrice(Sale sale, int position, float newPrice);
    }

    public EditProductPriceDialog(@NonNull Context context, Sale sale, int position, EditProductPriceListener mCallBack) {
        super(context);
        this.sale = sale;
        this.context = context;
        this.mCallBack = mCallBack;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_price_dialog);
        bindingView();
        Picasso.with(context).load(sale.getProduct().getImgDefault()).into(imgProduct);
        txtCode.setText(sale.getProduct().getCode());
        txtName.setText(sale.getProduct().getName());
        txtPrice.setText(sale.getProduct().getPrice().toString() + "đ");
        txtCategory.setText(Utils.getCategoryString(sale.getProduct().getCategoryID()));
        priceWrapper.setHint("Giá mởi của sản phẩm");
        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    mCallBack.editProductPrice(sale, position, Float.parseFloat(edtPrice.getText().toString()));
                    dismiss();
                }
            }
        });
    }


    private boolean isValid() {
        boolean flag = true;
        if (edtPrice.getText().toString().trim().isEmpty()) {
            flag = false;
            priceWrapper.setError("Giá không được để trống");
        } else {
            priceWrapper.setErrorEnabled(false);
        }
        return flag;
    }

    private void bindingView() {
        imgProduct = (ImageView) findViewById(R.id.img_product_detail_atv);
        btnAddSale = (Button) findViewById(R.id.btn_update_sale);
        priceWrapper = (TextInputLayout) findViewById(R.id.priceWrapper);
        edtPrice = (EditText) findViewById(R.id.edt_price_sale);
        txtCode = (TextView) findViewById(R.id.txt_code_detail_atv);
        txtName = (TextView) findViewById(R.id.txt_name_detail_atv);
        txtPrice = (TextView) findViewById(R.id.txt_price_detail_atv);
        txtCategory = (TextView) findViewById(R.id.txt_product_category);
    }

}
