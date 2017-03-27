package kost.golok.manajemenuang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kost.golok.database.DBHelper;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.object.Transaction;
import kost.golok.utility.Preference;

public class TransactionDetail extends AppCompatActivity {

    private Transaction mTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);
        init();
    }

    private void init() {
        createUserInfo();
    }

    private void createUserInfo() {
        TextView judul = (TextView) findViewById(R.id.judul_detail);
        TextView jumlah = (TextView) findViewById(R.id.jumlah_detail);
        TextView tanggal = (TextView) findViewById(R.id.tanggal_detail);
        TextView ket = (TextView) findViewById(R.id.ket_detail);

        mTransaksi = getIntent().getExtras().getParcelable("content");

        judul.setText(mTransaksi.getType());
        jumlah.setText(mTransaksi.getAmount());
        tanggal.setText(mTransaksi.getDate());
        ket.setText(mTransaksi.getDescription());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Listener listener = new Listener();

        Button btnEdit = (Button) findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(listener.edit);
        Button btnDel = (Button) findViewById(R.id.btn_del);
        btnDel.setOnClickListener(listener.del);
        Button btnRev = (Button) findViewById(R.id.btn_rev);
        btnRev.setOnClickListener(listener.revert);
    }

    private void delete() {
        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DBSchema.Pengeluaran.TABLE_NAME, "_id=" + mTransaksi.getID(), null);

        Intent intent = new Intent(TransactionDetail.this, TransactionRecord.class);
        startActivity(intent);
    }

    private class Listener {

        View.OnClickListener edit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionDetail.this, TransactionForm.class);
                intent.putExtra("content", mTransaksi);
                intent.putExtra("edit", true);
                startActivity(intent);
            }
        };

        View.OnClickListener del = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        };

        View.OnClickListener revert = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update dompet value from preference
                SharedPreferences pref = getSharedPreferences(Preference.PREFERENCES_NAMES, Context.MODE_PRIVATE);
                int dompet = Integer.parseInt(pref.getString(Preference.DOMPET, null));

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
                String strDompet = "" + dompet;
                pref.edit().putString(Preference.DOMPET, strDompet).apply();

                delete();
            }
        };
    }

}

