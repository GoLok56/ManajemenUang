package io.github.golok56.manajemenuang.ui.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.ui.base.BaseActivity
import io.github.golok56.manajemenuang.ui.main.MainActivity
import io.github.golok56.manajemenuang.utility.Formatter
import io.github.golok56.manajemenuang.viewmodels.TransactionDetailViewModel
import kotlinx.android.synthetic.main.activity_transaction_detail.*

class TransactionDetailActivity : BaseActivity() {

    private lateinit var mViewModel: TransactionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)

        mViewModel = ViewModelProviders.of(this).get(TransactionDetailViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getLongExtra(TRANSACTION_ID_EXTRA, -1)
        mViewModel.id = id

        mViewModel.transaction.observe(this, Observer { transaction ->
            tvAmount.text = Formatter.formatCurrency(transaction?.amount)
            tvDate.text = transaction?.date
            tvDesc.text = transaction?.description
            tvType.text = transaction?.typeText
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_transaction_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit_transaction_detail -> {
                start(TransactionFormActivity::class.java, TRANSACTION_ID_EXTRA, mViewModel.id)
                return true
            }
            R.id.menu_revert_transaction_detail -> {
                revertTransaction()
                return true
            }
            R.id.menu_delete_transaction_detail -> {
                deleteTransaction()
                return true
            }
        }
        return false
    }

    private fun deleteTransaction() {
        mViewModel.delete()
        showToast("Berhasil menghapus transaksi.")
        start(MainActivity::class.java)
    }

    private fun revertTransaction() {
        mViewModel.revert()
        mViewModel.delete()
        showToast("Berhasil melakukan rollback")
        start(MainActivity::class.java)
    }

    companion object {

        val TRANSACTION_ID_EXTRA = "transaction_id"

        fun getIntent(ctx: Context) = Intent(ctx, TransactionDetailActivity::class.java)
    }

}

