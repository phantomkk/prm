package com.project.barcodechecker.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.HistoryAdapter;
import com.project.barcodechecker.models.Product;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private ListView lvHistory;
    private List<Product> list;
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
        lvHistory = (ListView) v.findViewById(R.id.list_history_frag_history);
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
        HistoryAdapter adapter = new HistoryAdapter(getContext(), R.layout.item_history, list);

        lvHistory.setAdapter(adapter);
        return v;
    }
}
