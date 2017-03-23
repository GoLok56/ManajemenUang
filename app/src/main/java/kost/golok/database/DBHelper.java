package kost.golok.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * The version of database
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * The name of database
     */
    public static final String DATABASE_NAME = "uang.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBSchema.Pengeluaran.TABLE_NAME + " (" +
            DBSchema.Pengeluaran._ID + " INTEGER PRIMARY KEY, " +
            DBSchema.Pengeluaran.COLUMN_JUMLAH + " INTEGER, " +
            DBSchema.Pengeluaran.COLUMN_DESKRIPSI + " TEXT, " +
            DBSchema.Pengeluaran.COLUMN_TANGGAL + " TEXT, " +
            DBSchema.Pengeluaran.COLUMN_TIPE + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
