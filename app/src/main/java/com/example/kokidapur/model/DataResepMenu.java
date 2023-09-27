package com.example.kokidapur.model;

public class DataResepMenu {

    private String id, id_resep, recipe_name, material_name, instruction;

    public DataResepMenu(String id, String id_resep, String recipe_name, String material_name, String instruction) {
        this.id = id;
        this.id_resep = id_resep;
        this.recipe_name = recipe_name;
        this.material_name = material_name;
        this.instruction = instruction;
    }

    public DataResepMenu() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_resep() {
        return id_resep;
    }

    public void setId_resep(String id_resep) {
        this.id_resep = id_resep;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}