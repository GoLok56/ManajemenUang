package io.github.golok56.manajemenuang.utility

import android.content.Context
import android.content.SharedPreferences

import io.github.golok56.manajemenuang.models.Transaction

class PreferenceManager private constructor(ctx: Context) {
    private val mPref: SharedPreferences

    var userName: String?
        get() = mPref.getString(PreferenceManager.NAME, null)
        set(name) = mPref.edit().putString(NAME, name).apply()

    val wallet: Double
        get() = java.lang.Double.parseDouble(mPref.getString(WALLET, "0"))

    init {
        mPref = ctx.getSharedPreferences(PREFERENCES_NAMES, Context.MODE_PRIVATE)
    }

    fun revert(transaction: Transaction) {
        var wallet = wallet
        when (transaction.type) {
            Transaction.EXPENSE_TYPE -> wallet += transaction.amount
            Transaction.INCOME_TYPE -> wallet -= transaction.amount
        }
        mPref.edit().putString(WALLET, wallet.toString()).apply()
    }

    fun setWalet(wallet: String) {
        mPref.edit().putString(WALLET, wallet).apply()
    }

    fun updateWallet(transaction: Transaction) {
        var wallet = wallet
        when (transaction.type) {
            Transaction.EXPENSE_TYPE -> wallet -= transaction.amount
            Transaction.INCOME_TYPE -> wallet += transaction.amount
        }
        mPref.edit().putString(WALLET, wallet.toString()).apply()
    }

    companion object {
        private val PREFERENCES_NAMES = "Prefs"
        private val NAME = "nameKey"
        private val WALLET = "walletKey"
        private var sInstance: PreferenceManager? = null

        fun getInstance(ctx: Context): PreferenceManager {
            if (sInstance == null) {
                sInstance = PreferenceManager(ctx)
            }

            return sInstance as PreferenceManager
        }
    }

}
