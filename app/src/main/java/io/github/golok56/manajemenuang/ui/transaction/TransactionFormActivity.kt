package io.github.golok56.manajemenuang.ui.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.ui.base.BaseActivity
import io.github.golok56.manajemenuang.ui.main.MainActivity
import io.github.golok56.manajemenuang.utility.Formatter
import io.github.golok56.manajemenuang.viewmodels.TransactionFormViewModel
import kotlinx.android.synthetic.main.activity_transaction_form.*

class TransactionFormActivity : BaseActivity() {

    private lateinit var mViewModel: TransactionFormViewModel
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_form)

        mViewModel = ViewModelProviders.of(this).get(TransactionFormViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(TransactionDetailActivity.TRANSACTION_ID_EXTRA, -1)
        if (id != -1L) {
            mViewModel.setId(id)
            mViewModel.transaction.observe(this, Observer { transaction ->
                etAmount.setText(Formatter.formatCurrency(transaction?.amount))
                etDesc.setText(transaction?.description)
                if (mViewModel.isIncome) {
                    rbIncome.isChecked = true
                }
            })

            isEditMode = true
        }

        btnSave.setOnClickListener{_ -> save()}
    }

    fun save() {
        val amount: Double
        try {
            amount = java.lang.Double.parseDouble(etAmount.text.toString())
        } catch (ex: Exception) {
            etAmount.error = getString(R.string.validasi_form)
            return
        }

        val type = if (rbIncome.isChecked) Transaction.INCOME_TYPE else Transaction.EXPENSE_TYPE
        val desc = etDesc.text.toString()
        val date = Formatter.formatDate("dd MMMM yyyy")

        mViewModel.save(amount, type, desc, date)
        if (isEditMode) {
            showToast(getString(R.string.success_edit_transaction))
        } else {
            showToast(getString(R.string.success_create_transaction))
        }

        start(MainActivity::class.java)
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, TransactionFormActivity::class.java)
        }
    }

}
