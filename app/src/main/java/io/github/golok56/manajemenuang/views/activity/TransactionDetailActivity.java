package io.github.golok56.manajemenuang.views.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.ui.base.BaseActivity;
import io.github.golok56.manajemenuang.ui.main.MainActivity;
import io.github.golok56.manajemenuang.utility.Formatter;
import io.github.golok56.manajemenuang.viewmodels.TransactionDetailViewModel;

public class TransactionDetailActivity extends BaseActivity {

    public static final String TRANSACTION_ID_EXTRA = "transaction_id";

    @BindView(R.id.tv_type_fragment_transaction_detail)
    TextView mTvType;
    @BindView(R.id.tv_amount_activity_transaction_detail)
    TextView mTvAmount;
    @BindView(R.id.tv_date_activity_transaction_detail)
    TextView mTvDate;
    @BindView(R.id.tv_desc_activity_transaction_detail)
    TextView mTvDescription;

    private TransactionDetailViewModel mViewModel;

    public static Intent getIntent(Context ctx) {
        return new Intent(ctx, TransactionDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this).get(TransactionDetailViewModel.class);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        long id = getIntent().getLongExtra(TRANSACTION_ID_EXTRA, -1);
        mViewModel.setId(id);

        mViewModel.getTransaction().observe(this, transaction -> {
            if (transaction != null) {
                mTvAmount.setText(Formatter.formatCurrency(transaction.getAmount()));
                mTvDate.setText(transaction.getDate());
                mTvDescription.setText(transaction.getDescription());
                mTvType.setText(transaction.getTypeText());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_transaction_detail:
                editTransaction();
                return true;
            case R.id.menu_revert_transaction_detail:
                revertTransaction();
                return true;
            case R.id.menu_delete_transaction_detail:
                deleteTransaction();
                return true;
        }

        return false;
    }

    private void editTransaction() {
        Intent intent = TransactionFormActivity.getIntent(this);
        intent.putExtra(TRANSACTION_ID_EXTRA, mViewModel.getId());
        startActivity(intent);
        finish();
    }

    private void deleteTransaction() {
        mViewModel.delete();
        showToast("Berhasil menghapus transaksi.");
        showMainActivity();
    }

    private void revertTransaction() {
        mViewModel.revert();
        mViewModel.delete();
        showToast("Berhasil melakukan rollback");
        showMainActivity();
    }

    private void showMainActivity() {
        startActivity(MainActivity.Companion.getIntent(this));
        finish();
    }

}

