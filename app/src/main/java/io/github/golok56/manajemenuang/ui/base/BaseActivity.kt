package io.github.golok56.manajemenuang.ui.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * @author Satria Adi Putra
 */
open class BaseActivity : AppCompatActivity() {

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
