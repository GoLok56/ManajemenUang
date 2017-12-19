package io.github.golok56.manajemenuang.ui.base

import android.content.Intent
import android.support.v4.app.Fragment

/**
 * @author Satria Adi Putra
 */
open class BaseFragment : Fragment() {

    fun start(activityClass: Class<*>, extraName: String, extra: Long){
        val i = Intent(activity!!, activityClass)
        i.putExtra(extraName, extra)
        startActivity(i)
    }

    fun start(activityClass: Class<*>){
        startActivity(Intent(activity!!, activityClass))
    }

}