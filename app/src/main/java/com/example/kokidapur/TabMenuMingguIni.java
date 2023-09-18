package com.example.kokidapur;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kokidapur.adapter.AdapterTanggal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabMenuMingguIni#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabMenuMingguIni extends Fragment {
    private ListView listView;
    private AdapterTanggal adapterTanggal;
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabMenuMingguIni() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabMenuMingguIni.
     */
    // TODO: Rename and change types and number of parameters
    public static TabMenuMingguIni newInstance(String param1, String param2) {
        TabMenuMingguIni fragment = new TabMenuMingguIni();
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
        View root = inflater.inflate(R.layout.fragment_tab_menu_minggu_ini, container, false);

        listView = root.findViewById(R.id.LV_MenuMingguIni);
        adapterTanggal = new AdapterTanggal(getActivity(), days, dates);
        listView.setAdapter(adapterTanggal);

        //Mengisi data dari dan tanggal untuk 1 Minggu
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        for (int i=0; i<7; i++){
            String day = dayFormat.format(calendar.getTime());
            String date = dateFormat.format(calendar.getTime());

            days.add(day);
            dates.add(date);

            //Maju 1 minggu
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        adapterTanggal.notifyDataSetChanged();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//                String day = dayFormat.format(calendar.getTime());
//                String date = dateFormat.format(calendar.getTime());
//
//                days.add(day);
//                dates.add(date);
//
//                adapterTanggal.notifyDataSetChanged();
//
//                handler.postDelayed(this, 1000);
//
//            }
//        }, 0);

        return root;
    }
}