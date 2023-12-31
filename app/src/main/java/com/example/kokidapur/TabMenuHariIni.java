package com.example.kokidapur;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kokidapur.adapter.AdapterMRB;
import com.example.kokidapur.adapter.AdapterTanggal;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.DataMRB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    List<DataMRB> dataMRBList = new ArrayList<>();
    AdapterMRB adapterMRB;
    Helper dbhelper = new Helper(getActivity());
    ListView listViewMenu;
    private String newformat;
    private TextView emptyText;


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

        emptyText = root.findViewById(R.id.EmptyText);
        dbhelper = new Helper(getActivity().getApplicationContext());
        DayView = root.findViewById(R.id.TglHari_Home_Harini);
        DateView = root.findViewById(R.id.Tgl_Home_Harini);

        handler = new Handler(Looper.getMainLooper());

        updateDateTime();
        updateRunable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
            }
        };

        listViewMenu = root.findViewById(R.id.List_HariIniMenu);
        adapterMRB = new AdapterMRB(getActivity(), dataMRBList);
        listViewMenu.setAdapter(adapterMRB);
        getDataMenu(newformat);
        Log.d("Lihat Hari", newformat);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_menumrb = dataMRBList.get(position).getId_menu();
                final String nama_menumrb = dataMRBList.get(position).getNama_menu();
                Intent intent = new Intent(getActivity(), LihatDetailMenu.class);
                intent.putExtra("id_menu", id_menumrb);
                intent.putExtra("nama_menu", nama_menumrb);
                startActivity(intent);
            }
        });

        return root;
    }

    private void getDataMenu(String tanggal) {
        ArrayList<HashMap<String, String>> rows = dbhelper.getTanggalMenu(tanggal);
        dataMRBList.clear();
        for(int i=0; i< rows.size(); i++){
            String id = rows.get(i).get("id_menu");
            String nama = rows.get(i).get("nama_menu");

            DataMRB dataMRB = new DataMRB();
            dataMRB.setId_menu(id);
            dataMRB.setNama_menu(nama);

            dataMRBList.add(dataMRB);
        }
        adapterMRB.notifyDataSetChanged();

        if (dataMRBList.isEmpty()){
            listViewMenu.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
        else {
            listViewMenu.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        dataMRBList.clear();
        getDataMenu(newformat);
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

        try {
            Date datevalue = dateFormat.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            newformat = sdf.format(datevalue);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}