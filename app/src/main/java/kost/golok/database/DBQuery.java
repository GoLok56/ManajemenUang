package kost.golok.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBQuery {

    public static final String[] SELECT_ALL = {
            DBSchema.Pengeluaran._ID,
            DBSchema.Pengeluaran.COLUMN_JUMLAH,
            DBSchema.Pengeluaran.COLUMN_DESKRIPSI,
            DBSchema.Pengeluaran.COLUMN_TANGGAL,
            DBSchema.Pengeluaran.COLUMN_TIPE
    };

    public static final String[] SELECT_AMOUNT = {
            DBSchema.Pengeluaran.COLUMN_JUMLAH,
            DBSchema.Pengeluaran.COLUMN_TIPE
    };

    private DBQuery() {
    }

    public static Cursor select(Context ctx, String[] column, String selection, String[] args) {
        // Creating SQLiteDatabase instance
        DBHelper dbHelper = new DBHelper(ctx);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Executing INSERT SQLite query
        Cursor cursor = db.query(
                DBSchema.Pengeluaran.TABLE_NAME,
                column,
                selection,
                args,
                null,
                null,
                null
        );
        return cursor;
    }

    public static Cursor rawQuery(Context ctx) {
        // Creating SQLiteDatabase instance
        DBHelper dbHelper = new DBHelper(ctx);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Executing INSERT SQLite query
        final String SUBSTR_BULAN = "SUBSTR(" + DBSchema.Pengeluaran.COLUMN_TANGGAL + ",3)";
        String query = "SELECT SUM(" + DBSchema.Pengeluaran.COLUMN_JUMLAH + ") AS 'amount', " +
                SUBSTR_BULAN + " AS 'bulan', " + DBSchema.Pengeluaran.COLUMN_TIPE +
                " FROM " + DBSchema.Pengeluaran.TABLE_NAME +
                " GROUP BY " + SUBSTR_BULAN + ", " + DBSchema.Pengeluaran.COLUMN_TIPE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

}
