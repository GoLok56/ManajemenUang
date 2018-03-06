package io.github.golok56.manajemenuang.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.utility.PreferenceManager

/**
 * @author Satria Adi Putra
 */

class NewUserViewModel(app: Application) : AndroidViewModel(app) {
    private val mPref: PreferenceManager = PreferenceManager.getInstance(app)

    val validationText: String
        get() = getApplication<Application>().getString(R.string.validasi_dialog)

    fun createUser(name: String, nominal: String) {
        mPref.userName = name
        mPref.setWalet(nominal)
    }

}
