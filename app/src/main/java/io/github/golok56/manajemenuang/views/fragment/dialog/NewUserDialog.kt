package io.github.golok56.manajemenuang.views.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.viewmodels.NewUserViewModel
import kotlinx.android.synthetic.main.dialog_new_user.*

/**
 * @author Satria Adi Putra
 */

class NewUserDialog : BaseDialog() {
    private var mViewModel: NewUserViewModel? = null

    private var mCommunicator: Communicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(NewUserViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Communicator) {
            mCommunicator = context
        } else {
            throw RuntimeException("Error! Activity must implement Communicator interface!")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_new_user, null)

        return AlertDialog.Builder(activity)
                .setView(view)
                .setPositiveButton(OK) { dialogInterface, i ->
                    mViewModel!!.createUser(
                            etUsername.text.toString(),
                            etWallet.text.toString()
                    )
                    mCommunicator!!.onPositive()
                }
                .setNegativeButton(CANCEL) { dialogInterface, i ->
                    showToast(mViewModel!!.validationText)
                    activity!!.finish()
                }
                .create()
    }

    interface Communicator {
        fun onPositive()
    }

    companion object {
        private val OK = "OK"
        private val CANCEL = "Cancel"
    }

}
