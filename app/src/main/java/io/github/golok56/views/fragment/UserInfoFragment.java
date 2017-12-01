package io.github.golok56.views.fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.github.golok56.database.DBQuery;
import io.github.golok56.database.DBSchema;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.models.Report;
import io.github.golok56.utility.Component;
import io.github.golok56.utility.Formatter;
import io.github.golok56.utility.Preference;

import static android.content.Context.*;

public class UserInfoFragment extends Fragment {

    private static final String FORMAT_BULAN = "MMMM yyyy";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Report> laporan = createLaporan();
        Listener listener = new Listener(laporan);
        SparseArrayCompat<AdapterView.OnItemSelectedListener> spinnerMap = createSpinnerMap(listener);

        super.onViewCreated(view, savedInstanceState);
        init(view, spinnerMap);
    }

    private void init(View view, SparseArrayCompat map) {
        getPref();
        createTransactionInfo();
        createSpinner(view, map);
    }

    private SparseArrayCompat<AdapterView.OnItemSelectedListener> createSpinnerMap(Listener listener) {
        SparseArrayCompat<AdapterView.OnItemSelectedListener> data = new SparseArrayCompat<>();
        data.put(R.id.spinner_pengeluaran, listener.pengeluaranSelected);
        data.put(R.id.spinner_pemasukan, listener.pemasukanSelected);
        return data;
    }

    private void createSpinner(View view, SparseArrayCompat listeners) {
        ArrayAdapter adapter = createAdapter();
        for (int i = 0; i < listeners.size(); i++) {
            int id = listeners.keyAt(i);
            AdapterView.OnItemSelectedListener listener = (AdapterView.OnItemSelectedListener) listeners.get(id);
            Component.setSpinner(view, id, adapter, listener);
        }
    }

    private ArrayAdapter<String> createAdapter() {
        ArrayList<String> spinnerItem = new ArrayList<>(Report.getBulan(getActivity()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItem);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }

    private ArrayList<Report> createLaporan() {
        final ArrayList<Report> laporan = new ArrayList<>();
        Cursor c = DBQuery.selectLaporan(getActivity());
        while (c.moveToNext()) {
            int jumlah = c.getInt(c.getColumnIndex("amount"));
            String bulan = c.getString(c.getColumnIndex("bulan"));
            int tipe = c.getInt(c.getColumnIndex(DBSchema.Transaksi.COLUMN_TIPE));
            laporan.add(new Report(jumlah, tipe, bulan));
        }
        c.close();
        return laporan;
    }

    private void getPref() {
        SharedPreferences pref = getActivity().getSharedPreferences(Preference.PREFERENCES_NAMES, MODE_PRIVATE);
        String nama = pref.getString(Preference.NAME, null);
        double dompet = Double.parseDouble(pref.getString(Preference.DOMPET, null));

        String[][] tv = {
                {"" + R.id.nama_info, nama},
                {"" + R.id.dompet_info, Formatter.formatCurrency(dompet)}
        };
        createTextView(tv);
    }

    private void createTransactionInfo() {
        double[] jumlah = hitungJumlah();
        String[][] tv = {
                {"" + R.id.pengeluaran_info, Formatter.formatCurrency(jumlah[0])},
                {"" + R.id.pemasukan_info, Formatter.formatCurrency(jumlah[1])}
        };
        createTextView(tv);
    }

    private double[] hitungJumlah(){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_BULAN);
        String where = DBSchema.Transaksi.COLUMN_TANGGAL + " LIKE ?";
        String month = "%" + sdf.format(new Date()) + "%";
        String[] like = {month};
        Cursor cursor = DBQuery.select(getActivity(), DBQuery.SELECT_AMOUNT, where, like);
        double jmlPengeluaran = 0;
        double jmlPemasukan = 0;
        while (cursor.moveToNext()) {
            double jml = cursor.getDouble(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_JUMLAH));
            int type = cursor.getInt(cursor.getColumnIndex(DBSchema.Transaksi.COLUMN_TIPE));
            switch (type) {
                case DBSchema.Transaksi.TIPE_PENGELUARAN:
                    jmlPengeluaran += jml;
                    break;
                case DBSchema.Transaksi.TIPE_PEMASUKAN:
                    jmlPemasukan += jml;
                    break;
            }
        }
        cursor.close();
        return new double[]{jmlPengeluaran, jmlPemasukan};
    }

    private void createTextView(String[][] tv){
        int indeksId = 0;
        int indeksText = 1;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        for (String[] textview : tv) {
            int id = Integer.parseInt(textview[indeksId]);
            String text = textview[indeksText];
            Component.setText(activity, id, text);
        }
    }

    private class Listener {

        private ArrayList<Report> laporan;
        private static final int TEXTVIEW_PENGELUARAN = R.id.pengeluaran_info;
        private static final int TEXTVIEW_PEMASUKAN = R.id.pemasukan_info;

        Listener(ArrayList<Report> laporan) {
            this.laporan = laporan;
        }

        AdapterView.OnItemSelectedListener pengeluaranSelected = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Report lapor : laporan) {
                    if (lapor.getWaktu().equals(item) && lapor.getTipe() == DBSchema.Transaksi.TIPE_PENGELUARAN) {
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        String strJumlah = Formatter.formatCurrency((double) lapor.getJumlah());
                        Component.setText(activity, TEXTVIEW_PENGELUARAN, strJumlah);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        AdapterView.OnItemSelectedListener pemasukanSelected = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Report lapor : laporan) {
                    if (lapor.getWaktu().equals(item) && lapor.getTipe() == DBSchema.Transaksi.TIPE_PEMASUKAN) {
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        String strJumlah = Formatter.formatCurrency((double) lapor.getJumlah());
                        Component.setText(activity, TEXTVIEW_PEMASUKAN, strJumlah);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

    }

}
