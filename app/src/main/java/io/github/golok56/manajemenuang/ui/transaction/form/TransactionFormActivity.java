package io.github.golok56.manajemenuang.ui.transaction.form;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.ui.base.BaseActivity;
import io.github.golok56.manajemenuang.ui.transaction.detail.TransactionDetailActivity;
import io.github.golok56.manajemenuang.utility.FormatUtil;
import io.github.golok56.manajemenuang.ui.main.MainActivity;

public class TransactionFormActivity extends BaseActivity {

    @BindView(R.id.et_amount_activity_transaction_form)
    EditText mEtAmount;
    @BindView(R.id.et_desc_activity_transaction_form)
    EditText mEtDescription;
    @BindView(R.id.rb_income_activity_transaction_form)
    RadioButton mCbIncome;
    private TransactionFormViewModel mViewModel;
    private boolean isEditMode = false;

    public static Intent getIntent(Context context) {
        return new Intent(context, TransactionFormActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_form);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(TransactionFormViewModel.class);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        long id = getIntent().getLongExtra(TransactionDetailActivity.TRANSACTION_ID_EXTRA, -1);
        if (id != -1) {
            mViewModel.setId(id);
            mViewModel.getTransaction().observe(this, transaction -> {
                mEtAmount.setText(mViewModel.getAmount());
                mEtDescription.setText(mViewModel.getDesc());
                if (mViewModel.isIncome()) {
                    mCbIncome.setChecked(true);
                }
            });

            isEditMode = true;
        }

    }

    @OnClick(R.id.btn_save_activity_transaction_form)
    public void save(View v) {
        double amount;
        try {
            amount = Double.parseDouble(mEtAmount.getText().toString());
        } catch (Exception ex) {
            mEtAmount.setError(getString(R.string.validasi_form));
            return;
        }

        int type = mCbIncome.isChecked() ? Transaction.INCOME_TYPE : Transaction.EXPENSE_TYPE;
        String desc = mEtDescription.getText().toString();
        String date = FormatUtil.formatDate("dd MMMM yyyy");

        mViewModel.save(amount, type, desc, date);
        if (isEditMode) {
            showToast(getString(R.string.success_edit_transaction));
        } else {
            showToast(getString(R.string.success_create_transaction));
        }

        startActivity(MainActivity.getIntent(this));
    }

}
