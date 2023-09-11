package com.example.kokidapur.contract;

public class ContractDatabase {

    public class MenuContract{
        public static final String TABLE_NAME = "menu";
        public static final String COLUMN_ID_MENU = "id_menu";
        public static final String COLUMN_NAMA_MENU = "nama_menu";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " +TABLE_NAME+ "( "+
                        COLUMN_ID_MENU+ "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAMA_MENU +"TEXT)";
    }

    public class  ResepContract{
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_ID_RESEP = "id";
        public static final String COLUMN_NAMA_RESEP = "recipe_name";
        public static final String COLUMN_NAMA_BAHAN = "material_name";
        public static final String COLUMN_CARA_MEMBUAT = "instruction";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID_RESEP + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAMA_RESEP + " TEXT NOT NULL," +
                        COLUMN_NAMA_BAHAN + " TEXT NOL NULL," +
                        COLUMN_CARA_MEMBUAT + " TEXT NOT NULL)";
    }

    public class BahanContract{
        public static final String TABLE_NAME = "bahan";
        public static final String COLUMN_ID_BAHAN = "id_bahan";
        public static final String COLUMN_NAMA_BAHAN = "nama_bahan";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_JUMLAH = "jumlah";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID_BAHAN + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAMA_BAHAN + " TEXT," +
                        COLUMN_STATUS + " TEXT CHECK (status IN('tersedia','tidak tersedia')," +
                        COLUMN_JUMLAH + " TEXT)";
    }

    public class MRBContract{
        public static final String TABLE_NAME = "mrb";
        public static final String COLUMN_ID_MENUS = "id_mrb";
        public static final String COLUMN_ID_MENU = "id_menu";
        public static final String COLUMN_ID_RESEP = "id";
        public static final String COLUMN_ID_BAHAN = "id_bahan";
        public static final String COLUMN_TANGGAL = "tanggal";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_UPDATED_AT = "updated_at";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID_MENUS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_ID_MENU + " INTEGER," +
                        COLUMN_ID_RESEP + " INTEGER," +
                        COLUMN_ID_BAHAN + " INTEGER," +
                        COLUMN_TANGGAL + " DATETIME," +
                        COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        COLUMN_UPDATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (" + COLUMN_ID_MENU + ") REFERENCES " + MenuContract.TABLE_NAME + "(" + MenuContract.COLUMN_ID_MENU + ")," +
                        "FOREIGN KEY (" + COLUMN_ID_RESEP + ") REFERENCES " + ResepContract.TABLE_NAME + "(" + ResepContract.COLUMN_ID_RESEP + ")," +
                        "FOREIGN KEY (" + COLUMN_ID_BAHAN + ") REFERENCES " + BahanContract.TABLE_NAME + "(" + BahanContract.COLUMN_ID_BAHAN + "))";
    }
}
