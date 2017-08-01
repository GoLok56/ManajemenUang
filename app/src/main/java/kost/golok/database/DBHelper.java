package kost.golok.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class DBHelper yang mengatur Database SQLite pada aplikasi
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
class DBHelper extends SQLiteOpenHelper {

    /** Versi database */
    private static final int DATABASE_VERSION = 2;

    /** Nama database */
    private static final String DATABASE_NAME = "uang.db";

    /** Create query, untuk membuat table transaksi */
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBSchema.Transaksi.TABLE_NAME + " (" +
            DBSchema.Transaksi._ID + " INTEGER PRIMARY KEY, " +
            DBSchema.Transaksi.COLUMN_JUMLAH + " INTEGER, " +
            DBSchema.Transaksi.COLUMN_DESKRIPSI + " TEXT, " +
            DBSchema.Transaksi.COLUMN_TANGGAL + " TEXT, " +
            DBSchema.Transaksi.COLUMN_TIPE + " INTEGER);";

    /**
     * Constructor DBHelper memanggil Parent Constructor {@link SQLiteOpenHelper}
     *
     * @param context Context dari {@link android.view.View} yang aktif
     */
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Membuat database "uang.db" jika belom eksis di memori
     *
     * @param db Instance {@link SQLiteDatabase}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Melakukan upgrade database, apabila database telah eksis dengan versi yang lama
     *
     * @param db Instance {@link SQLiteDatabase}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1)
            db.execSQL("ALTER TABLE pengeluaran RENAME TO " + DBSchema.Transaksi.TABLE_NAME + ";");

    }

    /**
     * Membuat instance {@link SQLiteDatabase} yang read-only
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @return Instance {@link SQLiteDatabase} yang read-only
     */
    static SQLiteDatabase getDb(Context ctx){
        DBHelper dbHelper = new DBHelper(ctx);
        return dbHelper.getReadableDatabase();
    }

}
