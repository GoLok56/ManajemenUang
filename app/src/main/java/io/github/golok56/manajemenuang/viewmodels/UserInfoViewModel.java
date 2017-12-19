package io.github.golok56.manajemenuang.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.golok56.manajemenuang.database.AppDatabase;
import io.github.golok56.manajemenuang.models.Report;
import io.github.golok56.manajemenuang.utility.Formatter;
import io.github.golok56.manajemenuang.utility.PreferenceManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Satria Adi Putra
 */

public class UserInfoViewModel extends AndroidViewModel {

    private PreferenceManager mPref;

    private MutableLiveData<List<Report>> mReports;

    public UserInfoViewModel(Application app) {
        super(app);
        AppDatabase database = AppDatabase.getInstance(app);

        mReports = new MutableLiveData<>();

        database.getTransactionTable().getReports()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reports -> mReports.setValue(reports));

        mPref = PreferenceManager.getInstance(app);
    }

    public LiveData<List<String>> getMonths() {
        MutableLiveData<List<String>> monthsLive = new MutableLiveData<>();
        List<String> months;

        List<Report> reports = mReports.getValue();
        if (reports == null || reports.size() == 0) {
            months = Collections.singletonList(Formatter.INSTANCE.formatDate("MMMM yyyy"));
        } else {
            int reportSize = reports.size();
            months = new ArrayList<>(reportSize);
            for (int i = 0; i < reportSize; i++) {
                months.add(reports.get(i).getMonth());
            }
        }
        monthsLive.setValue(months);
        return monthsLive;
    }

    public String getAmountOn(String month, int type) {
        List<Report> reports = mReports.getValue();
        for (int i = 0, size = reports != null ? reports.size() : 0; i < size; i++) {
            Report report = reports.get(i);
            if (report.getMonth().equals(month) && report.getType() == type) {
                return Formatter.INSTANCE.formatCurrency((double) report.getAmount());
            }
        }

        return Formatter.INSTANCE.formatCurrency(.0);
    }

    public String getUserName() {
        return mPref.getUserName();
    }

    public double getWallet() {
        return mPref.getWallet();
    }

}
