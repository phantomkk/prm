package com.project.barcodechecker.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.SaleService;
import com.project.barcodechecker.fragments.SaleProductFragment;
import com.project.barcodechecker.fragments.SaleUserFragment;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends BaseActivity {
    private CircleImageView imvAvatar;
    private TextView tvName,edtName, edtAddress, edtEmail, edtPhone, edtIntroduct, edtWeb;
    private User user;
    private SaleProductFragment saleUserFragment;
    private ProgressBar pbSale;
    private List<Sale> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Chi Tiết Người Dùng");
        tvName = (TextView) findViewById(R.id.txt_name);
        imvAvatar = (CircleImageView) findViewById(R.id.img_avatar_user);
        edtName = (TextView) findViewById(R.id.edt_name);
        edtAddress = (TextView) findViewById(R.id.edt_address);
        edtEmail = (TextView) findViewById(R.id.edt_email);
        edtPhone = (TextView) findViewById(R.id.edt_phone);
        edtIntroduct = (TextView) findViewById(R.id.edt_introduct);
        edtWeb = (TextView) findViewById(R.id.edt_website);
        pbSale = (ProgressBar) findViewById(R.id.process_sale);
        Intent previousPage = getIntent();
        Bundle b = previousPage.getExtras();
        if (b != null) {
            user = (User) (b.get(AppConst.USER_PARAM));
            setUserInfor(user);
        }
        setFragmentSale();
        loadSale(user);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_user_detail;
    }

    private void showLoad(ProgressBar progressBar, boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void setUserInfor(User user) {
        if (user != null) {
            tvName.setText(user.getUsername());
            edtName.setText(user.getName());
            edtAddress.setText(user.getAddress());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());
            edtIntroduct.setText(user.getIntroduce());
            edtWeb.setText(user.getWebsite());
            Picasso.with(this).load(user.getAvatar()).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.avatar).into(imvAvatar);
        }
    }

    private void setFragmentSale() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        saleUserFragment = new SaleProductFragment();
        transaction.replace(R.id.frm_sale_product, saleUserFragment);
        transaction.commit();
    }

    private void loadSale(User user) {
        if (user != null) {
            showLoad(pbSale, true);
            SaleService saleService = APIServiceManager.getSaleService();
            saleService.getSaleByUserID(user.getId()).enqueue(new Callback<List<Sale>>() {
                @Override
                public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                    if (response.isSuccessful()) {
                        listProducts = response.body();
                        saleUserFragment.setData(listProducts);
                    } else {
                    }
                    showLoad(pbSale, false);
                }

                @Override
                public void onFailure(Call<List<Sale>> call, Throwable t) {
                    showLoad(pbSale, false);
                }
            });
        } else {
        }
    }


}
