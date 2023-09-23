package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
                final String id_menumrb = dataMRBList.get(position).getId_menu();
                final String nama_menumrb = dataMRBList.get(position).getNama_menu();
                final CharSequence[] dialogItem = {"Edit","Hapus"};
                dialog = new AlertDialog.Builder(TambahMenuActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                dbhelper.updateMRB(Integer.parseInt(id_menumrb),editnama_menu.getText().toString());
                                dataMRBList.clear();
                                getDataMenu(finalNewformat);
                                break;
                            case 1:
                                dbhelper.deleteMRB(Integer.parseInt(id_menumrb));
                                dataMRBList.clear();
                                getDataMenu(finalNewformat);
                                break;
                        }
                    }
                }).show();
                return true;
            }
        });

        fabTambahBahan = findViewById(R.id.floatingTambahBahan);
        fabTambahBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupBahan();
            }
        });

        fabTambahResep = findViewById(R.id.floatingTambahResep);
        fabTambahResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupResep();
            }
        });
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

    private void showPopupBahan() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bahan_popup);

        //tambahkan untuk menampilkan bahan
        listViewBahan = dialog.findViewById(R.id.LV_ListBahan_Popup);
        adapterBahan = new AdapterBahan(this, dataBahanList);
        dataBahanList.clear();
        listViewBahan.setAdapter(adapterBahan);
        getDataBahan();

        Button btntambahanbahan = dialog.findViewById(R.id.Btn_Tambahkan_Bahan);
        btntambahanbahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TambahMenuActivity.this, "Bahan Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void getDataBahan() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBahan();
        for (int i=0; i<rows.size(); i++){
            String id_bahan = rows.get(i).get("id_bahan");
            String nama_bahan = rows.get(i).get("nama_bahan");
            DataBahan dataBahan = new DataBahan();
            dataBahan.setId_bahan(id_bahan);
            dataBahan.setNama_bahan(nama_bahan);

            dataBahanList.add(dataBahan);
        }
        adapterBahan.notifyDataSetChanged();
    }

    private void showPopupResep() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.resep_popup);

        // tambahkan untuk menampilkan resep
        listViewResep = dialog.findViewById(R.id.LV_ListResep_Popup);
        adapterResep = new Adapter(this, dataResepList);
        dataResepList.clear();
        listViewResep.setAdapter(adapterResep);
        getDataResep();


        Button btntambahresep = dialog.findViewById(R.id.Btn_Tambahkan_Resep);
        btntambahresep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TambahMenuActivity.this, "Resep Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void getDataResep() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAll();
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String recipe_name = rows.get(i).get("recipe_name");
            String material_name = rows.get(i).get("material_name");
            String instruction = rows.get(i).get("instruction");

            Data dataResep = new Data();
            dataResep.setId(id);
            dataResep.setRecipe_name(recipe_name);
            dataResep.setMaterial_name(material_name);
            dataResep.setInstruction(instruction);

            dataResepList.add(dataResep);
        }
        adapterResep.notifyDataSetChanged();
    }
}