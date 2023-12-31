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
import com.example.kokidapur.adapter.AdapterBahanPopup;
import com.example.kokidapur.adapter.AdapterMenuBahan;
import com.example.kokidapur.adapter.AdapterMenuResep;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.Data;
import com.example.kokidapur.model.DataBahan;
import com.example.kokidapur.model.DataBahanMenu;
import com.example.kokidapur.model.DataResepMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailMenu extends AppCompatActivity {
    FloatingActionButton fabTambahBahan, fabTambahResep, fabTambahBahanMenu;
    List<DataBahan> dataBahanList = new ArrayList<>();
    List<DataBahanMenu> dataBahanDMList = new ArrayList<>();
    List<DataResepMenu> dataResepDMList = new ArrayList<>();
    List<Data> dataResepList = new ArrayList<>();
//    AdapterBahan adapterBahan;
    AdapterBahanPopup adapterBahanPopup;
    Adapter adapterResep;
    AdapterMenuBahan adapterMenuBahan;
    AdapterMenuResep adapterMenuResep;

    Helper dbhelper = new Helper(this);
    ListView lvPopUpBahan, lvPopUpResep, lvBahan, lvResep;
    TextView namaMenu;
    String id_menu, nama_menu, id_bahan;
    EditText edit_detailbahan;
    private TextView emptyResep, emptyBahan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbDetailBahan);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String selectedDate = getIntent().getStringExtra("selectedDate");

        namaMenu = findViewById(R.id.NamaMenuDetail);
        id_menu = getIntent().getStringExtra("id_menu");
        nama_menu = getIntent().getStringExtra("nama_menu");

        if (id_menu !=null){
            namaMenu.setText(nama_menu);
        }

        dbhelper = new Helper(getApplicationContext());

        lvBahan = findViewById(R.id.ListBahanBeli);
        adapterMenuBahan = new AdapterMenuBahan(this, dataBahanDMList);
        lvBahan.setAdapter(adapterMenuBahan);
        getdataBahanDM();

        lvResep = findViewById(R.id.ListTambah_Resep);
        adapterMenuResep = new AdapterMenuResep(this, dataResepDMList);
        lvResep.setAdapter(adapterMenuResep);
        getdataResepDM();


        lvBahan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String TD = dataBahanDMList.get(position).getNama_bahan();
                AlertDialog.Builder alertDB = new AlertDialog.Builder(DetailMenu.this);
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_detailmenu, null);
                alertDB.setView(dialogView);
                AlertDialog alertDialog = alertDB.create();
                TextView dialogtitle = dialogView.findViewById(R.id.Title_dialog);
                Button deletbtn = dialogView.findViewById(R.id.BtnHapus);
                Button batalbtn = dialogView.findViewById(R.id.BtnTidak);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogtitle.setText("Hapus "+TD+"?");

                deletbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cekId = dataBahanDMList.get(position).getId();
                        Log.d("Cek", String.valueOf(position));
                        if (cekId != null && !cekId.isEmpty()){
                            Log.e("CekId", "Id"+cekId);
                            try {
                                int parsedId = Integer.parseInt(cekId);
                                Log.d("CekId", "Id"+parsedId);
                                dbhelper.deleteBahanDM(parsedId);
                                dataBahanDMList.clear();
                                getdataBahanDM();
                                Toast.makeText(DetailMenu.this, TD+" dihapus", Toast.LENGTH_SHORT).show();
                            }catch (NumberFormatException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.e("CekId","Id  NULL");
                        }
                        alertDialog.dismiss();
                    }
                });

                batalbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });

        lvResep.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String TD = dataResepDMList.get(position).getRecipe_name();
                AlertDialog.Builder alertDB = new AlertDialog.Builder(DetailMenu.this);
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_detailmenu_resep, null);
                alertDB.setView(dialogView);
                AlertDialog alertDialog = alertDB.create();
                TextView dialogTitle = dialogView.findViewById(R.id.Title_dialog_resep);
                Button deletebtn = dialogView.findViewById(R.id.BtnHapus_resep);
                Button tidakbtn = dialogView.findViewById(R.id.BtnTidak_resep);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogTitle.setText("Hapus "+TD);

                deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cekId = dataResepDMList.get(position).getId();
                        if (cekId != null && !cekId.isEmpty()){
                            try {
                                int parsedId = Integer.parseInt(cekId);
                                dbhelper.deleteResepDM(parsedId);
                                dataResepDMList.clear();
                                getdataResepDM();
                                Toast.makeText(DetailMenu.this, "Resep "+TD+" dihapus", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            Log.e("CekIdResep", "Id Null");
                        }
                        alertDialog.dismiss();
                    }
                });

                tidakbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;

            }
        });

        lvResep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                final String id = dataResepDMList.get(position).getId();
                final String recipe_name = dataResepDMList.get(position).getRecipe_name();
                final String material_name = dataResepDMList.get(position).getMaterial_name();
                final String instruction = dataResepDMList.get(position).getInstruction();
                Intent intent = new Intent(DetailMenu.this, LihatResepActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("recipe_name", recipe_name);
                intent.putExtra("material_name", material_name);
                intent.putExtra("instruction", instruction);
                startActivity(intent);

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

    private void showPopupBahan() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bahan_popup);

        emptyBahan = dialog.findViewById(R.id.EmptyBahan);

        //tambahkan untuk menampilkan bahan
        lvPopUpBahan = dialog.findViewById(R.id.LV_ListBahan_Popup);
        adapterBahanPopup = new AdapterBahanPopup(this, dataBahanList);
        dataBahanList.clear();
        lvPopUpBahan.setAdapter(adapterBahanPopup);
        getDataBahan();

        lvPopUpBahan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbhelper.insertBahanDM(Integer.parseInt(id_menu),Integer.parseInt(dataBahanList.get(position).getId_bahan()));
                dataBahanDMList.clear();
                getdataBahanDM();
                dialog.dismiss();
                Toast.makeText(DetailMenu.this, "Bahan ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });

        edit_detailbahan = dialog.findViewById(R.id.InputBahanMenu);
        fabTambahBahanMenu = dialog.findViewById(R.id.floatingTambahBahanMenu);

        fabTambahBahanMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String namaBahanBaru = edit_detailbahan.getText().toString().trim();
                    if (namaBahanBaru.isEmpty()){
                        Toast.makeText(DetailMenu.this, "Silahkan isi nama menu", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dbhelper.insertBahanBaruDM(namaBahanBaru);
                        edit_detailbahan.setText("");
                        dataBahanDMList.clear();
                        dataBahanList.clear();
                        getDataBahan();
                        Toast.makeText(DetailMenu.this, "Silahkan pilih bahan yang baru ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
            String status = rows.get(i).get("status");
            DataBahan dataBahan = new DataBahan();
            dataBahan.setId_bahan(id_bahan);
            dataBahan.setNama_bahan(nama_bahan);
            dataBahan.setStatus(status);

            dataBahanList.add(dataBahan);
        }
        adapterBahanPopup.notifyDataSetChanged();

        if (dataBahanList.isEmpty()){
            lvPopUpBahan.setVisibility(View.GONE);
            emptyBahan.setVisibility(View.VISIBLE);
        }
        else {
            lvPopUpBahan.setVisibility(View.VISIBLE);
            emptyBahan.setVisibility(View.GONE);
        }
    }

    private void getdataBahanDM() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBahanDM(Integer.parseInt(id_menu));{
            for (int i=0; i<rows.size(); i++){
                Log.d("Database", "Total bahan dari database: " + rows.size());
                String id = rows.get(i).get("id");
                String id_bahan = rows.get(i).get("id_bahan");
                String nama_bahan = rows.get(i).get("nama_bahan");
                String status = rows.get(i).get("status");
                String jumlah = rows.get(i).get("jumlah");

                DataBahanMenu dataBahan = new DataBahanMenu();
                dataBahan.setId(id);
                dataBahan.setId_bahan(id_bahan);
                dataBahan.setNama_bahan(nama_bahan);
                dataBahan.setStatus(status);
                dataBahan.setJumlah(jumlah);

                dataBahanDMList.add(dataBahan);
            }
            Log.d("CekId", rows.toString());
            adapterMenuBahan.notifyDataSetChanged();
        }
    }


    private void showPopupResep() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.resep_popup);

        emptyResep = dialog.findViewById(R.id.EmptyResep);

        // tambahkan untuk menampilkan resep
        lvPopUpResep = dialog.findViewById(R.id.LV_ListResep_Popup);
        adapterResep = new Adapter(this, dataResepList);
        dataResepList.clear();
        lvPopUpResep.setAdapter(adapterResep);
        getDataResep();

        lvPopUpResep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dbhelper.insertResepDB(Integer.parseInt(id_menu), Integer.parseInt(dataResepList.get(position).getId()));
                dataResepDMList.clear();
                getdataResepDM();
                dialog.dismiss();
                Toast.makeText(DetailMenu.this, "Resep ditambahakan", Toast.LENGTH_SHORT).show();
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

        if (dataResepList.isEmpty()){
            lvPopUpResep.setVisibility(View.GONE);
            emptyResep.setVisibility(View.VISIBLE);
        }
        else {
            lvPopUpResep.setVisibility(View.VISIBLE);
            emptyResep.setVisibility(View.GONE);
        }

    }


    private void getdataResepDM() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllResepDM(Integer.parseInt(id_menu));
        for (int i =0; i<rows.size(); i++){
            Log.d("Database", "Total resep dari database: " + rows.size());
            String id = rows.get(i).get("id");
            String id_resep = rows.get(i).get("id_resep");
            String recipe_name =rows.get(i).get("recipe_name");
            String material_name = rows.get(i).get("material_name");
            String instruction = rows.get(i).get("instruction");

            DataResepMenu dataResepMenu = new DataResepMenu();
            dataResepMenu.setId(id);
            dataResepMenu.setId_resep(id_resep);
            dataResepMenu.setRecipe_name(recipe_name);
            dataResepMenu.setMaterial_name(material_name);
            dataResepMenu.setInstruction(instruction);

            dataResepDMList.add(dataResepMenu);
        }
        adapterMenuResep.notifyDataSetChanged();

    }
}