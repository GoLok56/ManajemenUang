package kost.golok.manajemenuang.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kost.golok.adapter.PageAdapter;
import kost.golok.manajemenuang.R;
import kost.golok.object.utility.Preference;

import static java.security.AccessController.getContext;
import static kost.golok.manajemenuang.R.layout.dialog;


public class TransactionRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get rid the shadow below ActionBar
        getSupportActionBar().setElevation(0);

        SharedPreferences pref = getSharedPreferences(Preference.PREFERENCES, Context.MODE_PRIVATE);
        if (pref.getString(Preference.NAME, null) == null) {
            getDialog(pref).show();
        } else {
            display();
        }
    }

    private void display() {
        setContentView(R.layout.record_layout);

        // Setting up the view pager
        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(new PageAdapter(getSupportFragmentManager()));

        // Setting up the tab
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
    }

    private Dialog getDialog(final SharedPreferences pref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = pref.edit();
                        EditText etName = (EditText) view.findViewById(R.id.nama);
                        String name = etName.getText().toString();
                        editor.putString(Preference.NAME, name);
                        EditText etNominal = (EditText) view.findViewById(R.id.dompet);
                        String nominal = etNominal.getText().toString();
                        editor.putString(Preference.DOMPET, nominal);
                        editor.commit();
                        display();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Anda harus mengisi nama dan nominal dompet terlebih dahulu!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        return builder.create();
    }

}
