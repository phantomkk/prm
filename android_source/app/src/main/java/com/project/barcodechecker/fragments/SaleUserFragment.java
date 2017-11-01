package com.project.barcodechecker.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.UserDetailActivity;
import com.project.barcodechecker.adapters.SaleUserAdapter;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 30/10/2017.
 */

public class SaleUserFragment extends Fragment {
    private ListView rcvComment;
    private ArrayAdapter<Sale> adapter;
    private List<Sale> list;

    public SaleUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sale_user, container, false);
        rcvComment = (ListView) v.findViewById(R.id.list_sale_product);
        list = new ArrayList<>();
        adapter = new SaleUserAdapter(getContext(), R.layout.item_sale_user, this.list);

        rcvComment.setAdapter(adapter);
        Utils.setListViewHeightBasedOnItems(rcvComment);
        return v;
    }

    public void setData(final List<Sale> list) {
        this.list = list;
        adapter = new SaleUserAdapter(getContext(), R.layout.item_sale_user, this.list);

        rcvComment.setAdapter(adapter);
        rcvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = list.get(position).getUser();
                if (user == null) {
                    searchUserAndTranferToUserDetail(list.get(position).getUserId());
                } else {
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    intent.putExtra(AppConst.USER_PARAM, list.get(position).getUser());
                    startActivity(intent);
                }
//
            }
        });
        Utils.setListViewHeightBasedOnItems(rcvComment);
        adapter.notifyDataSetChanged();
    }

    private User user;

    public void searchUserAndTranferToUserDetail(int id) {
        UserService userService = APIServiceManager.getUserService();
        userService.getByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Intent intent = new Intent(getContext(), UserDetailActivity.class);
                    intent.putExtra(AppConst.USER_PARAM, user);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
