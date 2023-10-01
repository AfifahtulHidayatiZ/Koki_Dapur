package com.example.kokidapur;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.kokidapur.adapter.AdapterMRB;
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
 * Use the {@link TabRiwayatMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabRiwayatMenu extends Fragment {
    DatePicker datePicker;
    List<DataMRB> dataMRBList = new ArrayList<>();
    AdapterMRB adapterMRB;
    Helper dbhelper = new Helper(getActivity());
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String selectedDate;

    public TabRiwayatMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabRiwayatMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static TabRiwayatMenu newInstance(String param1, String param2) {
        TabRiwayatMenu fragment = new TabRiwayatMenu();
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
        View root =  inflater.inflate(R.layout.fragment_tab_riwayat_menu, container, false);
        dbhelper = new Helper(getActivity().getApplicationContext());

        datePicker = root.findViewById(R.id.Tanggal_Riwayat);
        adapterMRB = new AdapterMRB(getActivity(),dataMRBList);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        selectedDate = sdf.format(calendar.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear+1, dayOfMonth);
//                    getDataMenu(selectedDate);
                    Log.i("Tanggal", selectedDate);

//                String id_menu = getDataMenu(selectedDate);
//                String nama_menu = getDataMenu(selectedDate);

//                if (id_menu !=null && nama_menu !=null){
//                    Intent intent = new Intent(getActivity(), DetailMenu.class);
//                    intent.putExtra("selectedDate", selectedDate);
//
//                    intent.putExtra("id_menu", id_menu);
//                    intent.putExtra("nama_menu", nama_menu);
//                    startActivity(intent);
//                }
//                else {
//                    Toast.makeText(getActivity(), "Tidak ada menu pada tanggal "+selectedDate, Toast.LENGTH_SHORT).show();
//                }
                }
            });
        }

        Button btnriwayat = (Button) root.findViewById(R.id.Btn_RiwayatMenu);
        btnriwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RiwayatMenuActivity.class);
                intent.putExtra("selectedDate", selectedDate);
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
    }


    @Override
    public void onResume() {
        super.onResume();
        dataMRBList.clear();
//        getDataMenu();
    }
}