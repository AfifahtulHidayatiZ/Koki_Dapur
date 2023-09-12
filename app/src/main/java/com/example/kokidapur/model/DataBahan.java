package com.example.kokidapur.model;

public class DataBahan {
    private String id_bahan, nama_bahan, status;

    public DataBahan() {

    }

    public DataBahan(String id_bahan, String nama_bahan, String status) {
        this.id_bahan = id_bahan;
        this.nama_bahan = nama_bahan;
        this.status = status;
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
}
