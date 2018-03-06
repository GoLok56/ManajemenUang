package io.github.golok56.manajemenuang.views.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.utility.Formatter
import io.github.golok56.manajemenuang.viewmodels.UserInfoViewModel
import kotlinx.android.synthetic.main.fragment_user_info.view.*

class UserInfoFragment : Fragment() {
    private lateinit var mViewModel: UserInfoViewModel

    private var mAdapter: ArrayAdapter<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)

        mViewModel = ViewModelProviders.of(this).get(UserInfoViewModel::class.java)

        view.tvUserName.text = mViewModel.userName
        view.tvWallet.text = Formatter.formatCurrency(mViewModel.wallet)

        mViewModel.months.observe(this, Observer { months ->
            if (mAdapter == null) {
                if (months != null) {
                    mAdapter = ArrayAdapter(
                            activity!!,
                            android.R.layout.simple_spinner_item,
                            months
                    )

                    mAdapter!!.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                    view.spExpense.adapter = mAdapter
                    view.spIncome.adapter = mAdapter
                }
                return@Observer
            }

            mAdapter!!.notifyDataSetChanged()
        })

        view.spExpense.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val month = parent.getItemAtPosition(position).toString()
                view.tvExpense.text = mViewModel.getAmountOn(month, Transaction.EXPENSE_TYPE)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        view.spIncome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val month = parent.getItemAtPosition(position).toString()
                view.tvIncome.text = mViewModel.getAmountOn(month, Transaction.INCOME_TYPE)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

        return view
    }

}
