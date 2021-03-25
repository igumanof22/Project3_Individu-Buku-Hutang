package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDetailActivity extends AppCompatActivity {
    EditText idhutang, detail, harga;
    Button edit;
    DatabaseHelper myDb;
    Bundle bundle;
    String editId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);
        idhutang = findViewById(R.id.detailUpdateId);
        detail = findViewById(R.id.detailUpdateDetail);
        harga = findViewById(R.id.detailUpdateHarga);
        edit = findViewById(R.id.buttonDetailUpdate);

        myDb = new DatabaseHelper(this);
        bundle = getIntent().getExtras();
        idhutang.setEnabled(false);

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
            editId = getIntent().getStringExtra("id");
            idhutang.setText(getIntent().getStringExtra("idhutang"));
            detail.setText(getIntent().getStringExtra("detail"));
            harga.setText(getIntent().getStringExtra("harga"));
            editData();
        } else{
            Toast.makeText(this, "Error, Tidak ada data", Toast.LENGTH_LONG).show();
            startActivity(new Intent(UpdateDetailActivity.this, RecyclerActivity.class));
            finish();
        }

    }

    public void editData(){
        edit.setOnClickListener(v -> {
            boolean isUpdate = myDb.updateData2(editId, idhutang.getText().toString(), detail.getText().toString(), harga.getText().toString());

            if(isUpdate) {
                Toast.makeText(UpdateDetailActivity.this, "Data Berhasil di Ubah", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateDetailActivity.this, DetailActivity.class);
                intent.putExtra("id", bundle.getString("idhutang"));
                intent.putExtra("nama", bundle.getString("nama"));
                intent.putExtra("nomor", bundle.getString("nomor"));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(UpdateDetailActivity.this, "Data Gagal Berhasil di Ubah", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateDetailActivity.this, DetailActivity.class);
        intent.putExtra("id", bundle.getString("idhutang"));
        intent.putExtra("nama", bundle.getString("nama"));
        intent.putExtra("nomor", bundle.getString("nomor"));
        startActivity(intent);
        finish();
    }
}