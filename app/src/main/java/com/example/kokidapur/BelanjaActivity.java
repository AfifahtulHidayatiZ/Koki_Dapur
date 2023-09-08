package com.example.kokidapur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kokidapur.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BelanjaActivity extends AppCompatActivity {
//    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);

        FloatingActionButton fabBelanja = findViewById(R.id.floatingBelanja);
        fabBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BelanjaActivity.this, TambahDaftarBelanjaActivity.class);
                startActivity(intent);
            }
        });



//        actionBar = getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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