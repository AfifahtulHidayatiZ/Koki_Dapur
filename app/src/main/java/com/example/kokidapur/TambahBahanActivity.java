package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kokidapur.helper.Helper;

public class TambahBahanActivity extends AppCompatActivity {
    private EditText editnama_bahan;
    Button btnSimpanBahan;
    private Helper dbhelper = new Helper(this);
    private String id_bahan, nama_bahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_bahan);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbTambahBahan);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editnama_bahan = findViewById(R.id.InputNamaBahan);
        btnSimpanBahan = findViewById(R.id.Btn_Simpan_Bahan);

        id_bahan = getIntent().getStringExtra("id_bahan");
        nama_bahan = getIntent().getStringExtra("nama_bahan");

        if (id_bahan == null || id_bahan.equals("")){
            setTitle("Tambah Bahan");
        }else {
            setTitle("Edit Bahan");
            TextView namaBahan = findViewById(R.id.txtNamaBahan);
            namaBahan.setText("EDIT STOK BAHAN");
            editnama_bahan.setText(nama_bahan);
        }

        btnSimpanBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (id_bahan == null || id_bahan.equals("")){
                        saveBahan();
                    }else {
                        editBahan();
                    }
                }catch (Exception e){
                    Log.e("saving", e.getMessage());
                }
            }
        });



    }

    private void saveBahan(){
        if (String.valueOf(editnama_bahan.getText()).equals("")){
            Toast.makeText(this, "Silakah Isi Nama Bahan", Toast.LENGTH_SHORT).show();
        }else {
            dbhelper.insertBahan(editnama_bahan.getText().toString());
            finish();
        }
    }

    private void editBahan(){
        if (String.valueOf(editnama_bahan.getText()).equals("")){
            Toast.makeText(this, "Silahkan Isi Nama Bahan", Toast.LENGTH_SHORT).show();
        }else {
            TextView namaBahan = findViewById(R.id.txtNamaBahan);
            dbhelper.updateBahan(Integer.parseInt(id_bahan), editnama_bahan.getText().toString());
            namaBahan.setText("EDIT STOK BAHAN");
            finish();
        }
    }

}