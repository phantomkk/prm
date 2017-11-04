package com.project.barcodechecker.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.project.barcodechecker.api.services.CategoryService;
import com.project.barcodechecker.api.services.CommentService;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.RatingService;
import com.project.barcodechecker.api.services.SaleService;
import com.project.barcodechecker.fragments.CommentFragment;
import com.project.barcodechecker.fragments.SaleUserFragment;
import com.project.barcodechecker.fragments.SuggestFragment;
import com.project.barcodechecker.fragments.UserFragment;
import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Rating;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;
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
    private SaleUserFragment saleUserFragment;
    private SuggestFragment suggestFragment;
    private ProgressBar pbSale, pbComment, pbSuggest;
    private Button btnAddSale;
    private ImageButton btnAddSalePrice;
    private TextInputLayout priceWrapper;
    private EditText edtPrice;
    private LinearLayout linearLayout;
    User u = null;
    private DecimalFormat formatter = new DecimalFormat("###,###,###.##VNĐ");
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
        loadSale(product);
        loadSuggests(product);
        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSaleAnimation();
            }
        });
        btnAddSalePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    addSaleProcess();
                }
            }
        });
        priceWrapper.setHint("Giá Sản Phẩm");
    }

    private void bindingView() {
        btnAddSale = (Button) findViewById(R.id.btn_add_sale);
        btnAddSalePrice = (ImageButton) findViewById(R.id.add_price);
        priceWrapper = (TextInputLayout) findViewById(R.id.priceWrapper);
        linearLayout = (LinearLayout) findViewById(R.id.linearAdd);
        edtPrice = (EditText) findViewById(R.id.edt_price_sale);
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
        saleUserFragment = new SaleUserFragment();
        transaction.replace(R.id.frm_sale_product, saleUserFragment);
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
        txtPrice.setText(Utils.formatPrice(p.getPrice()));
        txtCompany.setText(p.getComanyName());
        txtDescription.setText(p.getDescription());
        txtContact.setText(p.getPhone());
        rbStar.setRating((float) p.getAverageRating());
        u = CoreManager.getUser(DetailActivity.this);
        logError(u == null ? ("u null") : ("u khong null" + u.getName()));
        if (u != null) {
            if (u.getRatingList() == null) {
            }
            if (u.getRatingList() != null) {
                if (getRating(u) != null) {

                }
            }
        }
        rbStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Log.e("ERROR", "UP");
                    if (u == null) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                        dialog.setMessage("Bạn cần phải đăng nhập để đánh giá sản phẩm này!")
                                .setPositiveButton("Đăng nhập", positiveListener)
                                .setNegativeButton("Hủy", null)
                                .show();
                    } else {
                        Rating rate = getRating(u);
                        if (rate != null) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                            dialog.setMessage("Bạn đã đánh giá sản phẩm này " + rate.getRating() + " sao.");
                            dialog.setPositiveButton("OK", null);
                            dialog.show();
                            rbStar.setRating((float) product.getAverageRating());
                        } else {
                            rate = new Rating();
                            rate.setProductID(product.getId());
                            rate.setUserID(u.getId());
                            rate.setRating(rating);
                            logError(DetailActivity.class.getSimpleName(), "setValues", "Tét rating" + rate.getRating() + " rating" +
                                    "proID: " + rate.getProductID() + " userid " + rate.getUserID());
                            RatingService ratingService = APIServiceManager.getRatingService();
                            showLoading();
                            ratingService.postRating(rate).enqueue(new Callback<Rating>() {
                                @Override
                                public void onResponse(Call<Rating> call, Response<Rating> response) {
                                    if (response.isSuccessful()) {
//                                    logError(DetailActivity.class.getSimpleName(), "setValues", "Post rating success" + response.body().getRating() + " rating" +
//                                            "proID: " + response.body().getProductID() + " userid " + response.body().getUserID());
                                        u.getRatingList().add(response.body());
                                        CoreManager.setUser(DetailActivity.this, u);
//                                    rbStar.setIsIndicator(true);
                                        showMessage("Đánh giá sản phẩm thành công.");
                                    } else {
                                        logError(DetailActivity.class.getSimpleName(), "setValues", "Post rating onResponse but else" + response.code());

                                        rbStar.setRating((float) product.getAverageRating());
                                    }
                                    try {
                                        logError(response, "DetailActivity");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    hideLoading();
                                }

                                @Override
                                public void onFailure(Call<Rating> call, Throwable t) {
                                    logError(DetailActivity.class.getSimpleName(), "setValues", "OnFailure " + t.getMessage());
                                    hideLoading();
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private Rating getRating(User user) {
        List<Rating> list = user.getRatingList();
        if (list == null) {
            return null;
        }
        for (Rating r : list) {
            if (r.getProductID() == product.getId()) {
                return r;
            }
        }
        return null;

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
            SaleService saleService = APIServiceManager.getSaleService();
            saleService.getSaleByProductID(product.getId()).enqueue(new Callback<List<Sale>>() {
                @Override
                public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                    if (response.isSuccessful()) {
                        listSale = response.body();
                        saleUserFragment.setData(listSale);
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
                                if (!product.getId().equals(listProducts.get(i).getId())) {
                                    productList.add(listProducts.get(i));
                                }
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
        u = CoreManager.getUser(this);
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
            showLoading("Bình luận đang được đăng...");
            commentService.postComment(comment).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.isSuccessful()) {
                        logError(response.body().getUserAvatar());
                        commentFragment.updateData(response.body());
                    } else {
                        edtCmt.setText("EROR");
                        logError(DetailActivity.class.getSimpleName(), "postCmtClickListener", "ELSE API postComment");
                    }
                    try {
                        logErrorBody(response);
                    } catch (IOException e) {
                        e.printStackTrace();
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
            startActivityForResult(new Intent(DetailActivity.this, LoginActivity.class), Utils.USE_VIEW_DETAIL);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Utils.USE_VIEW_DETAIL) {
                setUpAvatar();
//                detailActitivityListenner.setUpAvatarInToolBar();
                u = CoreManager.getUser(this);
                commentFragment.setUpAvatarInToolBar();
            }
        }
    }

    private DetailActitivityListenner detailActitivityListenner;
    private boolean addClick = false;

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

    private void addSaleProcess() {
        showLoading();
        Date date = Calendar.getInstance().getTime();
        Sale sale = new Sale();
        sale.setDateCreate(new SimpleDateFormat(AppConst.DATE_AND_TIME_SQL, Locale.US).format(date));
        sale.setPrice(Float.parseFloat(edtPrice.getText().toString()));
        sale.setUserId(CoreManager.getUser(this).getId());
        sale.setProductId(product.getId());
        SaleService saleService = APIServiceManager.getSaleService();
        showLoading();
        saleService.addSale(sale).enqueue(new Callback<Sale>() {
            @Override
            public void onResponse(Call<Sale> call, Response<Sale> response) {
                if (response.isSuccessful()) {
                    linearLayout.setVisibility(View.GONE);
                    btnAddSale.setEnabled(false);
                    btnAddSale.setText("Thêm sản phẩm");
                    Toast.makeText(DetailActivity.this, "Đăng sản phẩm thành công.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Đăng sản phẩm lỗi. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    try {
                        logError(response,"addSale");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<Sale> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Đăng sản lỗi.Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                logError(DetailActivity.class.getSimpleName(), "addSale", "Failue API " + t.getCause());
                hideLoading();
            }
        });
    }

    private void addSaleAnimation() {
        User u = CoreManager.getUser(this);
        if (u == null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Bạn cần phải đăng nhập để bình luận!")
                    .setPositiveButton("Đăng nhập", positiveListener)
                    .setNegativeButton("Hủy", null)
                    .show();
        } else {
            boolean flag = false;
            for (Sale sale : listSale) {
                if (sale.getUserId() == CoreManager.getUser(this).getId()
                        && sale.getProductId() == product.getId()) {
                    flag = true;
                }
            }
            if (flag) {
                Toast.makeText(DetailActivity.this, "Bạn Đã Đăng bán sản phẩm này", Toast.LENGTH_SHORT).show();
                linearLayout.setVisibility(View.GONE);
            } else {
                if (!addClick) {
                    linearLayout.setVisibility(View.VISIBLE);
                    addClick = true;
                    btnAddSale.setText("Đóng");
                } else {
                    linearLayout.setVisibility(View.GONE);
                    addClick = false;
                    btnAddSale.setText("Thêm Sản Phẩm");
                }
            }
        }
    }

    public interface DetailActitivityListenner {
        public void setUpAvatarInToolBar();
    }

}
