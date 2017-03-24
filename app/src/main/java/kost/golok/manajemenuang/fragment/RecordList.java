package kost.golok.manajemenuang.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import kost.golok.adapter.RecordAdapter;
import kost.golok.database.DBHelper;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.manajemenuang.activity.TransactionForm;
import kost.golok.object.Transaksi;
import kost.golok.object.utility.Formatter;

public class RecordList extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflating the view with transaction_list of transactions
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(getAdapter());

        // Set onclick listener to start intent to TransactionForm Activity
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransactionForm.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Parsing the data from SQLite Database a
     *
     * @return {@link RecordAdapter} containing transaction_list of {@link Transaksi}
     */
    private RecordAdapter getAdapter() {
        // Creating SQLiteDatabase instance
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Declare the selection array to store which column to be selected
        String[] selection = {
                DBSchema.Pengeluaran.COLUMN_JUMLAH,
                DBSchema.Pengeluaran.COLUMN_DESKRIPSI,
                DBSchema.Pengeluaran.COLUMN_TANGGAL,
                DBSchema.Pengeluaran.COLUMN_TIPE
        };
        // Executing INSERT SQLite query
        Cursor cursor = db.query(
                DBSchema.Pengeluaran.TABLE_NAME,
                selection,
                null,
                null,
                null,
                null,
                null
        );
        // Iterating instance of cursor result of INSERT query and store it in an ArrayList
        ArrayList<Transaksi> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            double amount = cursor.getDouble(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_JUMLAH));
            int tipeId = cursor.getInt(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_TIPE));
            String deskripsi = cursor.getString(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_DESKRIPSI));
            String tanggal = cursor.getString(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_TANGGAL));
            String tipe;
            switch (tipeId) {
                case DBSchema.Pengeluaran.TIPE_PENGELUARAN:
                    tipe = "Pengeluaran";
                    break;
                case DBSchema.Pengeluaran.TIPE_PEMASUKAN:
                    tipe = "Pemasukan";
                    break;
                default:
                    tipe = "Undefined";
            }
            String formatted = Formatter.format(amount);
            list.add(new Transaksi(formatted, deskripsi, tanggal, tipe));
        }
        cursor.close();
        return new RecordAdapter(getActivity(), list);
    }
}