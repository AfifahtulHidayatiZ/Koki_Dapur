package com.example.kokidapur.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kokidapur.model.DataBahanMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 8;
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
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_resep INTEGER," +
                "id_menu INTEGER, "+
                "FOREIGN KEY (id_resep) REFERENCES recipes (id)," +
                "FOREIGN KEY (id_menu) REFERENCES mrb (id_menu))";
        db.execSQL(SQL_CREATE_TABLE_RESEPMRB);

        final String SQL_CREATE_TABLE_BAHANMRB = "CREATE TABLE bahan_menu ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT ,"+
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

    //menampilkan bahan pada detail menu
    public ArrayList<HashMap<String,String>> getAllBahanDM(int id_menu){
        ArrayList<HashMap<String, String>> listbelanja = new ArrayList<>();
        String QUERY = "SELECT bahan_menu.id AS id, bahan_menu.id_bahan AS id_bahan,bahan.nama_bahan AS nama, bahan.status AS status, bahan.jumlah AS jumlah FROM bahan_menu INNER JOIN bahan ON bahan.id_bahan = bahan_menu.id_bahan WHERE id_menu =" +id_menu;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("id_bahan", cursor.getString(1));
                map.put("nama_bahan", cursor.getString(2));
                map.put("status", cursor.getString(3));
                map.put("jumlah", cursor.getString(4));
                listbelanja.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  listbelanja;
    }

    //menampilkan resep pada detail menu
    public ArrayList<HashMap<String, String>> getAllResepDM(int id_menu){
        ArrayList<HashMap<String, String>> listresepDM = new ArrayList<>();
        String QUERY = "SELECT resep_mrb.id AS id, resep_mrb.id_resep AS id_resep, recipes.recipe_name AS nama_resep, recipes.material_name AS nama_bahan, recipes.instruction FROM resep_mrb INNER JOIN recipes ON recipes.id = resep_mrb.id_resep WHERE id_menu =" +id_menu;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(QUERY,null);
        if (cursor.moveToFirst()){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", cursor.getString(0));
            map.put("id_resep", cursor.getString(1));
            map.put("recipe_name", cursor.getString(2));
            map.put("material_name", cursor.getString(3));
            map.put("instruction", cursor.getString(4));
            listresepDM.add(map);
        }while (cursor.moveToNext());
        cursor.close();
        return listresepDM;
    }

    //menampilkan data MRB (MENU)
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

    //insert data resep ke detail menu dari list resep
    public void insertResepDB (int id_menu, int id_resep){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_menu", id_menu);
        values.put("id_resep", id_resep);
        String QUERY ="INSERT INTO resep_mrb (id_menu, id_resep) VALUES ("+id_menu+","+id_resep+")";
        database.execSQL(QUERY);
    }


    //insert data bahan ke detail menu dari list bahan
    public void insertBahanDM(int id_menu, int id_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_menu", id_menu);
        values.put("id_bahan", id_bahan);
        String QUERY = "INSERT INTO bahan_menu (id_menu, id_bahan) VALUES ("+id_menu+","+id_bahan+")";
        database.execSQL(QUERY);

    }

    //insert data bahan baru ke detail menu
    public void inserBahanBaruDM(int id_menu, int id_bahan, String nama_bahan){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_menu", id_menu);
        values.put("id_bahan", id_bahan);
        values.put("nama_bahan", nama_bahan);
        values.put("status", "beli");
        String QUERY = "INSERT INTO bahan_menu (id_menu, id_bahan, nama_bahan, status) VALUES ("+id_menu+","+id_bahan+", '"+nama_bahan+"','beli')";
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

    //menghapus data bahan pada Detail Menu
    public void deleteBahanDM(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM bahan_menu WHERE id= "+id;
        database.execSQL(QUERY);
    }

    //menghapus data resep pada Detail Menu
    public void deleteResepDM(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "DELETE FROM resep_mrb WHERE id="+id;
        database.execSQL(QUERY);
    }

}
