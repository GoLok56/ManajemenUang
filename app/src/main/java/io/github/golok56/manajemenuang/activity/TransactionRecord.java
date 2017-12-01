package io.github.golok56.manajemenuang.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import io.github.golok56.PageAdapter;
import io.github.golok56.manajemenuang.R;
import io.github.golok56.utility.Component;
import io.github.golok56.utility.Preference;

public class TransactionRecord extends AppCompatActivity {

    private static final String OK = "OK";
    private static final String CANCEL = "Cancel";
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get rid the shadow below ActionBar
        getSupportActionBar().setElevation(0);

        SharedPreferences pref = getSharedPreferences(Preference.PREFERENCES_NAMES, Context.MODE_PRIVATE);
        if (pref.getString(Preference.NAME, null) == null) {
            getDialog(pref).show();
        } else {
            display();
        }
    }

    private void display() {
        setContentView(R.layout.transaction_record);
        initActionBar();
    }

    private void initActionBar(){
        ViewPager vp = getViewPager();
        setupTab(vp);
    }

    private void setupTab(ViewPager vp){
        // Setting up the tab
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
    }

    private ViewPager getViewPager(){
        // Setting up the view pager
        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(new PageAdapter(getSupportFragmentManager()));
        return vp;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
        } else
            Toast.makeText(TransactionRecord.this, getString(R.string.konfirmasi), Toast.LENGTH_SHORT).show();

        back_pressed = System.currentTimeMillis();
    }

    private Dialog getDialog(final SharedPreferences pref) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog, null);
        Listener listener = new Listener(view, pref);
        builder.setView(view)
                .setPositiveButton(OK, listener.ok)
                .setNegativeButton(CANCEL, listener.cancel);
        return builder.create();
    }

    private class Listener{

        private final String TEXT_VALIDASI = getResources().getString(R.string.validasi_dialog);
        private View mView;
        private SharedPreferences mPref;

        Listener(View view, SharedPreferences pref){
            this.mView = view;
            this.mPref = pref;
        }

        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nama = Component.getText(mView, R.id.nama);
                String nominal = Component.getText(mView, R.id.dompet);
                Preference.createUser(mPref, nama, nominal);
                display();
            }
        };

        DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), TEXT_VALIDASI, Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }

}
