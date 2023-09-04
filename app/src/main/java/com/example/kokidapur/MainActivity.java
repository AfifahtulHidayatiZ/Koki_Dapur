package com.example.kokidapur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    FrameLayout frameLayout;
    private LinearLayout linearLayout;
    TabLayout tabLayout;
    ViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = (item) -> {
        if (item.getItemId()==R.id.nav_home)
        {
            linearLayout.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            return true;
        }else if (item.getItemId()==R.id.nav_bahan){
            linearLayout.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
            load_fragment_bottom(new BahanFragment());
            return true;
        }else if (item.getItemId()==R.id.nav_resep){
            linearLayout.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
            load_fragment_bottom(new ResepFragment());
            return true;
        }else {
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //hilangkan Toolbar
        setContentView(R.layout.activity_main);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.button_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        linearLayout = (LinearLayout) findViewById(R.id.LL_Tab);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        load_fragment_bottom(new HomeFragment());
        tabLayout = (TabLayout) findViewById(R.id.Tab_Layout);
        viewPager = (ViewPager) findViewById(R.id.View_Pager);

        Tab_Adapter tabAdapter = new Tab_Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId()==R.id.nav_home)
//                {
//                    fragment = new HomeFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
//                    return true;
//                }else if (item.getItemId()==R.id.nav_bahan){
//                    fragment = new BahanFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
//                    return true;
//                }else if (item.getItemId()==R.id.nav_resep){
//                    fragment = new ResepFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
//                    return true;
//                }else {
//                    return false;
//                }
//
//            }
//        });
    }

    Boolean load_fragment_bottom(Fragment fragment){
        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            return true;
        }
        return false;
    }

}

class Tab_Adapter extends FragmentStatePagerAdapter{
    int jumlahTab;

    public Tab_Adapter(@NonNull FragmentManager fm, int jmlTab) {
        super(fm);
        this.jumlahTab = jmlTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabMenuHariIni hariini = new TabMenuHariIni();
                return hariini;
            case 1:
                TabMenuMingguIni mingguini = new TabMenuMingguIni();
                return mingguini;
            case 2:
                TabRiwayatMenu riwayat = new TabRiwayatMenu();
                return riwayat;
        }
        return null;
    }

    @Override
    public int getCount() {
        return jumlahTab;
    }
}