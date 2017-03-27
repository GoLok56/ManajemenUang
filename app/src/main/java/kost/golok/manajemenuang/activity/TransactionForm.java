package kost.golok.manajemenuang.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Date;

import kost.golok.database.DBHelper;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.object.Transaction;
import kost.golok.utility.Formatter;
import kost.golok.utility.Preference;

public class TransactionForm extends AppCompatActivity {

    private boolean mEditMode;
    private Transaction mTransaksi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditMode = getIntent().getExtras().getBoolean("edit");
        mTransaksi = getIntent().getExtras().getParcelable("content");
        init();
    }

    private void init() {
        if (mTransaksi != null) {
            EditText etNominal = (EditText) findViewById(R.id.jmlNominal);
            String amount = mTransaksi.getAmount().replace("Rp ", "").replace(".", "").replace(",00", "");
            etNominal.setText(amount);

            EditText etDesc = (EditText) findViewById(R.id.deskripsi);
            etDesc.setText(mTransaksi.getDescription());

            RadioButton rbPemasukan = (RadioButton) findViewById(R.id.pemasukan);

            if (mTransaksi.getType().equals("Pemasukan"))
                rbPemasukan.setChecked(true);
        }
    }

    /**
     * Updating or Inserting the database
     */
    private void update() {
        // Get the data repository in write mode
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = getContentValue();

        if (mEditMode) {
            // Update the row to new value
            db.update(DBSchema.Pengeluaran.TABLE_NAME, values, "_id=" + mTransaksi.getID(), null);
        } else {
            // Insert the new row, returning the primary key value of the new row
            db.insert(DBSchema.Pengeluaran.TABLE_NAME, null, values);
        }

        Intent intent = new Intent(TransactionForm.this, TransactionRecord.class);
        startActivity(intent);
    }

    /**
     * Return the ContentValue for update or insert to sqlite
     */
    private ContentValues getContentValue() {
        TextView _TextView = (TextView) findViewById(R.id.jmlNominal);
        int jumlah = Integer.parseInt(_TextView.getText().toString());

        RadioButton rbPengeluaran = (RadioButton) findViewById(R.id.pengeluaran);
        int tipe = rbPengeluaran.isChecked() ? DBSchema.Pengeluaran.TIPE_PENGELUARAN : DBSchema.Pengeluaran.TIPE_PEMASUKAN;

        _TextView = (TextView) findViewById(R.id.deskripsi);
        String desc = _TextView.getText().toString();

        String date = mEditMode ? mTransaksi.getDate() : Formatter.formatDate(new Date());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBSchema.Pengeluaran.COLUMN_TANGGAL, date);
        values.put(DBSchema.Pengeluaran.COLUMN_JUMLAH, jumlah);
        values.put(DBSchema.Pengeluaran.COLUMN_DESKRIPSI, desc);
        values.put(DBSchema.Pengeluaran.COLUMN_TIPE, tipe);

        // Update dompet value from preference
        SharedPreferences pref = getSharedPreferences(Preference.PREFERENCES_NAMES, Context.MODE_PRIVATE);
        int dompet = Integer.parseInt(pref.getString(Preference.DOMPET, null));

        if (mEditMode) {
            int oldAmount = Integer.parseInt(mTransaksi.getAmount().replace("Rp ", "").replace(".", "").replace(",00", ""));
            String oldType = mTransaksi.getType();
            switch (oldType) {
                case "Pengeluaran":
                    dompet += oldAmount;
                    break;
                case "Pemasukan":
                    dompet -= oldAmount;
                    break;
            }
        }

        switch (tipe) {
            case DBSchema.Pengeluaran.TIPE_PENGELUARAN:
                dompet -= jumlah;
                break;
            case DBSchema.Pengeluaran.TIPE_PEMASUKAN:
                dompet += jumlah;
                break;
        }
        String strDompet = "" + dompet;
        pref.edit().putString(Preference.DOMPET, strDompet).apply();

        return values;
    }

    /**
     * Saving the form to database
     */
    public void save(View view) {
        update();
    }

    /**
     * Reset the EditView text back to empty
     */
    public void reset(View view) {
        // Get the object of EditText
        EditText _Jumlah = (EditText) findViewById(R.id.jmlNominal);
        EditText _Deskripsi = (EditText) findViewById(R.id.deskripsi);
        // Clear the EditText text
        _Jumlah.setText("");
        _Deskripsi.setText("");
    }
}
