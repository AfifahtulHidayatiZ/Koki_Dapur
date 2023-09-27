package com.example.kokidapur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kokidapur.adapter.AdapterBelanja;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.DataBahan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BelanjaActivity extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<DataBahan> listbelanja = new ArrayList<>();
    AdapterBelanja adapterBelanja;
    private Helper dbhelper = new Helper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);


        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbBelanja);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dbhelper = new Helper(getApplicationContext());

        FloatingActionButton fabBelanja = findViewById(R.id.floatingBelanja);
        fabBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BelanjaActivity.this, TambahDaftarBelanjaActivity.class);
                startActivity(intent);
            }
        });


        listView = findViewById(R.id.LV_ListBelanja);
        adapterBelanja = new AdapterBelanja(this, listbelanja);
        listView.setAdapter(adapterBelanja);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_bahan = listbelanja.get(position).getId_bahan();
                final String nama_bahan = listbelanja.get(position).getNama_bahan();
                final String jumlah = listbelanja.get(position).getJumlah();
                final String status = listbelanja.get(position).getStatus();

                AlertDialog.Builder alertDB = new AlertDialog.Builder(BelanjaActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_belanja, null);
                alertDB.setView(dialogView);
                AlertDialog alertDialog = alertDB.create();
                TextView dialogtitle = dialogView.findViewById(R.id.Title_dialog_belanja);
                ImageButton tk_bahan = dialogView.findViewById(R.id.BtnTK_Bahan);
                Button editbtn = dialogView.findViewById(R.id.BtnEdit_belanja);
                Button deletebtn = dialogView.findViewById(R.id.BtnHapus_belanja);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogtitle.setText(nama_bahan);

                tk_bahan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhelper.updateBahan(Integer.parseInt(id_bahan), nama_bahan);
                        listbelanja.clear();
                        getDataBelanja();
                        Toast.makeText(BelanjaActivity.this, "Ditambahkan Ke Daftar Bahan", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BelanjaActivity.this, TambahDaftarBelanjaActivity.class);
                        intent.putExtra("id_bahan", id_bahan);
                        intent.putExtra("nama_bahan", nama_bahan);
                        intent.putExtra("jumlah", jumlah);
                        intent.putExtra("status","beli");
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });

                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhelper.deleteBahan(Integer.parseInt(id_bahan));
                        listbelanja.clear();
                        getDataBelanja();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });


        getDataBelanja();

    }


    //kembali ke fragment bahan
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment bahanFragment = new BahanFragment();
            fragmentTransaction.replace(R.id.fragment_container, bahanFragment);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void getDataBelanja() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBelanja();
        for (int i=0; i<rows.size(); i++){
            String id_bahan = rows.get(i).get("id_bahan");
            String nama_bahan = rows.get(i).get("nama_bahan");
            String jumlah = rows.get(i).get("jumlah");

            DataBahan databelanja = new DataBahan();
            databelanja.setId_bahan(id_bahan);
            databelanja.setNama_bahan(nama_bahan);
            databelanja.setJumlah(jumlah);

            listbelanja.add(databelanja);
        }
        adapterBelanja.notifyDataSetChanged();
    }

    public void onResume(){
        super.onResume();
        listbelanja.clear();
        getDataBelanja();
    }
}