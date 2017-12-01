package io.github.golok56.views.activity;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.github.golok56.database.DBQuery;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.models.Transaction;
import io.github.golok56.utility.Component;
import io.github.golok56.utility.Preference;
import io.github.golok56.utility.IntentUtil;

public class TransactionDetail extends AppCompatActivity {

    private Transaction mTransaksi;

    /**
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);
        createUserInfo();
    }

    /**
     *
     */
    @SuppressWarnings("ConstantConditions")
    private void createUserInfo() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTransaksi = getIntent().getExtras().getParcelable(IntentUtil.CONTENT);

        SparseArrayCompat<String> TEXTVIEW_VALUE = createTextViewMap(mTransaksi);
        createTextView(TEXTVIEW_VALUE);

        SparseArrayCompat<View.OnClickListener> BUTTON_LISTENER = createButtonListenerMap(new Listener());
        createButtonListener(BUTTON_LISTENER);
    }

    /**
     *
     */
    private void delete() {
        DBQuery.delete(getBaseContext(), "" + mTransaksi.getID());
        IntentUtil.start(this, TransactionRecord.class, true);
    }

    /**
     * @param transaksi objek transaksi
     * @return Array K -> V, berisi id dan nilai
     */
    private SparseArrayCompat<String> createTextViewMap(Transaction transaksi){
        SparseArrayCompat<String> map = new SparseArrayCompat<>();
        map.put(R.id.judul_detail, transaksi.getType());
        map.put(R.id.jumlah_detail, transaksi.getAmount());
        map.put(R.id.tanggal_detail, transaksi.getDate());
        map.put(R.id.ket_detail, transaksi.getDescription());
        return map;
    }

    /**
     * @param listener objek {@link Listener}
     * @return Array K -> V, berisi id dan nilai
     */
    private SparseArrayCompat<View.OnClickListener> createButtonListenerMap(Listener listener){
        SparseArrayCompat<View.OnClickListener> map = new SparseArrayCompat<>();
        map.put(R.id.btn_edit, listener.edit);
        map.put(R.id.btn_del, listener.del);
        map.put(R.id.btn_rev, listener.revert);
        return map;
    }

    /**
     * @param data array K -> V, yang berisi id dan nilai
     */
    private void createTextView(SparseArrayCompat<String> data){
        for (int i = 0; i < data.size(); i++) {
            int id = data.keyAt(i);
            String value = data.get(id);
            Component.setText(this, id, value);
        }
    }

    /**
     * @param data array K -> V, yang berisi id dan nilai
     */
    private void createButtonListener(SparseArrayCompat<View.OnClickListener> data){
        for (int i = 0; i < data.size(); i++) {
            int id = data.keyAt(i);
            View.OnClickListener listener = data.get(id);
            Component.setOnClickListener(this, id, listener);
        }
    }

    /**
     *
     */
    private class Listener {

        /**
         *
         */
        View.OnClickListener edit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getParent();
                IntentUtil.start(activity, TransactionForm.class, true, true, mTransaksi);
            }
        };

        /**
         *
         */
        View.OnClickListener del = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        };

        /**
         *
         */
        View.OnClickListener revert = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.revertUpdate(TransactionDetail.this, mTransaksi);
                delete();
            }
        };
    }

}

