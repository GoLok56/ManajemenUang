package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.github.golok56.manajemenuang.models.Report
import io.github.golok56.manajemenuang.utility.Formatter
import io.github.golok56.manajemenuang.utility.PreferenceManager
import java.util.*

/**
 * @author Satria Adi Putra
 */

class UserInfoViewModel(app: Application) : AndroidViewModel(app) {
    private val mPref: PreferenceManager = PreferenceManager.getInstance(app)

    private val mReports: MutableLiveData<List<Report>> = MutableLiveData()

    val months: LiveData<List<String>>
        get() {
            val monthsLive = MutableLiveData<List<String>>()
            val months: MutableList<String>

            val reports = mReports.value
            if (reports == null || reports.isEmpty()) {
                months = listOf(Formatter.formatDate("MMMM yyyy")).toMutableList()
            } else {
                val reportSize = reports.size
                months = ArrayList(reportSize)
                for (i in 0 until reportSize) {
                    months.add(reports[i].month)
                }
            }
            monthsLive.value = months
            return monthsLive
        }

    val userName: String?
        get() = mPref.userName

    val wallet: Double
        get() = mPref.wallet

    init {
//        val database = AppDatabase.getInstance(app)
//        database.transactionTable.reports
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { reports -> mReports.setValue(reports) }
    }

    fun getAmountOn(month: String, type: Int): String {
        val reports = mReports.value
        var i = 0
        val size = reports?.size ?: 0
        while (i < size) {
            val report = reports!![i]
            if (report.month == month && report.type == type) {
                return Formatter.formatCurrency(report.amount.toDouble())
            }
            i++
        }

        return Formatter.formatCurrency(.0)
    }

}
