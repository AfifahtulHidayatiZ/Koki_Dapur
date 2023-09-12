package com.example.kokidapur.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kokidapur.contract.ContractDatabase;

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
//        db.execSQL(ContractDatabase.MenusContract.SQL_CREATE_TABLE_MENUS);
//        db.execSQL(ContractDatabase.ResepContract.SQL_CREATE_TABLE_RESEP);
//        db.execSQL(ContractDatabase.BahanContract.SQL_CREATE_TABLE_BAHAN);
//        db.execSQL(ContractDatabase.MRBContract.SQL_CREATE_TABLE_MRB);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+ ContractDatabase.MenusContract.TABLE_MENUS);
//        db.execSQL("DROP TABLE IF EXISTS "+ ContractDatabase.ResepContract.TABLE_RESEP);
//        db.execSQL("DROP TABLE IF EXISTS "+ ContractDatabase.BahanContract.TABLE_BAHAN);
//        db.execSQL("DROP TABLE IF EXISTS "+ ContractDatabase.MRBContract.TABLE_MRB);
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS bahan");
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
//        String QUERY = "UPDATE bahan SET nama_bahan = '"+nama_bahan+"' WHERE id_bahan = "+id_bahan;
//        database.execSQL(QUERY);
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

    //menampilkan bahan
//    public ArrayList<HashMap<String, String>> getAllBahan(){
//        ArrayList<HashMap<String, String>> listbahan = new ArrayList<>();
//        String QUERY = "SELECT * FROM bahan";
//        SQLiteDatabase database1 = this.getWritableDatabase();
//        Cursor cursor = database1.rawQuery(QUERY, null);
//        if (cursor.moveToFirst()){
//            do {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("id_bahan", cursor.getString(0));
//                map.put("nama_bahan", cursor.getString(1));
////                map.put("status", cursor.getString(2));
////                map.put("jumlah", cursor.getString(3));
//                //memasukan map ke list
//                listbahan.add(map);
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//        return listbahan;
//    }
//
//    //insert bahan
//    public void insertBahan(String nama_bahan){
//        SQLiteDatabase database = this.getWritableDatabase();
//        //hilangkan status dan jumlah sementara
//        String QUERY = "INSERT INTO bahan (nama_bahan) VALUES ('"+nama_bahan+"')";
//        database.execSQL(QUERY);
//    }
//
//    //update bahan
//    public void updateBahan(int id_bahan, String nama_bahan, String status, String jumlah){
//        SQLiteDatabase database = this.getWritableDatabase();
//        String QUERY = "UPDATE bahan SET nama_bahan = '"+nama_bahan+"' WHERE id_bahan = "+id_bahan;
//        database.execSQL(QUERY);
//    }
//
//    //menghapus bahan
//    public void deleteBahan(int id_bahan){
//        SQLiteDatabase database = this.getWritableDatabase();
//        String QUERY = "DELETE * FROM bahan WHERE id_bahan = "+id_bahan;
//        database.execSQL(QUERY);
//    }
//
//    //menampilkan data menu
//    public Cursor getAllMenus(){
//        SQLiteDatabase database = this.getReadableDatabase();
//        return database.rawQuery("SELECT * FROM "+ ContractDatabase.MenusContract.TABLE_MENUS, null);
//    }
//
//    //insert data menu
//    public long insertMenu(String nama_menu){
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ContractDatabase.MenusContract.COLUMN_NAMA_MENU, nama_menu);
//        long id = database.insert(ContractDatabase.MenusContract.TABLE_MENUS, null, values);
//        database.close();
//        return id;
//    }
//
//    //melakukan update menu
//    public void updateMenu(int id_menu, String nama_menu){
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ContractDatabase.MenusContract.COLUMN_NAMA_MENU, nama_menu);
//        database.update(ContractDatabase.MenusContract.TABLE_MENUS, values, ContractDatabase.MenusContract.COLUMN_ID_MENU + "= ?" , new String[]{String.valueOf(id_menu)});
//        database.close();
//    }
//
//    //delete menu
//    public void deleteMenu(int id_menu){
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.delete(ContractDatabase.MenusContract.TABLE_MENUS, ContractDatabase.MenusContract.COLUMN_ID_MENU + "= ?", new String[]{String.valueOf(id_menu)});
//        database.close();
//    }


}
