package com.example.kokidapur.model;

public class DataBahanMenu {
    private String id, id_bahan, nama_bahan, status, jumlah;

    public DataBahanMenu(String id, String id_bahan, String nama_bahan, String status, String jumlah) {
        this.id = id;
        this.id_bahan = id_bahan;
        this.nama_bahan = nama_bahan;
        this.status = status;
        this.jumlah = jumlah;
    }

    public DataBahanMenu() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(String id_bahan) {
        this.id_bahan = id_bahan;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
