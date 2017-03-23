package kost.golok.manajemenuang.activity;

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
        setContentView(R.layout.record_layout);
        ViewPager vp = (ViewPager) findViewById(R.id.pager);
        vp.setAdapter(new PageAdapter(getSupportFragmentManager()));
    }
}
