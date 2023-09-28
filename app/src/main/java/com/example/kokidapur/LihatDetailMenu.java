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

import com.example.kokidapur.adapter.AdapterMenuBahan;
import com.example.kokidapur.adapter.AdapterMenuResep;
import com.example.kokidapur.helper.Helper;
import com.example.kokidapur.model.DataBahanMenu;
import com.example.kokidapur.model.DataResepMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LihatDetailMenu extends AppCompatActivity {
    List<DataBahanMenu> dataBahanDMList = new ArrayList<>();
    List<DataResepMenu> dataResepDMList = new ArrayList<>();
    AdapterMenuBahan adapterMenuBahan;
    AdapterMenuResep adapterMenuResep;

    Helper dbhelper = new Helper(this);
    ListView lvBahan, lvResep;

    TextView namaMenu;
    String id_menu, nama_menu, id_bahan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_menu);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbLihatDetailBahan);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String selectedDate = getIntent().getStringExtra("selectedDate");
        namaMenu = findViewById(R.id.LihatNamaMenuDetail);
        id_menu = getIntent().getStringExtra("id_menu");
        nama_menu = getIntent().getStringExtra("nama_menu");

        if (id_menu !=null){
            namaMenu.setText(nama_menu);
        }

        dbhelper = new Helper(getApplicationContext());

        lvBahan = findViewById(R.id.ListBahanBeli);
        adapterMenuBahan = new AdapterMenuBahan(this, dataBahanDMList);
        lvBahan.setAdapter(adapterMenuBahan);

        lvResep =findViewById(R.id.ListTambah_Resep);
        adapterMenuResep =new AdapterMenuResep(this, dataResepDMList);
        lvResep.setAdapter(adapterMenuResep);

        lvResep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                final String id = dataResepDMList.get(position).getId();
                final String recipe_name = dataResepDMList.get(position).getRecipe_name();
                final String material_name = dataResepDMList.get(position).getMaterial_name();
                final String instruction = dataResepDMList.get(position).getInstruction();
                Intent intent = new Intent(LihatDetailMenu.this, LihatResepActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("recipe_name", recipe_name);
                intent.putExtra("material_name", material_name);
                intent.putExtra("instruction", instruction);
                startActivity(intent);
            }
        });

        getdataBahanDM();
        getdataResepDM();
    }

    private void getdataBahanDM() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllBahanDM(Integer.parseInt(id_menu));{
            for (int i=0; i<rows.size(); i++){
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

    private void getdataResepDM() {
        ArrayList<HashMap<String, String>> rows = dbhelper.getAllResepDM(Integer.parseInt(id_menu));
        for (int i =0; i<rows.size(); i++){
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