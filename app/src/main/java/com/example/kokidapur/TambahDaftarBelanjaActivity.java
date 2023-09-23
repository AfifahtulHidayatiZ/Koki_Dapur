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

public class TambahDaftarBelanjaActivity extends AppCompatActivity {
    private EditText editnama_belanja, editjumlah_belanja;
    Button btnSimpanBelanja;
    private Helper dbhelper = new Helper(this);
    private String id_bahan, nama_bahan, jumlah;

//    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_daftar_belanja);

        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbTambahBelanja);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editnama_belanja = findViewById(R.id.InputNamaBelanja);
        editjumlah_belanja = findViewById(R.id.InputJumlahBelanja);
        btnSimpanBelanja = findViewById(R.id.Btn_Simpan_Belanja);

        id_bahan = getIntent().getStringExtra("id_bahan");
        nama_bahan = getIntent().getStringExtra("nama_bahan");
        jumlah = getIntent().getStringExtra("jumlah");

        if (id_bahan == null || id_bahan.equals("")){
            setTitle("Tambah Daftar Belanja");
        }else {
            setTitle("Edit Daftar Belanja");
            editnama_belanja.setText(nama_bahan);
            editjumlah_belanja.setText(jumlah);
        }

        btnSimpanBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (id_bahan == null || id_bahan.equals("")){
                        saveBelanja();
                    }else {
                        editBelanja();
                    }
                }catch (Exception e){
                    Log.e("saving", e.getMessage());
                }
            }
        });
    }

    private void saveBelanja() {
        if (String.valueOf(editnama_belanja.getText()).equals("") || String.valueOf(editjumlah_belanja.getText()).equals("")){
            Toast.makeText(this, "Silahkan Isi Nama Bahan Belanja", Toast.LENGTH_SHORT).show();
        }else {
            dbhelper.insertBelanja(editnama_belanja.getText().toString(), editjumlah_belanja.getText().toString());
            finish();
        }
    }

    private void editBelanja() {
        if (String.valueOf(editnama_belanja.getText()).equals("") || String.valueOf(editjumlah_belanja.getText()).equals("")){
            Toast.makeText(this, "Silahkan Isi Nama Bahan Belanja", Toast.LENGTH_SHORT).show();
        }else {
            dbhelper.updateBelanja(Integer.parseInt(id_bahan),editnama_belanja.getText().toString(), editjumlah_belanja.getText().toString());
            finish();
        }
    }


//
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