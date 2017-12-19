package io.github.golok56.manajemenuang.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.github.golok56.manajemenuang.database.AppDatabase;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.Formatter;
import io.github.golok56.manajemenuang.utility.PreferenceManager;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Satria Adi Putra
 */

public class TransactionDetailViewModel extends AndroidViewModel {

    private AppDatabase mDatabase;
    private PreferenceManager mPref;

    private MutableLiveData<Transaction> mTransaction = new MutableLiveData<>();

    public TransactionDetailViewModel(@NonNull Application application) {
        super(application);
        mDatabase = AppDatabase.getInstance(application);
        mPref = PreferenceManager.getInstance(application);
    }

    public MutableLiveData<Transaction> getTransaction() {
        return mTransaction;
    }

    public long getId() {
        return mTransaction.getValue().getId();
    }

    public void setId(long id) {
        mDatabase.getTransactionTable().getTransaction(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transaction -> mTransaction.setValue(transaction));
    }

    public String getAmount() {
        return Formatter.INSTANCE.formatCurrency(mTransaction.getValue().getAmount());
    }

    public String getType() {
        switch (mTransaction.getValue().getType()) {
            case Transaction.EXPENSE_TYPE:
                return "Pengeluaran";
            case Transaction.INCOME_TYPE:
                return "Pemasukan";
        }
        return "";
    }

    public String getDate() {
        return mTransaction.getValue().getDate();
    }

    public void delete() {
        Completable.fromAction(() -> mDatabase.getTransactionTable().delete(mTransaction.getValue()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void revert() {
        mPref.revert(mTransaction.getValue());
    }

}
