package io.github.golok56.manajemenuang.utility;

import android.content.Context;
import android.content.SharedPreferences;

import io.github.golok56.manajemenuang.models.Transaction;

public class PreferenceManager {
    private static final String PREFERENCES_NAMES = "Prefs";
    private static final String NAME = "nameKey";
    private static final String WALLET = "walletKey";
    private static PreferenceManager sInstance;
    private SharedPreferences mPref;

    private PreferenceManager(Context ctx) {
        mPref = ctx.getSharedPreferences(PREFERENCES_NAMES, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(ctx);
        }

        return sInstance;
    }

    public String getUserName() {
        return mPref.getString(PreferenceManager.NAME, null);
    }

    public void setUserName(String name) {
        mPref.edit().putString(NAME, name).apply();
    }

    public double getWallet() {
        return Double.parseDouble(mPref.getString(WALLET, "0"));
    }

    public void revert(Transaction transaction) {
        mPref.edit()
                .putString(WALLET, String.valueOf(WalletUtil.revert(
                        getWallet(),
                        transaction.getAmount(),
                        transaction.isIncome()
                )))
                .apply();
    }

    public void setWalet(String wallet) {
        mPref.edit().putString(WALLET, wallet).apply();
    }

    public void updateWallet(Transaction transaction) {
        mPref.edit()
                .putString(WALLET, String.valueOf(WalletUtil.update(
                        getWallet(),
                        transaction.getAmount(),
                        transaction.isIncome()
                )))
                .apply();
    }

}
