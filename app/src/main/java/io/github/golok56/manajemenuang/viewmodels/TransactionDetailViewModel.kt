package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

import io.github.golok56.manajemenuang.database.AppDatabase
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.utility.Formatter
import io.github.golok56.manajemenuang.utility.PreferenceManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Satria Adi Putra
 */

class TransactionDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val mDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val mPref: PreferenceManager = PreferenceManager.getInstance(application)

    val transaction = MutableLiveData<Transaction>()

    var id: Long
        get() {
            return transaction.value!!.id
        }
        set(id) {
            mDatabase.transactionTable.getTransaction(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { transaction -> this.transaction.setValue(transaction) }
        }

    val amount: String
        get() = Formatter.formatCurrency(transaction.value!!.amount)

    val type: String
        get() {
            when (transaction.value!!.type) {
                Transaction.EXPENSE_TYPE -> return "Pengeluaran"
                Transaction.INCOME_TYPE -> return "Pemasukan"
            }
            return ""
        }

    val date: String?
        get() = transaction.value!!.date

    fun delete() {
        Completable.fromAction { mDatabase.transactionTable.delete(transaction.value!!) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun revert() {
        mPref.revert(transaction.value!!)
    }

}
