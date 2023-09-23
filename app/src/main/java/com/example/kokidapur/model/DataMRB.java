package com.example.kokidapur.model;

public class DataMRB {

    private String id_menu, nama_menu, tanggal;

    public DataMRB(){

    }

    public DataMRB(String id_menu, String nama_menu, String tanggal) {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.tanggal = tanggal;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
