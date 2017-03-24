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

import java.text.SimpleDateFormat;
import java.util.Date;

import kost.golok.database.DBHelper;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.utility.Preference;

public class TransactionForm extends AppCompatActivity {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pengeluaran);
    }

    /**
     * Saving the form to database
     */
    public void save(View view) {
        // Get the data repository in write mode
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TextView _TextView = (TextView) findViewById(R.id.jmlPengeluaran);
        int jumlah = Integer.parseInt(_TextView.getText().toString());

        RadioButton rbPengeluaran = (RadioButton) findViewById(R.id.pengeluaran);

        int tipe = rbPengeluaran.isChecked() ? DBSchema.Pengeluaran.TIPE_PENGELUARAN : DBSchema.Pengeluaran.TIPE_PEMASUKAN;

        _TextView = (TextView) findViewById(R.id.deskripsi);
        String desc = _TextView.getText().toString();

        String date = DATE_FORMAT.format(new Date());

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBSchema.Pengeluaran.COLUMN_TANGGAL, date);
        values.put(DBSchema.Pengeluaran.COLUMN_JUMLAH, jumlah);
        values.put(DBSchema.Pengeluaran.COLUMN_DESKRIPSI, desc);
        values.put(DBSchema.Pengeluaran.COLUMN_TIPE, tipe);

        // Insert the new row, returning the primary key value of the new row
        long row = db.insert(DBSchema.Pengeluaran.TABLE_NAME, null, values);

        // Update dompet value from preference
        SharedPreferences pref = getSharedPreferences(Preference.PREFERENCES_NAMES, Context.MODE_PRIVATE);
        int dompet = Integer.parseInt(pref.getString(Preference.DOMPET, null));
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

        Intent intent = new Intent(TransactionForm.this, TransactionRecord.class);
        startActivity(intent);
    }

    /**
     * Reset the EditView text back to empty
     */
    public void reset(View view) {
        // Get the object of EditText
        EditText _Jumlah = (EditText) findViewById(R.id.jmlPengeluaran);
        EditText _Deskripsi = (EditText) findViewById(R.id.deskripsi);
        // Clear the EditText text
        _Jumlah.setText("");
        _Deskripsi.setText("");
    }
}
