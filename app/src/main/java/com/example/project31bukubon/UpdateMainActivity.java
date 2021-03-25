package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateMainActivity extends AppCompatActivity {
    EditText id, nama, nomor;
    Button btn;
    DatabaseHelper myDb;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main);
        id = findViewById(R.id.mainTextId);
        nama = findViewById(R.id.mainTextNama);
        nomor = findViewById(R.id.mainTextNomor);
        btn = findViewById(R.id.buttonMainUpdate);

        myDb = new DatabaseHelper(this);
        id.setEnabled(false);
        bundle = getIntent().getExtras();

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Aplikasi Mode Biasa
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Aplikasi Mode Malam
                break;
        }

        if (bundle != null){
            id.setText(bundle.getString("id"));
            nama.setText(bundle.getString("nama"));
            nomor.setText(bundle.getString("nomor"));

            EditData();
        } else{
            Toast.makeText(this, "Error, Tidak ada data", Toast.LENGTH_LONG).show();
            startActivity(new Intent(UpdateMainActivity.this, RecyclerActivity.class));
            finish();
        }
    }

    public void EditData(){
        btn.setOnClickListener(v -> {
            boolean isUpdate = myDb.updateData1(id.getText().toString(), nama.getText().toString(), nomor.getText().toString());

            if(isUpdate) {
                Toast.makeText(UpdateMainActivity.this, "Data Berhasil di Update", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateMainActivity.this, DetailActivity.class);
                intent.putExtra("id", bundle.getString("id"));
                intent.putExtra("nama", nama.getText().toString());
                intent.putExtra("nomor", nomor.getText().toString());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(UpdateMainActivity.this, "Data Gagal Berhasil di Update", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateMainActivity.this, DetailActivity.class);
        intent.putExtra("id", bundle.getString("id"));
        intent.putExtra("nama", bundle.getString("nama"));
        intent.putExtra("nomor", bundle.getString("nomor"));
        startActivity(intent);
        finish();
    }
}