package com.example.kokidapur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kokidapur.adapter.Adapter;
import com.example.kokidapur.adapter.AdapterBahan;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.Data;
import com.example.kokidapur.model.DataBahan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TambahMenuActivity extends AppCompatActivity {
    FloatingActionButton fabTambahBahan, fabTambahResep;
    List<DataBahan> dataBahanList = new ArrayList<>();
    List<Data> dataResepList = new ArrayList<>();
    AdapterBahan adapterBahan;
    Adapter adapterResep;
    Helper dbhelper = new Helper(this);
    ListView listViewBahan, listViewResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);

        //Mengambil tanggal yang dikirim dari listView
        String selectionDate = getIntent().getStringExtra("selectionDate");

        //Menampilkan tanggal di textView
        TextView dayTextView  = findViewById(R.id.TglHari_MenuHariIni);
        TextView dateTextView = findViewById(R.id.Tgl_MenuHariIni);
        dateTextView.setText(selectionDate);

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

    private void showPopupBahan() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bahan_popup);

        //tambahkan untuk menampilkan bahan
        dbhelper = new Helper(this);
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
        dbhelper = new Helper(this);
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