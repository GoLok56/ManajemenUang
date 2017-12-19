package io.github.golok56.manajemenuang.ui.base

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * @author Satria Adi Putra
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun start(activityClass: Class<*>){
        startActivity(Intent(this, activityClass))
        finish()
    }

    fun start(activityClass: Class<*>, extraName: String, extra: Long){
        val intent = Intent(this, activityClass)
        intent.putExtra(extraName, extra)
        startActivity(intent)
    }

}
