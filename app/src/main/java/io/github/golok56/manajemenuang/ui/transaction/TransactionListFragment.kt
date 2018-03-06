package io.github.golok56.manajemenuang.ui.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.ui.base.BaseFragment
import io.github.golok56.manajemenuang.viewmodels.TransactionListViewModel
import kotlinx.android.synthetic.main.fragment_transaction_list.view.*

class TransactionListFragment : BaseFragment() {

    private lateinit var mAdapter: TransactionAdapter
    private lateinit var mViewModel: TransactionListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_list, container, false)

        mAdapter = TransactionAdapter(activity!!, ArrayList())

        mViewModel.transactions.observe(this, Observer { transactions ->
            if (transactions == null || transactions.size == 0) {
                view.lvRecord.visibility = View.GONE
                view.tvNotFound.visibility = View.VISIBLE
            } else if (mAdapter.isEmpty) {
                mAdapter.addAll(transactions)
                view.lvRecord.adapter = mAdapter
            } else {
                mAdapter.notifyDataSetChanged()
            }
        })

        view.lvRecord.setOnItemClickListener { parent, _, position, _ ->
            val transaction = parent.getItemAtPosition(position) as Transaction
            start(
                    TransactionDetailActivity::class.java,
                    TransactionDetailActivity.TRANSACTION_ID_EXTRA,
                    transaction.id
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.fabAddNewTransaction.setOnClickListener { _ -> start(TransactionFormActivity::class.java) }
    }

}