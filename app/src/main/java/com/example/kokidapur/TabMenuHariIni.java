package com.example.kokidapur;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kokidapur.adapter.AdapterTanggal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabMenuHariIni#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabMenuHariIni extends Fragment {
    private TextView DayView, DateView;
    private Handler handler;
    private Runnable updateRunable;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabMenuHariIni() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabMenuHariIni.
     */
    // TODO: Rename and change types and number of parameters
    public static TabMenuHariIni newInstance(String param1, String param2) {
        TabMenuHariIni fragment = new TabMenuHariIni();
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
        View root = inflater.inflate(R.layout.fragment_tab_menu_hari_ini, container, false);

        DayView = root.findViewById(R.id.TglHari_Home_Harini);
        DateView = root.findViewById(R.id.Tgl_Home_Harini);

        handler = new Handler(Looper.getMainLooper());

        updateRunable = new Runnable() {
            @Override
            public void run() {
                updateDateTime();
                handler.postDelayed(this, 1000);
            }
        };

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(updateRunable);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateRunable);
    }

    private void updateDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        String date = dateFormat.format(calendar.getTime());
        String day = dayFormat.format(calendar.getTime());

        DateView.setText(date);
        DayView.setText(day);
    }
}