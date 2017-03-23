package kost.golok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kost.golok.manajemenuang.R;
import kost.golok.object.Transaksi;

/**
 * Custom adapter to inflate the layout with transaction
 */
public class RecordAdapter extends ArrayAdapter<Transaksi> {

    /**
     * Create instance of {@link RecordAdapter}
     */
    public RecordAdapter(Context context, ArrayList<Transaksi> transaksi) {
        super(context, 0, transaksi);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Transaksi transaksi = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        // Lookup view for data population
        TextView tvDate = (TextView) convertView.findViewById(R.id.tanggal);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.nominal);
        TextView tvTipe = (TextView) convertView.findViewById(R.id.tipe);
        // Populate the data into the template view using the data object
        tvDate.setText(transaksi.getDate());
        tvAmount.setText(String.valueOf(transaksi.getAmount()));
        tvTipe.setText(transaksi.getType());
        // Return the completed view to render on screen
        return convertView;
    }
}
