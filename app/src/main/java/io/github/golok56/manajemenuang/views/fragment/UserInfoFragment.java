package io.github.golok56.manajemenuang.views.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.Formatter;
import io.github.golok56.manajemenuang.viewmodels.UserInfoViewModel;

public class UserInfoFragment extends Fragment {

    @BindView(R.id.sp_expense_fragment_user_info)
    Spinner mSpExpenses;
    @BindView(R.id.sp_income_fragment_user_info)
    Spinner mSpIncome;
    @BindView(R.id.tv_expense_amount_fragment_user_info)
    TextView mTvExpense;
    @BindView(R.id.tv_income_amount_fragment_user_info)
    TextView mTvIncome;
    @BindView(R.id.tv_user_name_fragment_user_info)
    TextView mTvUserName;
    @BindView(R.id.tv_wallet_fragment_user_info)
    TextView mTvWallet;

    private UserInfoViewModel mViewModel;

    private ArrayAdapter<String> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);

        mTvUserName.setText(mViewModel.getUserName());
        mTvWallet.setText(Formatter.formatCurrency(mViewModel.getWallet()));

        mViewModel.getMonths().observe(this, months -> {
            if (mAdapter == null) {
                if (months != null) {
                    mAdapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_spinner_item,
                            months
                    );

                    mAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    mSpExpenses.setAdapter(mAdapter);
                    mSpIncome.setAdapter(mAdapter);
                }
                return;
            }

            mAdapter.notifyDataSetChanged();
        });

        mSpExpenses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = parent.getItemAtPosition(position).toString();
                mTvExpense.setText(mViewModel.getAmountOn(month, Transaction.EXPENSE_TYPE));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mSpIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = parent.getItemAtPosition(position).toString();
                mTvIncome.setText(mViewModel.getAmountOn(month, Transaction.INCOME_TYPE));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }

}
