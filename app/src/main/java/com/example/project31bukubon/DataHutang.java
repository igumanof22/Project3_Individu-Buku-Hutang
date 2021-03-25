package com.example.project31bukubon;

public class DataHutang {
    private String id;
    private String nama;
    private String nomor;
    private String total;

    public DataHutang(String id, String nama, String nomor, String total){
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNomor() {
        return nomor;
    }

    public String getTotal() {
        return total;
    }
}
