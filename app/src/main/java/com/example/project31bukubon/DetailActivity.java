package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;

public class DetailActivity extends AppCompatActivity {
    TextView nama, totalhutang;
    Button add, delete, edit, kirim;
    RecyclerView recyclerView;
    DatabaseHelper myDb;
    List<DetailHutang> dataList;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nama = findViewById(R.id.detailNama);
        totalhutang = findViewById(R.id.textTotal);
        add = findViewById(R.id.detailAdd);
        delete = findViewById(R.id.detailDelete);
        edit = findViewById(R.id.btnMainEdit);
        kirim = findViewById(R.id.detailKirim);
        recyclerView = findViewById(R.id.recyclerDetail);

        bundle = getIntent().getExtras();
        if (bundle != null){
            nama.setText(bundle.getString("nama"));
        } else{
            Toast.makeText(DetailActivity.this,"Error!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DetailActivity.this, RecyclerActivity.class));
            finish();
        }

        dataList = new ArrayList<>();
        myDb = new DatabaseHelper(this);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Aplikasi Mode Biasa
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Aplikasi Mode Malam
                break;
        }

        Cursor data = myDb.getAllData2(bundle.getString("id"));
        if (data.getCount() == 0){
            showMessage("Warning!", "Tidak Ada Detail Hutang");
        } else {
            while(data.moveToNext()) {
                dataList.add(new DetailHutang(data.getString(0), data.getString(1), data.getString(2), data.getString(3)));
            }
            RecyclerCardDetailAdapter adapter = new RecyclerCardDetailAdapter(this, dataList, bundle.getString("nama"), bundle.getString("nomor"));
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
            recyclerView.setAdapter(adapter);


            TotalHarga();
            myDb.insertData2_1(bundle.getString("id"), totalhutang.getText().toString());
            kirimData();
        }

        addData();
        deleteData();
        editMainData();
    }

    public void kirimData(){
        kirim.setOnClickListener(v -> {
            Cursor data3 = myDb.getAllData2(bundle.getString("id"));
            StringBuilder pesan = new StringBuilder();
            String semuaPesan;

            while (data3.moveToNext()){
                pesan.append(data3.getString(2)).append(" : ").append(data3.getString(3)).append("\n");
            }
            semuaPesan = pesan.toString()+"Total Hutang : "+totalhutang.getText().toString();
            Intent intent = new Intent(DetailActivity.this, SendActivity.class);
            intent.putExtra("id", bundle.getString("id"));
            intent.putExtra("nama", bundle.getString("nama"));
            intent.putExtra("nomor", bundle.getString("nomor"));
            intent.putExtra("pesan", semuaPesan);
            startActivity(intent);
            finish();
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void addData(){
        add.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, InsertDetailActivity.class);
            intent.putExtra("id", bundle.getString("id"));
            intent.putExtra("nama", bundle.getString("nama"));
            intent.putExtra("nomor", bundle.getString("nomor"));
            startActivity(intent);
            finish();
        });
    }

    public void deleteData(){
        delete.setOnClickListener(v -> {
            Integer deleteData = myDb.deleteData1(bundle.getString("id"));
            Integer deleteData2 = myDb.deleteData2(bundle.getString("id"));
            if (deleteData2 > 0){
                if (deleteData > 0){
                    Toast.makeText(DetailActivity.this, "Data Hutang dan Detail berhasil dihapus", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, RecyclerActivity.class));
                    finish();
                } else{
                    Toast.makeText(DetailActivity.this, "Data Hutang gagal dihapus", Toast.LENGTH_SHORT).show();
                }
            } else{
                if (deleteData > 0){
                    Toast.makeText(DetailActivity.this, "Data Hutang berhasil dihapus", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DetailActivity.this, RecyclerActivity.class));
                    finish();
                } else{
                    Toast.makeText(DetailActivity.this, "Data Hutang gagal dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editMainData(){
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, UpdateMainActivity.class);
            intent.putExtra("id", bundle.getString("id"));
            intent.putExtra("nama", bundle.getString("nama"));
            intent.putExtra("nomor", bundle.getString("nomor"));
            startActivity(intent);
            finish();
        });
    }

    public void TotalHarga(){
        Cursor data2 = myDb.getAllData2(bundle.getString("id"));
        int total = 0;

        while(data2.moveToNext()) {
            total += Integer.valueOf(data2.getString(3));
        }
        totalhutang.setText(String.valueOf(total));
        myDb.insertData2_1(bundle.getString("id"), totalhutang.getText().toString());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailActivity.this, RecyclerActivity.class));
        finish();
    }
}