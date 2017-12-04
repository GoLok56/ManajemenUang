package io.github.golok56.manajemenuang.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.github.golok56.manajemenuang.database.AppDatabase;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.PreferenceManager;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Satria Adi Putra
 */
public class TransactionFormViewModel extends AndroidViewModel {

    private AppDatabase mDatabase;
    private PreferenceManager mPref;

    private MutableLiveData<Transaction> mTransaction = new MutableLiveData<>();

    public TransactionFormViewModel(@NonNull Application application) {
        super(application);
        mDatabase = AppDatabase.getInstance(application);
        mPref = PreferenceManager.getInstance(application);
    }

    public void setId(long id) {
        mDatabase.getTransactionTable().getTransaction(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transaction -> mTransaction.setValue(transaction));
    }

    public MutableLiveData<Transaction> getTransaction() {
        return mTransaction;
    }

    public String getAmount() {
        return String.valueOf((int) mTransaction.getValue().getAmount());
    }

    public String getDesc() {
        return mTransaction.getValue().getDescription();
    }

    public boolean isIncome() {
        return mTransaction.getValue().getType() == Transaction.INCOME_TYPE;
    }

    public void save(double amount, int type, String desc, String date) {
        Transaction transaction = mTransaction.getValue();
        if (transaction != null) {
            mPref.revert(transaction);

            transaction.setAmount(amount);
            transaction.setDate(date);
            transaction.setDescription(desc);
            transaction.setType(type);

            mPref.updateWallet(transaction);
            Completable.fromAction(() -> mDatabase.getTransactionTable().insert(transaction))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            return;
        }

        Transaction newTransaction = new Transaction(amount, desc, date, type);
        Completable.fromAction(() -> mDatabase.getTransactionTable().insert(newTransaction))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        mPref.updateWallet(newTransaction);
    }

}
