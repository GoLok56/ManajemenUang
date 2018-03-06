package io.github.golok56.manajemenuang.ui.transaction.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.FormatUtil;

/**
 * Custom adapter untuk mengatur bagaimana {@link android.widget.ListView} transaksi terisi
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {

    @BindView(R.id.tv_date_transaction_list)
    TextView mTvDateText;
    @BindView(R.id.tv_nominal_transaction_list)
    TextView mTvNominalText;
    @BindView(R.id.tv_type_transaction_list)
    TextView mTvTypeText;

    /**
     * Constructor untuk RecordAdapter yang memanggil Parent Constructor {@link ArrayAdapter}
     *
     * @param context      Context dari {@link View} yang aktif
     * @param transactions {@link ArrayList} yang berisi kumpulan {@link Transaction}
     */
    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
    }

    /**
     * @param pos    Posisi item
     * @param view   {@link View} yang akan diisi
     * @param parent Parent yang akan menjadi root view
     * @return {@link View} yang telah diisi
     */
    @NonNull
    @Override
    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        // Get the data item for this pos
        Transaction transaction = getItem(pos);

        if (view == null) {
            view = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_transaction, parent, false);
        }

        ButterKnife.bind(this, view);

        if (transaction != null) {
            mTvDateText.setText(transaction.getDate());
            mTvNominalText.setText(FormatUtil.formatCurrency(transaction.getAmount()));
            mTvTypeText.setText(transaction.getTypeText());
        }

        return view;
    }

}
