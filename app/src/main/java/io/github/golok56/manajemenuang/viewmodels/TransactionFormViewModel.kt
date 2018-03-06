package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

import io.github.golok56.manajemenuang.database.AppDatabase
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.utility.PreferenceManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Satria Adi Putra
 */
class TransactionFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val mPref: PreferenceManager = PreferenceManager.getInstance(application)

    val transaction = MutableLiveData<Transaction>()

    val amount: String
        get() = transaction.value!!.amount.toInt().toString()

    val desc: String?
        get() = transaction.value!!.description

    val isIncome: Boolean
        get() = transaction.value!!.type == Transaction.INCOME_TYPE

    fun setId(id: Long) {
        mDatabase.transactionTable.getTransaction(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { transaction -> this.transaction.setValue(transaction) }
    }

    fun save(amount: Double, type: Int, desc: String, date: String) {
        val transaction = this.transaction.value
        if (transaction != null) {
            mPref.revert(transaction)

            transaction.amount = amount
            transaction.date = date
            transaction.description = desc
            transaction.type = type

            mPref.updateWallet(transaction)
            Completable.fromAction { mDatabase.transactionTable.insert(transaction) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            return
        }

        val newTransaction = Transaction(amount, desc, date, type)
        Completable.fromAction { mDatabase.transactionTable.insert(newTransaction) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        mPref.updateWallet(newTransaction)
    }

}
