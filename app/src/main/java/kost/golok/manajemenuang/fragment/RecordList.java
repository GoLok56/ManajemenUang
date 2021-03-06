package kost.golok.manajemenuang.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kost.golok.adapter.RecordAdapter;
import kost.golok.database.DBQuery;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.manajemenuang.activity.TransactionDetail;
import kost.golok.manajemenuang.activity.TransactionForm;
import kost.golok.object.Transaction;
import kost.golok.utility.Formatter;
import kost.golok.utility.IntentUtil;

public class RecordList extends Fragment {

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Transaction transaksi = (Transaction) parent.getItemAtPosition(position);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            IntentUtil.start(activity, TransactionDetail.class, false, transaksi);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        createListView(view);
        createFAB(view);
    }

    private void createListView(View view){
        // Inflating the view with transaction_list of transactions
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(getAdapter());

        // Set on item click listener for the listview
        listView.setOnItemClickListener(onItemClick);
    }

    private void createFAB(View view){
        // Set onclick listener to start intent to TransactionForm Activity
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                IntentUtil.start(activity, TransactionForm.class, false, true);
            }
        });
    }

    /**
     * Parsing the data from SQLite Database
     *
     * @return {@link RecordAdapter} containing transaction_list of {@link Transaction}
     */
    private RecordAdapter getAdapter() {
        Cursor cursor = DBQuery.select(getActivity(), DBQuery.SELECT_ALL, null, null);
        // Iterating instance of cursor result of INSERT query and store it in an ArrayList
        ArrayList<Transaction> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(DBSchema.Transaksi._ID));
            double amount = cursor.getDouble(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_JUMLAH));
            String deskripsi = cursor.getString(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_DESKRIPSI));
            String tanggal = cursor.getString(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_TANGGAL));
            int tipeId = cursor.getInt(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_TIPE));
            String tipe = getTipe(tipeId);
            String formatted = Formatter.formatCurrency(amount);
            list.add(new Transaction(id, formatted, deskripsi, tanggal, tipe));
        }
        cursor.close();
        return new RecordAdapter(getActivity(), list);
    }

    private String getTipe(int id){
        switch (id){
            case DBSchema.Transaksi.TIPE_PENGELUARAN:
                return "Pengeluaran";
            case DBSchema.Transaksi.TIPE_PEMASUKAN:
                return "Pemasukan";
            default:
                return "Undefined";
        }
    }
}