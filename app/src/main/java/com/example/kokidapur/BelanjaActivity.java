package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kokidapur.R;
import com.example.kokidapur.adapter.AdapterBahan;
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
    AdapterBahan adapterBahan;
    private Helper dbhelper = new Helper(this);

//    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);

        dbhelper = new Helper(this);

        FloatingActionButton fabBelanja = findViewById(R.id.floatingBelanja);
        fabBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BelanjaActivity.this, TambahDaftarBelanjaActivity.class);
                startActivity(intent);
            }
        });


        listView = findViewById(R.id.LV_ListBelanja);
        adapterBahan = new AdapterBahan(this, listbelanja);
        listView.setAdapter(adapterBahan);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_bahan = listbelanja.get(position).getId_bahan();
                final String nama_bahan = listbelanja.get(position).getNama_bahan();
                final String status = listbelanja.get(position).getStatus();
                final CharSequence[] dialogItem = {"Edit", "Hapus","Masukan ke Bahan"};
                dialog = new AlertDialog.Builder(BelanjaActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(BelanjaActivity.this, TambahDaftarBelanjaActivity.class);
                                intent.putExtra("id_bahan", id_bahan);
                                intent.putExtra("nama_bahan", nama_bahan);
                                intent.putExtra("status","beli");
                                startActivity(intent);
                                break;
                            case 1:
                                dbhelper.deleteBahan(Integer.parseInt(id_bahan));
                                listbelanja.clear();
                                getDataBelanja();
                                break;
                            case 2:
                                dbhelper.updateBahan(Integer.parseInt(id_bahan), nama_bahan);
                                listbelanja.clear();
                                getDataBelanja();
                                Intent resultintent = new Intent();
                                setResult(BelanjaActivity.RESULT_OK, resultintent);
                                finish();
                                break;
                        }

                    }
                }).show();
                return true;
            }
        });
        getDataBelanja();



//        actionBar = getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getDataBelanja() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBelanja();
        for (int i=0; i<rows.size(); i++){
            String id_bahan = rows.get(i).get("id_bahan");
            String nama_bahan = rows.get(i).get("nama_bahan");

            DataBahan databelanja = new DataBahan();
            databelanja.setId_bahan(id_bahan);
            databelanja.setNama_bahan(nama_bahan);

            listbelanja.add(databelanja);
        }
        adapterBahan.notifyDataSetChanged();
    }

    public void onResume(){
        super.onResume();
        listbelanja.clear();
        getDataBelanja();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}