package kost.golok.manajemenuang.fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import kost.golok.database.DBQuery;
import kost.golok.database.DBSchema;
import kost.golok.manajemenuang.R;
import kost.golok.object.Laporan;
import kost.golok.utility.Formatter;
import kost.golok.utility.Preference;

import static android.content.Context.*;
import static kost.golok.manajemenuang.R.string.jumlah;

public class UserInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPref(view);
        createTransactionInfo(view);

        ArrayList<String> spinnerItem = new ArrayList<>(Laporan.getBulan(getActivity()));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerItem);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Spinner spinnerPengeluaran = (Spinner) view.findViewById(R.id.spinner_pengeluaran);
        spinnerPengeluaran.setAdapter(adapter);
        Spinner spinnerPemasukan = (Spinner) view.findViewById(R.id.spinner_pemasukan);
        spinnerPemasukan.setAdapter(adapter);

        final ArrayList<Laporan> laporan = new ArrayList<>();
        Cursor c = DBQuery.rawQuery(getActivity());
        while (c.moveToNext()) {
            int jumlah = c.getInt(c.getColumnIndex("amount"));
            String bulan = c.getString(c.getColumnIndex("bulan"));
            int tipe = c.getInt(c.getColumnIndex(DBSchema.Pengeluaran.COLUMN_TIPE));
            laporan.add(new Laporan(jumlah, tipe, bulan));
        }

        final TextView infoPemasukan = (TextView) view.findViewById(R.id.pemasukan_info);

        spinnerPemasukan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Laporan lapor : laporan) {
                    if (lapor.getWaktu().equals(item) && lapor.getTipe() == DBSchema.Pengeluaran.TIPE_PEMASUKAN)
                        infoPemasukan.setText(Formatter.formatCurrency((double) lapor.getJumlah()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        final TextView infoPengeluaran = (TextView) view.findViewById(R.id.pengeluaran_info);

        spinnerPengeluaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                for (Laporan lapor : laporan) {
                    if (lapor.getWaktu().equals(item) && lapor.getTipe() == DBSchema.Pengeluaran.TIPE_PENGELUARAN)
                        infoPengeluaran.setText(Formatter.formatCurrency((double) lapor.getJumlah()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    private void getPref(View view) {
        SharedPreferences pref = getActivity().getSharedPreferences(Preference.PREFERENCES_NAMES, MODE_PRIVATE);

        String nama = pref.getString(Preference.NAME, null);
        TextView tvNama = (TextView) view.findViewById(R.id.nama_info);
        tvNama.setText(nama);

        double dompet = Double.parseDouble(pref.getString(Preference.DOMPET, null));
        TextView tvDompet = (TextView) view.findViewById(R.id.dompet_info);
        tvDompet.setText(Formatter.formatCurrency(dompet));
    }

    private void createTransactionInfo(View view) {
        String where = DBSchema.Pengeluaran.COLUMN_TANGGAL + " LIKE ?";
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        String month = "%" + sdf.format(new Date()) + "%";
        String[] like = {month};
        Cursor cursor = DBQuery.select(getActivity(), DBQuery.SELECT_AMOUNT, where, like);
        double jmlPengeluaran = 0;
        double jmlPemasukan = 0;
        while (cursor.moveToNext()) {
            double jml = cursor.getDouble(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_JUMLAH));
            int type = cursor.getInt(cursor.getColumnIndex(DBSchema.Pengeluaran.COLUMN_TIPE));
            switch (type) {
                case DBSchema.Pengeluaran.TIPE_PENGELUARAN:
                    jmlPengeluaran += jml;
                    break;
                case DBSchema.Pengeluaran.TIPE_PEMASUKAN:
                    jmlPemasukan += jml;
                    break;
            }
        }
        TextView infoPengeluaran = (TextView) view.findViewById(R.id.pengeluaran_info);
        infoPengeluaran.setText(Formatter.formatCurrency(jmlPengeluaran));

        TextView infoPemasukan = (TextView) view.findViewById(R.id.pemasukan_info);
        infoPemasukan.setText(Formatter.formatCurrency(jmlPemasukan));
    }

}
