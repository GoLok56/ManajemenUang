package io.github.golok56.manajemenuang.views.fragment;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.viewmodels.TransactionListViewModel;
import io.github.golok56.manajemenuang.views.activity.TransactionDetailActivity;
import io.github.golok56.manajemenuang.views.activity.TransactionFormActivity;
import io.github.golok56.manajemenuang.views.adapter.TransactionAdapter;

public class TransactionListFragment extends Fragment {

    @BindView(R.id.lv_record_list_fragment)
    ListView mLvTransactions;
    @BindView(R.id.fab_add_new_transaction_fragment_transaction_list)
    FloatingActionButton mFabAddNewRecord;
    @BindView(R.id.tv_transaction_not_found_fragment_transaction_list)
    TextView mTvNotFound;

    private TransactionAdapter mAdapter;

    private TransactionListViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this).get(TransactionListViewModel.class);

        mViewModel.getTransactions().observe(this, transactions -> {
            if (transactions == null || transactions.size() == 0) {
                mLvTransactions.setVisibility(View.GONE);
                mTvNotFound.setVisibility(View.VISIBLE);
                return;
            }

            if (mAdapter == null) {
                mAdapter = new TransactionAdapter(getActivity(), transactions);
                mLvTransactions.setAdapter(mAdapter);
                return;
            }

            mAdapter.notifyDataSetChanged();
        });

        mLvTransactions.setOnItemClickListener((parent, view1, position, id) -> {
            Activity activity = getActivity();
            Transaction transaction = (Transaction) parent.getItemAtPosition(position);
            Intent intent = TransactionDetailActivity.getIntent(activity);
            intent.putExtra(TransactionDetailActivity.TRANSACTION_ID_EXTRA, transaction.getId());
            activity.startActivity(intent);
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFabAddNewRecord.setOnClickListener(v -> {
            Activity activity = getActivity();
            activity.startActivity(TransactionFormActivity.getIntent(activity));
        });
    }

}