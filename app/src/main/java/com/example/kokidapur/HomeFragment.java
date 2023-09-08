package com.example.kokidapur;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HomeFragment extends Fragment {
    private RecyclerView mrecyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
//
//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.Tab_Layout);
//
//        ViewPager viewPager = (ViewPager) view.findViewById(R.id.View_Pager);
//
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.LL_Tab);
//
//
//        LinearLayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
//        mlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
//        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        return view;
//    }
//
//    public class PagerAdapter extends FragmentStatePagerAdapter{
//        int mNumOfTabs;
//
//        public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
//            super(fm);
//            this.mNumOfTabs = NumOfTabs;
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            switch (position){
//                case 0:
//                    TabMenuHariIni hariIni= new TabMenuHariIni();
//                    return hariIni;
//                case 1:
//                    TabMenuMingguIni mingguIni = new TabMenuMingguIni();
//                    return mingguIni;
//                case 2:
//                    TabRiwayatMenu riwayatMenu = new TabRiwayatMenu();
//                    return riwayatMenu;
//
//                default:
//                    return null;
//            }
//
//        }
//
//        @Override
//        public int getCount() {
//            return mNumOfTabs;
//        }
    }

}