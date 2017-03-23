package kost.golok.manajemenuang.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kost.golok.adapter.PageAdapter;
import kost.golok.manajemenuang.R;


public class TransactionRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.record_layout);
        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(new PageAdapter(getSupportFragmentManager()));
        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
    }
}
