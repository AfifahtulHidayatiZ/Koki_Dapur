package com.example.kokidapur.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 7;
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
                "jumlah TEXT," +
                "status TEXT CHECK (status IN('ada','beli')))";
        db.execSQL(SQL_CREATE_TABLE_BAHAN);

        final String SQL_CREATE_TABLE_MRB = "CREATE TABLE mrb (" +
                "id_menu INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama_menu TEXT NOT NULL, " +
                "tanggal DATE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(SQL_CREATE_TABLE_MRB);

        final String SQL_CREATE_TABLE_RESEPMRB = "CREATE TABLE resep_mrb ("+
                "id_resep INTEGER," +
                "id_menu INTEGER, "+
                "FOREIGN KEY (id_resep) REFERENCES recipes (id)," +
                "FOREIGN KEY (id_menu) REFERENCES mrb (id_menu))";
        db.execSQL(SQL_CREATE_TABLE_RESEPMRB);

        final String SQL_CREATE_TABLE_BAHANMRB = "CREATE TABLE bahan_menu ("+
                "id_bahan INTEGER,"+
                "id_menu INTEGER," +
                "FOREIGN KEY (id_bahan) REFERENCES bahan (id_bahan)," +
                "FOREIGN KEY (id_menu) REFERENCES mrb (id_menu))";
        db.execSQL(SQL_CREATE_TABLE_BAHANMRB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS bahan");
        db.execSQL("DROP TABLE IF EXISTS mrb");
        db.execSQL("DROP TABLE IF EXISTS resep_mrb");
        db.execSQL("DROP TABLE IF EXISTS bahan_menu");
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
//                map1.put("status", cursor.getString(3));
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
                map.put("jumlah", cursor.getString(2));
//                map.put("status", cursor.getString(3));
                listbelanja.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  listbelanja;
    }


    //Menampilkan data mrb pada menu minggu ini dan hari ini
//    public ArrayList<HashMap<String, String>> getAllMRB() {
//        ArrayList<HashMap<String, String>> listMRB = new ArrayList<>();
//        String QUERY = "SELECT mrb.id_menu, mrb.nama_menu, bahan.nama_bahan, recipes.recipe_name " +
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
//                map.put("nama_menu", cursor.getString(1));
//                map.put("nama_bahan", cursor.getString(2));
//                map.put("recipe_name", cursor.getString(3));
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

    //menampilkan data MRB
    public ArrayList<HashMap<String, String>> getMenuMRB(){
        ArrayList<HashMap<String, String>> listMenu = new ArrayList<>();
        String QUERY = "SELECT * FROM mrb";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_menu", cursor.getString(0));
                map.put("nama_menu", cursor.getString(1));
                map.put("tanggal", cursor.getString(2));
                listMenu.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listMenu;
    }

    public ArrayList<HashMap<String, String>> getTanggalMenu(String tanggal){
        ArrayList<HashMap<String, String>> listTanggalMenu = new ArrayList<>();
        String QUERY = "SELECT * FROM mrb WHERE date(tanggal) = '"+tanggal+"'";
        Log.d("tQuesry", QUERY);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_menu", cursor.getString(0));
                map.put("nama_menu", cursor.getString(1));
                map.put("tanggal", cursor.getString(2));
                listTanggalMenu.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("Lihat", listTanggalMenu.toString());
        return listTanggalMenu;
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
    public void insertBelanja(String nama_bahan, String jumlah){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        values.put("jumlah", jumlah);
        values.put("status", "beli");
        String QUERY = "INSERT INTO bahan (nama_bahan, jumlah, status) VALUES ('"+nama_bahan+"','"+jumlah+"','beli')";
        database.execSQL(QUERY);
    }

    //insert data menu MRB
    public void insertMRB(String nama_menu, String tanggal){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY ="INSERT INTO mrb (nama_menu, tanggal) VALUES ('"+nama_menu+"', '"+tanggal+"')";
        Log.d("Insert", QUERY);
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
    public void updateBelanja(int id_bahan, String nama_bahan, String jumlah){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_bahan", nama_bahan);
        values.put("jumlah", jumlah);
        String whereClause = "id_bahan = ?";
        String[] whereArgs = {String.valueOf(id_bahan)};
        values.put("status","beli");
        database.update("bahan", values, whereClause, whereArgs);
    }

    //melakukan update(edit) data menu MRB
    public void updateMRB(int id_menu, String nama_menu){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "UPDATE mrb SET nama_menu ='"+nama_menu+"' WHERE id_menu = "+id_menu;
        database.execSQL(QUERY);
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

    //menghapus data menu MRB
    public void deleteMRB(int id_menu){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM mrb WHERE id_menu = "+id_menu;
        database.execSQL(QUERY);
    }
//
//    //menghapus data mrb
//    public void dateMRB(int id_menu){
//        SQLiteDatabase database = this.getWritableDatabase();
//        String QUERY = "DELETE FROM mrb WHERE id_menu =" +id_menu;
//        database.execSQL(QUERY);
//    }

}
