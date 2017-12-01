package io.github.golok56.database;

import android.provider.BaseColumns;

/**
 * Class static yang menyimpan informasi mengenai nama-nama tabel beserta
 * nama kolomnya
 */
public final class DBSchema {

    // Private Constructor, karena DBSchema merupakan class static
    private DBSchema() {}

    /** Inner class yang menyimpan info tentang tabel Transaksi */
    public static class Transaksi implements BaseColumns {
        static final String TABLE_NAME = "transaksi";
        public static final String COLUMN_JUMLAH = "jumlah";
        public static final String COLUMN_DESKRIPSI = "deskripsi";
        public static final String COLUMN_TANGGAL = "date";
        public static final String COLUMN_TIPE = "tipe";

        public static final int TIPE_PENGELUARAN = 0;
        public static final int TIPE_PEMASUKAN = 1;
    }
}
