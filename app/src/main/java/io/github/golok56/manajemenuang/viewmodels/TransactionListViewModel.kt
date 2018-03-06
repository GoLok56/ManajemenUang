package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import io.github.golok56.manajemenuang.database.AppDatabase
import io.github.golok56.manajemenuang.models.Transaction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Satria Adi Putra
 */
class TransactionListViewModel(app: Application) : AndroidViewModel(app) {

    private val mTransactions: MutableLiveData<List<Transaction>>

    val transactions: LiveData<List<Transaction>>
        get() = mTransactions

    init {
        val database = AppDatabase.getInstance(getApplication())

        mTransactions = MutableLiveData()

        database.transactionTable.transactions
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { transactions -> mTransactions.setValue(transactions) }
    }

}
