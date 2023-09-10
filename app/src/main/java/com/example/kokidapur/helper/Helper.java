package com.example.kokidapur.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "koki_dapur";

    public Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE recipes (id INTEGER PRIMARY KEY autoincrement, recipe_name TEXT NOT NULL, material_name TEXT NOT NULL, instruction TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getAll(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM recipes";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("recipe_name", cursor.getString(1));
                map.put("material_name", cursor.getString(2));
                map.put("instruction", cursor.getString(3));
                //memasukan map ke list
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insert(String recipe_name, String material_name, String instruction){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY ="INSERT INTO recipes (recipe_name, material_name, instruction) VALUES('"+recipe_name+"', '"+material_name+"', '"+instruction+"')";
        database.execSQL(QUERY);
    }

    public void update(int id, String recipe_name, String material_name, String instruction){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "UPDATE recipes SET recipe_name = '"+recipe_name+"', material_name = '"+material_name+"', instruction = '"+instruction+"' WHERE id = "+id;
        database.execSQL(QUERY);
    }

    public void delete(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM recipes WHERE id = "+id;
        database.execSQL(QUERY);
    }
}
