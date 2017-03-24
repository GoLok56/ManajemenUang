package kost.golok.manajemenuang.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kost.golok.manajemenuang.R;
import kost.golok.utility.Formatter;
import kost.golok.utility.Preference;

import static android.content.Context.*;

public class UserInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = getActivity().getSharedPreferences(Preference.PREFERENCES_NAMES, MODE_PRIVATE);

        String nama = pref.getString(Preference.NAME, null);
        TextView tvNama = (TextView) view.findViewById(R.id.nama_info);
        tvNama.setText(nama);


        double dompet = Double.parseDouble(pref.getString(Preference.DOMPET, null));
        TextView tvDompet = (TextView) view.findViewById(R.id.dompet_info);
        tvDompet.setText(Formatter.format(dompet));

    }
}
