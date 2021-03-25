package com.example.project31bukubon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Nama_Database = "Utang.db";
    public static final String Nama_Tabel_1 = "hutang_table";
    public static final String Nama_Tabel_2 = "detail_table";
    public static final int DATABASE_VERSION = 1;

    public static final String Kolom_1 = "ID";
    public static final String Kolom_2 = "Nama";
    public static final String Kolom_3 = "Nomor";
    public static final String Kolom_4 = "Total";

    public static final String Kolom_A = "ID";
    public static final String Kolom_B = "IdHutang";
    public static final String Kolom_C = "Detail";
    public static final String Kolom_D = "Harga";

    public DatabaseHelper(Context context) {
        super(context, Nama_Database, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table hutang_table (id integer primary key autoincrement, nama text, nomor integer, total integer);");
        db.execSQL("create table detail_table (id integer primary key autoincrement, idhutang text, detail text, harga integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+ Nama_Tabel_1);
//        db.execSQL("DROP TABLE IF EXISTS "+ Nama_Tabel_1);
        onCreate(db);
    }

    public boolean insertData1(String nama, String nomor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kolom_2, nama);
        contentValues.put(Kolom_3, nomor);
        long result = db.insert(Nama_Tabel_1, null, contentValues);

        return result != -1;
    }

    public boolean insertData2(String idhutang, String detail, String harga){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kolom_B, idhutang);
        contentValues.put(Kolom_C, detail);
        contentValues.put(Kolom_D, harga);
        long result = db.insert(Nama_Tabel_2, null, contentValues);

        return result != -1;
    }

    public boolean insertData2_1(String idhutang, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kolom_4, total);

        db.update(Nama_Tabel_1, contentValues, "id = ?", new String[] {idhutang});
        return true;
    }

    public Cursor getAllData1(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select *from hutang_table", null);
    }

    public Cursor getAllData2(String idhutang){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select *from detail_table where idhutang = "+idhutang, null);
    }

    public Cursor getSearch(String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select *from hutang_table where nama like '%"+nama+"%' collate nocase", null);
    }

    public boolean updateData1(String id, String nama, String nomor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kolom_2,nama);
        contentValues.put(Kolom_3,nomor);

        db.update(Nama_Tabel_1, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateData2(String id, String idhutang, String detail, String harga){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Kolom_C,detail);
        contentValues.put(Kolom_D,harga);

        db.update(Nama_Tabel_2, contentValues, "id = ? and idhutang = ?", new String[] {id,idhutang});
        return true;
    }

    public int deleteData1(String idhutang){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Nama_Tabel_1, "id = ?", new String[] {idhutang});
    }

    public int deleteData2(String idhutang){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Nama_Tabel_2, "idhutang = ?", new String[] {idhutang});
    }
}
