package com.example.kokidapur.model;

public class Data {
    private String id, recipe_name, material_name, instruction;

    public Data(){

    }

    public Data(String id, String recipe_name, String material_name, String instruction) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.material_name = material_name;
        this.instruction = instruction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
