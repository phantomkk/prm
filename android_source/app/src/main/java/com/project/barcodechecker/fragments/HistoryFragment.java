package com.project.barcodechecker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.DetailActivity;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.utils.AppConst;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private ListView lvHistory;
    private List<Product> list;
    SwipeMenuListView listView;
    private static HistoryFragment instance= new HistoryFragment();
    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    public static HistoryFragment getInstance(){
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (SwipeMenuListView) v.findViewById(R.id.list_history_frag_history);
        list = new ArrayList<>();
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        list.add(new Product(1, 1, "Baocaosu", 999d, "Vietname","nguyen van luong", "0255255225", "luc@email.com", "0223652665", "No description"));
        HistoryAdapter adapter = new HistoryAdapter(getContext(),R.layout.item_history,list);

        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getActivity().getApplicationContext());
                item1.setBackground(R.color.color_red_50);
                item1.setWidth(dp2px(60));
                item1.setIcon(R.drawable.ic_card_travel_black_24dp);
                menu.addMenuItem(item1);
            }
        };
        //set icon for listview
        listView.setMenuCreator(creator);
        //hide devider of item in list
        listView.setDivider(null);
        //show confirm when item click
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        //showConfirmDeleteDialog(position);
                        break;
                }
                return false;
            }
        });
        //add event when click item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConst.PRODUCT_PARAM, list.get(position));
                startActivity(intent);
            }
        });



        return v;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
