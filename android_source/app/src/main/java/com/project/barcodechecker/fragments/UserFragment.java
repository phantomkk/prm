package com.project.barcodechecker.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.adapters.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private static UserFragment instance = new UserFragment();
    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager_accout);
        setupViewPager(viewPager);
        tabLayout= (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
       // tabLayout.getTabAt(0).select();
        return view;
    }

    public static UserFragment getInstance() {
        return instance;
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new AccoutFragment());
        adapter.addFragment(new ListProductFragment());
        adapter.addFragment(new NotificationFragment());
        viewPager.setAdapter(adapter);
    }


    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.tab_accout));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_account_box_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getString(R.string.tab_my_product));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_content_paste_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.tab_message));
        tabThree.setText(getString(R.string.tab_message));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_mail_white_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }



}
