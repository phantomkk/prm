package com.project.barcodechecker.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.ProductAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.CategoryService;
import com.project.barcodechecker.api.services.CommentService;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.fragments.CommentFragment;
import com.project.barcodechecker.fragments.SaleFragment;
import com.project.barcodechecker.fragments.SuggestFragment;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends BaseActivity implements CommentFragment.ButtonPostCommentClickListener {
    private TextView txtCode, txtName, txtPrice, txtCompany, txtContact, txtDescription;
    private RatingBar rbStar;
    private ImageView imgProduct;
    private Product product;
    private FrameLayout frameComments, frameSales, frameSuggests;
    private List<Comment> list;
    private List<Sale> listSale;
    private List<Product> listProducts;
    private CommentFragment commentFragment;
    private SaleFragment saleFragment;
    private SuggestFragment suggestFragment;
    private ProgressBar pbSale, pbComment, pbSuggest;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView();
//        initToolbar();
        setFragmentComment();
        setFragmentSale();
        setFragmentSuggest();
        setToolbarTitle("Xem chi tiết sản phẩm");
        //get item
        Intent previousPage = getIntent();
        Bundle b = previousPage.getExtras();
        if (b != null) {
            product = (Product) (b.get(AppConst.PRODUCT_PARAM));
            setValues(product);
        }
        loadComments(product);
//        loadSale(product);
        loadSuggests(product);
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
        frameSales = (FrameLayout) findViewById(R.id.frm_sale_product);
        frameSuggests = (FrameLayout) findViewById(R.id.frm_suggest);
        pbSale = (ProgressBar) findViewById(R.id.process_sale);
        pbComment = (ProgressBar) findViewById(R.id.process_comment);
        pbSuggest = (ProgressBar) findViewById(R.id.process_suggest);
    }


    private void showLoad(ProgressBar progressBar, boolean isShow) {
        if (isShow) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setFragmentComment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        commentFragment = new CommentFragment();
        transaction.replace(R.id.frm_comment_detail_atv, commentFragment);
        transaction.commit();
    }

    private void setFragmentSale() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        saleFragment = new SaleFragment(1);
        transaction.replace(R.id.frm_sale_product, saleFragment);
        transaction.commit();
    }

    private void setFragmentSuggest() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        suggestFragment = new SuggestFragment();
        transaction.replace(R.id.frm_suggest, suggestFragment);
        transaction.commit();
    }


    private void setValues(Product p) {
        if (p == null) {
            p = new Product();
            showMessage("An error occur!");
            Log.e("ERROR", "DetailActivity.setValues(*): Product null");
            logError(DetailActivity.class.getSimpleName(), "setValues", "Product null");
        }
        Picasso.with(this).load(p.getImgDefault()).into(imgProduct);
        txtCode.setText(p.getCode());
        txtName.setText(p.getName());
        txtPrice.setText(String.format(Locale.US, "%3f", p.getPrice()));
//        txtCompany.setText();
        txtDescription.setText(p.getDescription());
        txtContact.setText(p.getPhone());
        rbStar.setRating((float) p.getAverageRating());
    }

    private void loadComments(Product product) {
        if (product != null) {
            commentFragment.setProduct(product);
            showLoad(pbComment, true);
            ProductService productService = APIServiceManager.getPService();
            productService.getProductComments(product.getId()).enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if (response.isSuccessful()) {
                        list = response.body();
                        commentFragment.setData(list);
                    } else {
                        logError(DetailActivity.class.getSimpleName(), "loadComments", "ELSE ");
                    }
                    showLoad(pbComment, false);
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {
                    showLoad(pbComment, false);
                    logError(DetailActivity.class.getSimpleName(), "loadComments", "Failure API " + t.getMessage());
                }
            });
        } else {
            logError(DetailActivity.class.getSimpleName(), "loadComments", "PRODUCT null");
        }
    }

    private void loadSale(Product product) {
        if (product != null) {
            showLoad(pbSale, true);
            ProductService productService = APIServiceManager.getPService();
            productService.getProductSales(product.getId()).enqueue(new Callback<List<Sale>>() {
                @Override
                public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                    if (response.isSuccessful()) {
                        listSale = response.body();
                        saleFragment.setData(listSale);
                    } else {
                        logError(DetailActivity.class.getSimpleName(), "loadSale", "ELSE ");
                    }
                    showLoad(pbSale, false);
                }

                @Override
                public void onFailure(Call<List<Sale>> call, Throwable t) {
                    showLoad(pbSale, false);
                    logError(DetailActivity.class.getSimpleName(), "loadSale", "Failure API " + t.getMessage());
                }
            });
        } else {
            logError(DetailActivity.class.getSimpleName(), "loadSale", "PRODUCT null");
        }
    }

    private void loadSuggests(final Product product) {
        if (product != null) {
            showLoad(pbSuggest, true);
            CategoryService categoryService = APIServiceManager.getCategoryService();
            categoryService.getProductByCategoryId(product.getCategoryID()).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        listProducts = response.body();
                        List<Product> productList = new ArrayList<Product>();
                        if (listProducts.size() > 10) {
                            for (int i = 0; i < 10; i++) {
                                productList.add(listProducts.get(i));
                            }
                            suggestFragment.setData(productList);
                        } else {
                            suggestFragment.setData(listProducts);
                        }
                    } else {
                        logError(DetailActivity.class.getSimpleName(), "loadSuggests", "ELSE ");
                    }
                    showLoad(pbSuggest, false);
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    showLoad(pbSuggest, false);
                    logError(DetailActivity.class.getSimpleName(), "loadSuggests", "Failure API " + t.getMessage());
                }
            });
        } else {
            logError(DetailActivity.class.getSimpleName(), "loadComments", "PRODUCT null");
        }
    }


    @Override
    public void postCmtClickListener(String content, final EditText edtCmt, TextView txtError) {
        if (content.length() == 0) {
            txtError.setVisibility(View.VISIBLE);
            return;
        } else {
            txtError.setVisibility(View.INVISIBLE);
        }
        User u = CoreManager.getUser(this);
        if (u == null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Bạn cần phải đăng nhập để bình luận!")
                    .setPositiveButton("Đăng nhập", positiveListener)
                    .setNegativeButton("Hủy", null)
                    .show();
        } else {
            Date date = Calendar.getInstance().getTime();
            final Comment comment = new Comment();
            comment.setUserID(u.getId());
            comment.setComment(content);
            comment.setName(u.getName());
            comment.setUserAvatar(u.getAvatar());
            comment.setUserID(u.getId());
            comment.setProductID(product == null ? 1 : product.getId());
            comment.setDateCreated(new SimpleDateFormat(AppConst.DATE_AND_TIME_SQL, Locale.US).format(date));
            CommentService commentService = APIServiceManager.getCommentService();
            showLoading();
            commentService.postComment(comment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()) {
                        commentFragment.updateData(response.body());
                    } else {
                        edtCmt.setText("EROR");
                        logError(DetailActivity.class.getSimpleName(), "postCmtClickListener", "ELSE API postComment");
                    }
                    edtCmt.setText("");
                    hideLoading();
                    Toast.makeText(DetailActivity.this, "Đăng bình luận thành công.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    logError(DetailActivity.class.getSimpleName(), "postCmtClickListener", "Failue API " + t.getCause());
                    edtCmt.setText("");
                    hideLoading();
                }
            });
        }
    }

    final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(DetailActivity.this, LoginActivity.class));
        }
    };
}
