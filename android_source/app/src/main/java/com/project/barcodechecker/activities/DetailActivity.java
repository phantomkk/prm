package com.project.barcodechecker.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;

import java.util.Locale;

public class DetailActivity extends BaseActivity {
    private TextView txtCode, txtName, txtPrice, txtCompany, txtContact, txtDescription;
    private RatingBar rbStar;
    private ImageView imgProduct;
    private Product product;
    private FrameLayout frameComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView();
        initToolbar();
        setToolbarTitle("Xem chi tiết sản phẩm");

        //get item
        Intent previousPage = getIntent();
        Bundle b = previousPage.getExtras();
        if (b != null) {
            product = (Product) (b.get(AppConst.PRODUCT_PARAM));
            setValues(product);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    private void bindingView() {
        txtCode = (TextView) findViewById(R.id.txt_code_detail_atv);
        txtName = (TextView) findViewById(R.id.txt_name_detail_atv);
        txtPrice = (TextView) findViewById(R.id.txt_price_detail_atv);
        txtCompany = (TextView) findViewById(R.id.txt_company_detail_atv);
        txtDescription = (TextView) findViewById(R.id.txt_description_detail_atv);
        txtContact = (TextView) findViewById(R.id.txt_contact_detail_atv);
        imgProduct = (ImageView) findViewById(R.id.img_product_detail_atv);
        rbStar = (RatingBar) findViewById(R.id.rbar_star);
        frameComments = (FrameLayout) findViewById(R.id.frm_comment_detail_atv);
    }

    private void setValues(Product p) {
        if (p == null) {
            p = new Product();
            showMessage("An error occur!");
            Log.e("ERROR", "DetailActivity.setValues(*): Product null");
        }
        txtCode.setText(p.getCode());
        txtName.setText(p.getName());
        txtPrice.setText(String.format(Locale.US, "%1f", p.getPrice()));
//        txtCompany.setText();
        txtDescription.setText(p.getDescription());
        txtContact.setText(p.getPhone());
    }

}
