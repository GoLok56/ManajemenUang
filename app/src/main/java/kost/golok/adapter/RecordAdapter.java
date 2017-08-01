package kost.golok.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import kost.golok.manajemenuang.R;
import kost.golok.object.Transaction;
import kost.golok.utility.Component;

/**
 * Custom adapter untuk mengatur bagaimana {@link android.widget.ListView} transaksi terisi
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
public class RecordAdapter extends ArrayAdapter<Transaction> {

    /**
     * Constructor untuk RecordAdapter yang memanggil Parent Constructor {@link ArrayAdapter}
     *
     * @param context Context dari {@link View} yang aktif
     * @param transaksi {@link ArrayList} yang berisi kumpulan {@link Transaction}
     */
    public RecordAdapter(Context context, ArrayList<Transaction> transaksi) {
        super(context, 0, transaksi);
    }

    /**
     * @param pos Posisi item
     * @param view {@link View} yang akan diisi
     * @param parent Parent yang akan menjadi root view
     * @return {@link View} yang telah diisi
     */
    @NonNull
    @Override
    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        // Get the data item for this pos
        Transaction transaksi = getItem(pos);

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_row, parent, false);
        }

        if (transaksi != null) {
            SparseArrayCompat<String> TEXTVIEW_VALUE = createTextViewMap(transaksi);
            createTextView(view, TEXTVIEW_VALUE);
        }

        return view;
    }

    /**
     * @param transaksi Data transaksi pada posisi "pos"
     * @return Array yang menyimpan pasangan key > value, id dan text yang akan diisi
     */
    private SparseArrayCompat<String> createTextViewMap(Transaction transaksi){
        SparseArrayCompat<String> data = new SparseArrayCompat<>();
        data.put(R.id.tanggal, transaksi.getDate());
        data.put(R.id.nominal, transaksi.getAmount());
        data.put(R.id.tipe, transaksi.getType());
        return data;
    }

    /**
     * @param view {@link View} yang sedang aktif
     * @param data Array yang menyimpan pasangan key > value, id dan text yang akan diisi
     */
    private  void createTextView(View view, SparseArrayCompat<String> data){
        for (int i = 0; i < data.size(); i++) {
            int id = data.keyAt(i);
            String value = data.get(id);
            Component.setText(view, id, value);
        }
    }
}
