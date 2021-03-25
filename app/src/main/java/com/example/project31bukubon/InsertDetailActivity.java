package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertDetailActivity extends AppCompatActivity {
    EditText idhutang, detail, harga;
    Button add, edit;
    DatabaseHelper myDb;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_detail);
        idhutang = findViewById(R.id.detailTextId);
        detail = findViewById(R.id.detailTextDetail);
        harga = findViewById(R.id.detailTextHarga);
        add = findViewById(R.id.buttonDetailAdd);
        edit = findViewById(R.id.buttonDetailUpdate);

        myDb = new DatabaseHelper(this);
        idhutang.setEnabled(false);
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
            idhutang.setText(bundle.getString("id"));
            addData();
        } else{
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(InsertDetailActivity.this, RecyclerActivity.class));
            finish();
        }
    }

    public void addData(){
        add.setOnClickListener(v -> {
            boolean IsInserted = myDb.insertData2(idhutang.getText().toString(), detail.getText().toString(), harga.getText().toString());
            if (IsInserted){
                Toast.makeText(InsertDetailActivity.this, "Data Detail Hutang Berhasil di tambahkan", Toast.LENGTH_LONG).show();
                detail.setText("");
                harga.setText("");
            } else{
                Toast.makeText(InsertDetailActivity.this, "Data Detail Hutang Gagal di tambahkan", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InsertDetailActivity.this, DetailActivity.class);
        intent.putExtra("id", bundle.getString("id"));
        intent.putExtra("nama", bundle.getString("nama"));
        intent.putExtra("nomor", bundle.getString("nomor"));
        startActivity(intent);
        finish();
    }
}