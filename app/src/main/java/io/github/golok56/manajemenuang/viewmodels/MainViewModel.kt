package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.utility.PreferenceManager

/**
 * @author Satria Adi Putra
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    var backPressedDuration: Long = 0

    private val mPref: PreferenceManager = PreferenceManager.getInstance(application)

    val confirmText: String
        get() = getApplication<Application>().getString(R.string.konfirmasi)

    fun hasLogin(): Boolean {
        return mPref.userName != null
    }

}
