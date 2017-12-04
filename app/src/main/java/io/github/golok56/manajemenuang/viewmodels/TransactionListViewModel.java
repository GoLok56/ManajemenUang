package io.github.golok56.manajemenuang.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import io.github.golok56.manajemenuang.database.AppDatabase;
import io.github.golok56.manajemenuang.models.Transaction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Satria Adi Putra
 */
public class TransactionListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Transaction>> mTransactions;

    public TransactionListViewModel(Application app) {
        super(app);
        AppDatabase database = AppDatabase.getInstance(getApplication());

        mTransactions = new MutableLiveData<>();

        database.getTransactionTable().getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transactions -> mTransactions.setValue(transactions));
    }

    public LiveData<List<Transaction>> getTransactions() {
        return mTransactions;
    }

}
