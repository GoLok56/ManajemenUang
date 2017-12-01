package io.github.golok56.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.golok56.database.DBSchema;
import io.github.golok56.object.Transaction;

public class Preference {
    public static final String PREFERENCES_NAMES = "Prefs";
    public static final String NAME = "nameKey";
    public static final String DOMPET = "dompetKey";

    private Preference() {
    }

    public static void updatePref(AppCompatActivity activity, int tipe, int jumlah, @Nullable Transaction transaksi){
        if(transaksi != null) revertUpdate(activity, transaksi);

        int dompet = getDompet(activity);
        switch (tipe) {
            case DBSchema.Transaksi.TIPE_PENGELUARAN:
                dompet -= jumlah;
                break;
            case DBSchema.Transaksi.TIPE_PEMASUKAN:
                dompet += jumlah;
                break;
        }
        updateDompet(activity, dompet);
    }

    public static void revertUpdate(AppCompatActivity activity, Transaction transaksi){
        int dompet = getDompet(activity);
        int jumlah = Integer.parseInt(Formatter.formatAmount(transaksi));
        String tipe = transaksi.getType();
        switch (tipe) {
            case "Pengeluaran":
                dompet += jumlah;
                break;
            case "Pemasukan":
                dompet -= jumlah;
                break;
        }
        updateDompet(activity, dompet);
    }

    public static void createUser(SharedPreferences pref, String nama, String nominal){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Preference.NAME, nama);
        editor.putString(Preference.DOMPET, nominal);
        editor.apply();
    }

    private static void updateDompet(AppCompatActivity activity, int dompet){
        SharedPreferences pref = getPref(activity);
        String strDompet = "" + dompet;
        pref.edit().putString(Preference.DOMPET, strDompet).apply();
    }

    private static int getDompet(AppCompatActivity activity){
        SharedPreferences pref = getPref(activity);
        return Integer.parseInt(pref.getString(Preference.DOMPET, null));
    }

    private static SharedPreferences getPref(AppCompatActivity activity){
        return activity.getSharedPreferences(PREFERENCES_NAMES, Context.MODE_PRIVATE);
    }

}
