package io.github.golok56.manajemenuang.ui.transaction.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.ui.transaction.form.TransactionFormActivity;
import io.github.golok56.manajemenuang.utility.TransactionUtil;

public class TransactionListFragment extends Fragment {

    @BindView(R.id.rv_record_list_fragment)
    RecyclerView mRvTransactions;
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
                mRvTransactions.setVisibility(View.GONE);
                mTvNotFound.setVisibility(View.VISIBLE);
                return;
            }

            if (mAdapter == null) {
                mAdapter = new TransactionAdapter(TransactionUtil.preprocess(transactions));
                mRvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
                mRvTransactions.setAdapter(mAdapter);
                return;
            }

            mAdapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFabAddNewRecord.setOnClickListener(v ->
                getActivity().startActivity(TransactionFormActivity.getIntent(getActivity())));
    }

}