package com.example.kokidapur.model;

public class DataMRB {

    private String id_menu, nama_menu, tanggal,recipe_name;

    public DataMRB(){

    }

    public DataMRB(String id_menu, String nama_menu, String tanggal, String recipe_name) {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.tanggal = tanggal;
        this.recipe_name = recipe_name;
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

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }
}
