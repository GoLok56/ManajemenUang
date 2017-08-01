package kost.golok.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static kost.golok.database.DBHelper.getDb;

/**
 * DBQuery adalah class static untuk mengeksekusi query-query yang dibutuhkan dalam aplikasi
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
public class DBQuery {

    /** String Array yang menyimpan semua nama kolom pada tabel transaksi */
    public static final String[] SELECT_ALL = {
            DBSchema.Transaksi._ID,
            DBSchema.Transaksi.COLUMN_JUMLAH,
            DBSchema.Transaksi.COLUMN_DESKRIPSI,
            DBSchema.Transaksi.COLUMN_TANGGAL,
            DBSchema.Transaksi.COLUMN_TIPE
    };

    /** String array yang menyimpan kolom jumlah dan tipe pada tabel transaksi */
    public static final String[] SELECT_AMOUNT = {
            DBSchema.Transaksi.COLUMN_JUMLAH,
            DBSchema.Transaksi.COLUMN_TIPE
    };

    /** Private constructor, karena DBQuery merupakan class static */
    private DBQuery() {
    }

    /**
     * Method untuk mengeksekusi Select Query
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @param column kolom yang ingin diambil nilainya
     * @param selection Where Clause, Like Clause, etc
     * @param args Argument untuk selection
     * @return Tabel nilai dari kolom yang dipilih berupa {@link Cursor}
     */
    public static Cursor select(Context ctx, String[] column, String selection, String[] args) {
        SQLiteDatabase db = DBHelper.getDb(ctx);
        return db.query(
                DBSchema.Transaksi.TABLE_NAME,
                column,
                selection,
                args,
                null,
                null,
                null
        );
    }

    /**
     * Melakukan Select Query untuk mendapatkan tanggal transaksi dalam format "MMMM YYYY" dari
     * database
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @return Tabel nilai Bulan, Jml nominal transaksi berdasarkan jenis dan bulan dalam bentuk
     * {@link Cursor}
     */
    public static Cursor selectLaporan(Context ctx) {
        final String SUBSTR_BULAN = "SUBSTR(" + DBSchema.Transaksi.COLUMN_TANGGAL + ",3)";
        String query = "SELECT SUM(" + DBSchema.Transaksi.COLUMN_JUMLAH + ") AS 'amount', " +
                SUBSTR_BULAN + " AS 'bulan', " + DBSchema.Transaksi.COLUMN_TIPE +
                " FROM " + DBSchema.Transaksi.TABLE_NAME +
                " GROUP BY " + SUBSTR_BULAN + ", " + DBSchema.Transaksi.COLUMN_TIPE;

        SQLiteDatabase db = getDb(ctx);
        return db.rawQuery(query, null);
    }

    /**
     * Delete suatu baris pada tabel tranaksi
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @param id id baris yang ingin dihapus
     */
    public static void delete(Context ctx, String id){
        SQLiteDatabase db = DBHelper.getDb(ctx);
        String where = DBSchema.Transaksi._ID + "=" + id;
        db.delete(DBSchema.Transaksi.TABLE_NAME, where, null);
    }

    /**
     * Memasukkan data baris baru kedalam database
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @param jumlah Nilai dari kolom jumlah
     * @param tipe Nilai dari kolom tipe
     * @param date Nilai dari kolom date
     * @param desc Nilai dari kolom deskripsi
     */
    public static void insert(Context ctx, int jumlah, int tipe, String date, String desc){
        ContentValues values = getContentValues(jumlah, tipe, date, desc);
        SQLiteDatabase db = DBHelper.getDb(ctx);
        db.insert(DBSchema.Transaksi.TABLE_NAME, null, values);
    }

    /**
     * Update data dari salah satu baris dalam database
     *
     * @param ctx Context dari {@link android.view.View} yang aktif
     * @param jumlah Nilai dari kolom jumlah
     * @param tipe Nilai dari kolom tipe
     * @param date Nilai dari kolom date
     * @param desc Nilai dari kolom deskripsi
     * @param id Nilai dari kolom id baris yang ingin diupdate
     */
    public static void update(Context ctx, int jumlah, int tipe, String date, String desc, long id){
        SQLiteDatabase db = DBHelper.getDb(ctx);
        ContentValues values = getContentValues(jumlah, tipe, date, desc);
        db.update(DBSchema.Transaksi.TABLE_NAME, values, "_id=" + id, null);
    }

    /**
     * @param jumlah Nilai dari kolom jumlah
     * @param tipe Nilai dari kolom tipe
     * @param date Nilai dari kolom date
     * @param desc Nilai dari kolom deskripsi
     * @return {@link ContentValues} untuk update database
     */
    private static ContentValues getContentValues(int jumlah, int tipe, String date, String desc){
        ContentValues values = new ContentValues();
        values.put(DBSchema.Transaksi.COLUMN_TANGGAL, date);
        values.put(DBSchema.Transaksi.COLUMN_JUMLAH, jumlah);
        values.put(DBSchema.Transaksi.COLUMN_DESKRIPSI, desc);
        values.put(DBSchema.Transaksi.COLUMN_TIPE, tipe);
        return values;
    }

}
