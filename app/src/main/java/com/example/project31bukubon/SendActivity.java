package com.example.project31bukubon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends AppCompatActivity {
    EditText editJudul, editPesan, editTambahan;
    Button kirim;
    Bundle bundle;
    String judul, pesan, tambahan, semuaPesan;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        editJudul = findViewById(R.id.sendTextTitle);
        editPesan = findViewById(R.id.sendTextMessage);
        editTambahan = findViewById(R.id.sendTextExtends);
        kirim = findViewById(R.id.btnSend);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Aplikasi Mode Biasa
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Aplikasi Mode Malam
                break;
        }

        editPesan.setEnabled(false);
        bundle = getIntent().getExtras();

        if (bundle != null){
            editPesan.setText(bundle.getString("pesan"));
            kirimData();
        } else{
            Toast.makeText(SendActivity.this,"Error!!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SendActivity.this, RecyclerActivity.class));
            finish();
        }
    }

    private void kirimData() {
        kirim.setOnClickListener(v -> {
            judul = editJudul.getText().toString();
            pesan = editPesan.getText().toString();
            tambahan = editTambahan.getText().toString();
            semuaPesan = "*"+judul+"*\n\n"+pesan+"\n\n"+tambahan;

            editJudul.setText("");
            editTambahan.setText("");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, semuaPesan);
            intent.putExtra("jid", "62"+bundle.getString("nomor")+"@s.whatsapp.net");
            intent.setPackage("com.whatsapp");
            intent.setPackage("com.yowhatsapp");
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SendActivity.this, RecyclerActivity.class));
        finish();
    }
}