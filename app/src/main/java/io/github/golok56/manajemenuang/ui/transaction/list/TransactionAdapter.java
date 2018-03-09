package io.github.golok56.manajemenuang.ui.transaction.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.FormatUtil;

/**
 * @author Satria Adi Putra
 */
public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final byte MONTH_VIEW_TYPE = 0;
    private static final byte TRANSACTION_VIEW_TYPE = 1;

    private List<Object> mTransactions;

    TransactionAdapter(List<Object> transactions) {
        mTransactions = transactions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TRANSACTION_VIEW_TYPE:
                return new TransactionViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_transaction, parent, false));
            case MONTH_VIEW_TYPE:
                return new MonthViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_month, parent, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mTransactions.get(position) instanceof String ?
                MONTH_VIEW_TYPE : TRANSACTION_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MonthViewHolder) {
            MonthViewHolder monthHolder = (MonthViewHolder) holder;
            monthHolder.setMonth(mTransactions.get(position).toString());
        } else {
            TransactionViewHolder transactionHolder = (TransactionViewHolder) holder;
            Transaction transaction = (Transaction) mTransactions.get(position);

            transactionHolder.setAmount(FormatUtil.formatCurrency(transaction.getAmount()));
            transactionHolder.setDate(transaction.getFullDate());
            transactionHolder.setType(transaction.getStringType());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_type_item_transaction_list)
        TextView mTvTransactionType;
        @BindView(R.id.tv_date_item_transaction_list)
        TextView mTvDate;
        @BindView(R.id.tv_nominal_item_transaction_list)
        TextView mTvAmount;

        TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setType(String type) {
            mTvTransactionType.setText(type);
        }

        void setAmount(String amount) {
            mTvAmount.setText(amount);
        }

        void setDate(String date) {
            mTvDate.setText(date);
        }
    }

    class MonthViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_month_item_month)
        TextView mTvMonth;

        MonthViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setMonth(String month) {
            mTvMonth.setText(month);
        }
    }

}
