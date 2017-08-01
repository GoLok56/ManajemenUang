package kost.golok.object;

import android.content.Context;
import android.database.Cursor;

import java.util.HashSet;

import kost.golok.database.DBQuery;

public class Laporan {

    private static HashSet<String> Bulan;
    private int jumlah, tipe;
    private String waktu;

    public Laporan(int jumlah, int tipe, String waktu) {
        this.jumlah = jumlah;
        this.waktu = waktu;
        this.tipe = tipe;
    }

    public static HashSet<String> getBulan(Context ctx) {
        Bulan = new HashSet<>();
        Cursor c = DBQuery.selectLaporan(ctx);
        while (c.moveToNext()) {
            Bulan.add(c.getString(c.getColumnIndex("bulan")));
        }
        c.close();
        return Bulan;
    }

    public int getTipe() {
        return tipe;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getWaktu() {
        return waktu;
    }
}
