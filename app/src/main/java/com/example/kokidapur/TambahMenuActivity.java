package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kokidapur.adapter.Adapter;
import com.example.kokidapur.adapter.AdapterBahan;
import com.example.kokidapur.adapter.AdapterMRB;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.Data;
import com.example.kokidapur.model.DataBahan;
import com.example.kokidapur.model.DataMRB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TambahMenuActivity extends AppCompatActivity {
    FloatingActionButton fabTambahMenu,fabTambahBahan, fabTambahResep;
    List<DataBahan> dataBahanList = new ArrayList<>();
    List<Data> dataResepList = new ArrayList<>();
    List<DataMRB> dataMRBList = new ArrayList<>();

    AdapterBahan adapterBahan;
    Adapter adapterResep;
    AlertDialog.Builder dialog;

    AdapterMRB adapterMRB;
    Helper dbhelper = new Helper(this);
    ListView listViewMenu, listViewBahan, listViewResep;
    DataMRB dataMRB;
    private boolean isEdit = false;
    private EditText editnama_menu;
    private String id_menu, nama_menu, newformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbTambahMenu);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dbhelper = new Helper(getApplicationContext());

        //Mengambil tanggal yang dikirim dari listView
        String selectionDay = getIntent().getStringExtra("selectionDay");
        String selectionDate = getIntent().getStringExtra("selectionDate");

        //Merubah format tanggal dari string menjadi Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        try {
            Date datevalue = simpleDateFormat.parse(selectionDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            newformat = sdf.format(datevalue);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Menampilkan tanggal di textView
        TextView dayTextView  = findViewById(R.id.TglHari_MenuHariIni);
        TextView dateTextView = findViewById(R.id.Tgl_MenuHariIni);
        dayTextView.setText(selectionDay);
        dateTextView.setText(selectionDate);

        editnama_menu = findViewById(R.id.InputNamaMenu);
        fabTambahMenu = findViewById(R.id.floatingTambahMenu);

        listViewMenu = findViewById(R.id.ListTambah_Menu);
        adapterMRB = new AdapterMRB(this, dataMRBList);
        listViewMenu.setAdapter(adapterMRB);
        fabTambahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (id_menu == null || id_menu.equals("")){
                        if (String.valueOf(editnama_menu.getText()).equals("")){
                            Toast.makeText(TambahMenuActivity.this, "Silahkan isi nama menu Anda", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dbhelper.insertMRB(editnama_menu.getText().toString(), newformat);
                            editnama_menu.setText("");
                            getDataMenu(newformat);
                            Toast.makeText(TambahMenuActivity.this, "Berhasil menambahkan menu", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        if (String.valueOf(editnama_menu.getText()).equals("")){
                            Toast.makeText(TambahMenuActivity.this, "Isi nama menu untuk melakukan update", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dbhelper.updateMRB(Integer.parseInt(id_menu),editnama_menu.getText().toString());
                            editnama_menu.setText("");
                            getDataMenu(newformat);
                            Toast.makeText(TambahMenuActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("saving", e.getMessage());
                }
            }
        });
        getDataMenu(newformat);

        //Update dan delete list menu
        String finalNewformat = newformat;
        listViewMenu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_menu = dataMRBList.get(position).getId_menu();
                final String nama_menu = dataMRBList.get(position).getNama_menu();

                AlertDialog.Builder alertDB = new AlertDialog.Builder(TambahMenuActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_menu, null);
                alertDB.setView(dialogView);
                AlertDialog alertDialog = alertDB.create();
                TextView dialogTitle = dialogView.findViewById(R.id.Title_dialog);
                Button editbtn = dialogView.findViewById(R.id.BtnEdit);
                Button deletbtn = dialogView.findViewById(R.id.BtnHapus);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogTitle.setText("Anda memilih "+nama_menu);

                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editnama_menu.setText(nama_menu);
                        dataMRB = dataMRBList.get(position);
                        isEdit = true;
                        updateUI();
                        alertDialog.dismiss();
                    }
                });

                deletbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbhelper.deleteMRB(Integer.parseInt(id_menu));
                        dataMRBList.clear();
                        getDataMenu(finalNewformat);
                        alertDialog.dismiss();
                        Toast.makeText(TambahMenuActivity.this, "Menu "+nama_menu+" dihapus", Toast.LENGTH_SHORT).show();
                    }

                });
                alertDialog.show();
                return true;
            }
        });

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String id_menumrb = dataMRBList.get(position).getId_menu();
                final String nama_menumrb = dataMRBList.get(position).getNama_menu();
                Intent intent = new Intent(TambahMenuActivity.this, DetailMenu.class);
                intent.putExtra("id_menu", id_menumrb);
                intent.putExtra("nama_menu", nama_menumrb);
                startActivity(intent);

            }
        });

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.floatingEditMenu);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatenamaMenu = editnama_menu.getText().toString();
                dbhelper.updateMRB(Integer.parseInt(dataMRB.getId_menu()),updatenamaMenu);
                editnama_menu.setText("");
                editnama_menu.clearFocus();
                isEdit = false;
                updateUI();
                dataMRBList.clear();
                getDataMenu(finalNewformat);
                Toast.makeText(TambahMenuActivity.this, "Edit Menu berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        FloatingActionButton fabTambah = (FloatingActionButton) findViewById(R.id.floatingTambahMenu);
        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.floatingEditMenu);
        if (isEdit){
            fabTambah.setVisibility(View.GONE);
            fabEdit.setVisibility(View.VISIBLE);
        } else {
            fabTambah.setVisibility(View.VISIBLE);
            fabEdit.setVisibility(View.GONE);
        }
    }

    private void getDataMenu(String tanggal) {
        ArrayList<HashMap<String, String>> rows = dbhelper.getTanggalMenu(tanggal);
        Log.d("TES", rows.toString());
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

    @Override
    public void onResume() {
        super.onResume();
        dataMRBList.clear();
        getDataMenu(newformat);
    }

}