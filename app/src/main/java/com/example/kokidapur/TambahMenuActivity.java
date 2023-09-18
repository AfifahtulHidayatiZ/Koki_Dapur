package com.example.kokidapur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TambahMenuActivity extends AppCompatActivity {

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
    }
}