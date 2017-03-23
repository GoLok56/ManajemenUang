package kost.golok.database;

import android.provider.BaseColumns;

public final class DBSchema {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DBSchema() {
    }

    /* Inner class that defines the table contents */
    public static class Pengeluaran implements BaseColumns {
        public static final String TABLE_NAME = "pengeluaran";
        public static final String COLUMN_JUMLAH = "jumlah";
        public static final String COLUMN_DESKRIPSI = "deskripsi";
        public static final String COLUMN_TANGGAL = "date";
        public static final String COLUMN_TIPE = "tipe";

        public static final int TIPE_PENGELUARAN = 0;
        public static final int TIPE_PEMASUKAN = 1;
    }
}
