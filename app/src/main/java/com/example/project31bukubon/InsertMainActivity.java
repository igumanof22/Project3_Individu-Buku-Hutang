package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertMainActivity extends AppCompatActivity {
    EditText id, nama, hp;
    Button add;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_main);
        id = findViewById(R.id.mainTextId);
        nama = findViewById(R.id.mainTextNama);
        hp = findViewById(R.id.mainTextNomor);
        add = findViewById(R.id.buttonAdd);

        myDb = new DatabaseHelper(this);
        id.setEnabled(false);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Aplikasi Mode Biasa
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Aplikasi Mode Malam
                break;
        }

        AddData();
    }

    public void AddData() {
        add.setOnClickListener(v -> {
            boolean isInserted = myDb.insertData1(nama.getText().toString(), hp.getText().toString());
            if(isInserted) {
                Toast.makeText(InsertMainActivity.this, "Data Berhasil di Input", Toast.LENGTH_LONG).show();
                startActivity(new Intent(InsertMainActivity.this, RecyclerActivity.class));
                finish();
            } else{
                Toast.makeText(InsertMainActivity.this,"Data Gagal di Input",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(InsertMainActivity.this, RecyclerActivity.class));
        finish();
    }
}