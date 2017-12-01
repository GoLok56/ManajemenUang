package io.github.golok56.manajemenuang.activity;

import android.content.Intent;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

import io.github.golok56.database.DBQuery;
import io.github.golok56.database.DBSchema;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.models.Transaction;
import io.github.golok56.utility.Component;
import io.github.golok56.utility.Formatter;
import io.github.golok56.utility.IntentUtil;
import io.github.golok56.utility.Preference;

public class TransactionForm extends AppCompatActivity {

    private Transaction mTransaksi;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_form);
        init();
    }

    @SuppressWarnings("ConstantConditions")
    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTransaksi = getIntent().getExtras().getParcelable(IntentUtil.CONTENT);
        fillForm(mTransaksi);
    }

    private void fillForm(Transaction transaksi){
        if (transaksi != null) {
            SparseArrayCompat<String> TEXTVIEW_VALUE = createTextViewMap(transaksi);
            initComponent(TEXTVIEW_VALUE, transaksi);
        }
    }

    private SparseArrayCompat<String> createTextViewMap(Transaction transaksi){
        String amount = Formatter.formatAmount(transaksi);
        SparseArrayCompat<String> data = new SparseArrayCompat<>();
        data.put(R.id.jmlNominal, amount);
        data.put(R.id.deskripsi, transaksi.getDescription());
        return data;
    }

    private void initComponent(SparseArrayCompat<String> textValue, Transaction transaksi){
        createTextView(textValue);
        setChecked(transaksi);
    }

    private void setChecked(Transaction transaksi){
        if (transaksi.getType().equals("Pemasukan"))
            Component.setChecked(this, R.id.pemasukan, true);
    }

    private void createTextView(SparseArrayCompat<String> textValue){
        for (int i = 0; i < textValue.size(); i++) {
            int id = textValue.keyAt(i);
            String value = textValue.get(id);
            Component.setText(this, id, value);
        }
    }

    /**
     * Saving the form to database
     */
    public void save(View view) {
        int jumlah, tipe;
        String desc, date;
        try {
            jumlah = Integer.parseInt(Component.getText(this, R.id.jmlNominal));
            tipe = Component.isChecked(this, R.id.pengeluaran) ?
                    DBSchema.Transaksi.TIPE_PENGELUARAN : DBSchema.Transaksi.TIPE_PEMASUKAN;
            desc = Component.getText(this, R.id.deskripsi);
            date = Formatter.formatDate(new Date());
            if (mTransaksi != null)
                DBQuery.update(getBaseContext(), jumlah, tipe, date, desc, mTransaksi.getID());
            else
                DBQuery.insert(getBaseContext(), jumlah, tipe, date, desc);

            Preference.updatePref(this, tipe, jumlah, mTransaksi);

            IntentUtil.start(this, TransactionRecord.class, true, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.validasi_form), Toast.LENGTH_SHORT).show();
        }
    }

}
