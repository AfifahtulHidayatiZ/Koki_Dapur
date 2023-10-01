package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kokidapur.adapter.AdapterMRB;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.DataMRB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RiwayatMenuActivity extends AppCompatActivity {
    List<DataMRB> dataMRBList = new ArrayList<>();
    AdapterMRB adapterMRB;
    ListView listViewMenu;
    Helper dbhelper = new Helper(this);
    private String date;
    private Date dateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_menu);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbRewayatMenu);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dbhelper = new Helper(getApplicationContext());
        date = getIntent().getStringExtra("selectedDate");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateValue = input.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        TextView tvHari = (TextView) findViewById(R.id.TglHari_Riwayat);
        TextView tvTanggal = (TextView) findViewById(R.id.Tgl_Riwayati);
        tvHari.setText(new SimpleDateFormat("EEEE").format(dateValue)+",");
        tvTanggal.setText(new SimpleDateFormat("dd MMMM yyyy").format(dateValue));

        listViewMenu = findViewById(R.id.ListRiwayat_Menu);
        adapterMRB = new AdapterMRB(this, dataMRBList);
        listViewMenu.setAdapter(adapterMRB);

        getDataMenu(date);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataMRB dataMRB = dataMRBList.get(position);
                Intent intent = new Intent(RiwayatMenuActivity.this,LihatDetailMenu.class);
                intent.putExtra("id_menu",dataMRB.getId_menu());
                intent.putExtra("nama_menu",dataMRB.getNama_menu());
                startActivity(intent);
            }
        });


    }

    private void getDataMenu(String tanggal) {
        ArrayList<HashMap<String, String>> rows = dbhelper.getTanggalMenu(tanggal);
        dataMRBList.clear();
        for (int i=0; i<rows.size(); i++){
            String id = rows.get(i).get("id_menu");
            String nama = rows.get(i).get("nama_menu");

            DataMRB dataMRB = new DataMRB();
            dataMRB.setId_menu(id);
            dataMRB.setNama_menu(nama);

            dataMRBList.add(dataMRB);
        }
        Log.d("MRBList", dataMRBList.toString());
        adapterMRB.notifyDataSetChanged();
    }
}