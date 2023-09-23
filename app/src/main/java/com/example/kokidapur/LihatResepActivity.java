package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kokidapur.helper.Helper;

public class LihatResepActivity extends AppCompatActivity {
    private TextView namaresep, namabahan, caramembuat;
    private Helper dbhelper = new Helper(this);
    private String id, recipe_name, material_name, instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_resep);
        ActionBar actionBar = getSupportActionBar();
        View appBar = findViewById(R.id.tbLihatResep);
        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        namaresep = findViewById(R.id.txt_NamaResep);
        namabahan = findViewById(R.id.txt_NamaBahanBahan);
        caramembuat = findViewById(R.id.txt_CaraMembuat);

        id = getIntent().getStringExtra("id");
        recipe_name = getIntent().getStringExtra("recipe_name");
        material_name = getIntent().getStringExtra("material_name");
        instruction = getIntent().getStringExtra("instruction");

        if (id != null){
            namaresep.setText(recipe_name);
            namabahan.setText(material_name);
            caramembuat.setText(instruction);
        }
    }
}