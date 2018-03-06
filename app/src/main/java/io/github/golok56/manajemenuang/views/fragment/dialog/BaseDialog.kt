package io.github.golok56.manajemenuang.views.fragment.dialog

import android.support.v4.app.DialogFragment
import android.widget.Toast

/**
 * @author Satria Adi Putra
 */
open class BaseDialog : DialogFragment() {

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

}
