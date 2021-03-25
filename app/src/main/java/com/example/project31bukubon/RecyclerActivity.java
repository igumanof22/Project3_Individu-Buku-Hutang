package com.example.project31bukubon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<DataHutang> dataList;
    DatabaseHelper myDb;
    Button add, search;
    EditText txtSearch;
    int doubleTapParam = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = findViewById(R.id.recyclerView);
        add = findViewById(R.id.btnMainAdd);
        search = findViewById(R.id.btnMainSearch);
        txtSearch = findViewById(R.id.txtMainSearch);

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
        getData();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataList.clear();
                if (Objects.requireNonNull(txtSearch.getText()).toString().equals("")){
                    getData();
                } else{
                    searchData();
                }
            }
        });
        addData();
    }

    public void getData(){
        Cursor data = myDb.getAllData1();
        if (data.getCount() == 0) {
            showMessage("Warning!", "Tidak Ada Data Hutang");
        } else {
            while (data.moveToNext()) {
                dataList.add(new DataHutang(data.getString(0), data.getString(1), data.getString(2), data.getString(3)));
            }
            RecyclerCardAdapter adapter = new RecyclerCardAdapter(RecyclerActivity.this, dataList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecyclerActivity.this, 2));
            recyclerView.setAdapter(adapter);
        }
    }

    public void searchData(){
        Cursor data2 = myDb.getSearch(txtSearch.getText().toString());
        if (data2.getCount() == 0){
            showMessage("Warning!", "Hutang atas nama "+txtSearch.getText()+" tidak ada");
            txtSearch.setText("");
        } else{
            while (data2.moveToNext()){
                dataList.add(new DataHutang(data2.getString(0), data2.getString(1), data2.getString(2), data2.getString(3)));
            }
            RecyclerCardAdapter recyclerCardAdapter = new RecyclerCardAdapter(this, dataList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(RecyclerActivity.this, 2));
            recyclerView.setAdapter(recyclerCardAdapter);
        }
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
            startActivity(new Intent(RecyclerActivity.this, InsertMainActivity.class));
            finish();
        });
    }

    @SuppressLint("ShowToast")
    @Override
    public void onBackPressed() {
        if (doubleTapParam == 1) {
            this.finish();
        }

        this.doubleTapParam = 1;
        Toast.makeText(this, "Tap sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleTapParam = 0, 2000);
    }
}