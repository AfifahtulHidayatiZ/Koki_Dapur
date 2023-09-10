package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kokidapur.helper.Helper;

public class TambahResepActivity extends AppCompatActivity {
    private EditText editnama_resep, editnama_bahan, editcara_membuat;
    Button btnSimpanResep;
    private Helper dbhelper = new Helper(this);
    private String id, recipe_name, material_name, instruction;


//    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);
        editnama_resep = findViewById(R.id.InputNamaResep);
        editnama_bahan = findViewById(R.id.InputNamaBahanBahan);
        editcara_membuat = findViewById(R.id.InputCaraMembuat);
        btnSimpanResep = findViewById(R.id.Btn_Simpan_Resep);

        id = getIntent().getStringExtra("id");
        recipe_name = getIntent().getStringExtra("recipe_name");
        material_name = getIntent().getStringExtra("material_name");
        instruction = getIntent().getStringExtra("instruction");

        if (id == null || id.equals("")){
            setTitle("Tambah Resep");
        }else {
            setTitle("Edit Resep");
            editnama_resep.setText(recipe_name);
            editnama_bahan.setText(material_name);
            editcara_membuat.setText(instruction);
        }
        btnSimpanResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (id == null || id.equals("")){
                        save();
                    }else {
                        edit();
                    }
                }catch (Exception e){
                    Log.e("Saving", e.getMessage());
                }
            }
        });

//        actionBar = getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void save(){
        if (String.valueOf(editnama_resep.getText()).equals("") || String.valueOf(editnama_bahan.getText()).equals("")|| String.valueOf(editcara_membuat.getText()).equals("")){
            Toast.makeText(this, "Silahkan Isi Semua Data", Toast.LENGTH_SHORT).show();
        }else {
            dbhelper.insert(editnama_resep.getText().toString(), editnama_bahan.getText().toString(), editcara_membuat.getText().toString());
            finish();
        }
    }

    private void edit(){
        if (String.valueOf(editnama_resep.getText()).equals("") || String.valueOf(editnama_bahan.getText()).equals("")|| String.valueOf(editcara_membuat.getText()).equals("")){
            Toast.makeText(this, "Silahkan Isi Semua Data", Toast.LENGTH_SHORT).show();
        }else {
            dbhelper.update(Integer.parseInt(id), editnama_resep.getText().toString(), editnama_bahan.getText().toString(), editcara_membuat.getText().toString());
            finish();
        }
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