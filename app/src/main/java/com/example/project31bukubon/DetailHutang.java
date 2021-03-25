package com.example.project31bukubon;

public class DetailHutang {
    private String id;
    private String idhutang;
    private String detail;
    private String harga;

    public DetailHutang(String id, String idhutang, String detail, String harga){
        this.id = id;
        this.idhutang = idhutang;
        this.detail = detail;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public String getIdhutang() {
        return idhutang;
    }

    public String getDetail() {
        return detail;
    }

    public String getHarga() {
        return harga;
    }
}
