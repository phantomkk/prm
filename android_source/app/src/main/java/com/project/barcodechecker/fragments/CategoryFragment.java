package com.project.barcodechecker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.ListProductActivity;
import com.project.barcodechecker.adapters.CategoryAdapter;
import com.project.barcodechecker.models.MenuItem;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends LoadingFragment {
    private ListView lvCategory;
    private List<MenuItem> list;
    private static CategoryFragment instance;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    public CategoryFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        lvCategory = (ListView) v.findViewById(R.id.lv_menu_category);

        list = new ArrayList<>();
        list.add(new MenuItem(R.drawable.ic_phone_iphone_black_24dp, "Điện thoại"));
        list.add(new MenuItem(R.drawable.ic_print_black_24dp, "Đồ điện tử"));
        list.add(new MenuItem(R.drawable.ic_local_bar_black_24dp, "Nước giải khát"));
        list.add(new MenuItem(R.drawable.ic_home_black_24dp, "Đồ gia dụng"));
        list.add(new MenuItem(R.drawable.ic_event_note_black_24dp, "Dụng cụ học tập"));
        list.add(new MenuItem(R.drawable.ic_motorcycle_black_24dp, "Thể thao"));
        list.add(new MenuItem(R.drawable.ic_shopping_cart_black_24dp, "Trang sức"));
        CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.item_category, list, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(View v, int position) {
                Intent intent = new Intent(getContext(), ListProductActivity.class);
                intent.putExtra(AppConst.CATEGORY_PARAM, position + 1);
                getContext().startActivity(intent);
            }
        });
        lvCategory.setAdapter(adapter);
        lvCategory.setDivider(null);
        return v;
    }

}
