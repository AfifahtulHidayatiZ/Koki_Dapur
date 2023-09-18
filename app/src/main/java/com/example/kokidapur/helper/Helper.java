package com.example.kokidapur.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 4;
    static final String DATABASE_NAME = "koki_dapur";

    public Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY autoincrement, " +
                "recipe_name TEXT NOT NULL, " +
                "material_name TEXT NOT NULL, " +
                "instruction TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_TABLE);

        final String SQL_CREATE_TABLE_BAHAN = "CREATE TABLE bahan ("+
                "id_bahan INTEGER PRIMARY KEY autoincrement, " +
                "nama_bahan TEXT NOT NULL," +
                "status TEXT CHECK (status IN('ada','beli')))";
        db.execSQL(SQL_CREATE_TABLE_BAHAN);

//        final String SQL_CREATE_TABLE_MRB = "CREATE TABLE mrb (" +
//                "id_menu INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "nama_menu TEXT NOT NULL, " +
//                "bahan_id INTEGER NOT NULL, " +
//                "resep_id INTEGER NOT NULL, " +
//                "tanggal DATETIME NOT NULL, " +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
//                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
//                "FOREIGN KEY (bahan_id) REFERENCES bahan(id_bahan), " +
//                "FOREIGN KEY (resep_id) REFERENCES recipes(id))";
//        db.execSQL(SQL_CREATE_TABLE_MRB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS bahan");
//        db.execSQL("DROP TABLE IF EXISTS mrb");
        onCreate(db);
    }

    //menampilkan data list resep
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

    //menampilkan data bahan
    public ArrayList<HashMap<String, String>> getAllBahan(){
        ArrayList<HashMap<String, String>> listbahan = new ArrayList<>();
        String QUERY = "SELECT * FROM bahan WHERE status = 'ada'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map1 = new HashMap<>();
                map1.put("id_bahan", cursor.getString(0));
                map1.put("nama_bahan", cursor.getString(1));
                map1.put("status", cursor.getString(2));
                listbahan.add(map1);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listbahan;
    }

    //menampilkan data belanja_bahan
    public ArrayList<HashMap<String, String>> getAllBelanja(){
        ArrayList<HashMap<String, String>> listbelanja = new ArrayList<>();
        String QUERY = "SELECT * FROM bahan WHERE status = 'beli'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_bahan", cursor.getString(0));
                map.put("nama_bahan", cursor.getString(1));
                map.put("status", cursor.getString(2));
                listbelanja.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  listbelanja;
    }


    //Menampilkan data mrb pada menu minggu ini dan hari ini
//    public ArrayList<HashMap<String, String>> getAllMRB() {
//        ArrayList<HashMap<String, String>> listMRB = new ArrayList<>();
//        String QUERY = "SELECT mrb.id_menu, mrb.nama.menu, bahan.nama_bahan, recipes.recipe_name, mrb.tanggal " +
//                "FROM mrb " +
//                "INNER JOIN bahan ON mrb.bahan_id = bahan.id_bahan " +
//                "INNER JOIN recipes ON mrb.resep_id = recipes.id";
//
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(QUERY, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("id_menu", cursor.getString(0));
//                map.put("menu_name", cursor.getString(1));
//                map.put("nama_bahan", cursor.getString(2));
//                map.put("recipe_name", cursor.getString(3));
//                map.put("tanggal", cursor.getString(4));
//                // Memasukkan map ke dalam list
//                listMRB.add(map);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        database.close();
//
//        return listMRB;
//    }


    //insert data resep
    public void insert(String recipe_name, String material_name, String instruction){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY ="INSERT INTO recipes (recipe_name, material_name, instruction) VALUES('"+recipe_name+"', '"+material_name+"', '"+instruction+"')";
        database.execSQL(QUERY);
    }

    //insert data bahan
    public void insertBahan(String nama_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        values.put("status", "ada");
        String QUERY = "INSERT INTO bahan (nama_bahan, status) VALUES ('"+nama_bahan+"','ada')";
        database.execSQL(QUERY);
    }

    //insert data belanja_bahan
    public void insertBelanja(String nama_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        values.put("status", "beli");
        String QUERY = "INSERT INTO bahan (nama_bahan, status) VALUES ('"+nama_bahan+"','beli')";
        database.execSQL(QUERY);
    }

    //melakukan update(edit) data resep
    public void update(int id, String recipe_name, String material_name, String instruction){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "UPDATE recipes SET recipe_name = '"+recipe_name+"', material_name = '"+material_name+"', instruction = '"+instruction+"' WHERE id = "+id;
        database.execSQL(QUERY);
    }

    //melakukan update(edit) data bahan
    public void updateBahan(int id_bahan, String nama_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        String whereClause = "id_bahan = ?";
        String[] whereArgs = {String.valueOf(id_bahan)};
        values.put("status","ada");
        database.update("bahan", values, whereClause, whereArgs);
    }

    //melakukan update(edit) data belanja_bahan
    public void updateBelanja(int id_bahan, String nama_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        String whereClause = "id_bahan = ?";
        String[] whereArgs = {String.valueOf(id_bahan)};
        values.put("status","beli");
        database.update("bahan", values, whereClause, whereArgs);
    }

    //menghapus data resep
    public void delete(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM recipes WHERE id = "+id;
        database.execSQL(QUERY);
    }

    //menghapus data bahan
    public void deleteBahan(int id_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM bahan WHERE id_bahan = "+id_bahan;
        database.execSQL(QUERY);
    }

}
