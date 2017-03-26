package kost.golok.manajemenuang.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kost.golok.manajemenuang.R;
import kost.golok.object.Transaction;

public class TransactionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);
        init();
    }

    private void init() {
        TextView judul = (TextView) findViewById(R.id.judul_detail);
        TextView jumlah = (TextView) findViewById(R.id.jumlah_detail);
        TextView tanggal = (TextView) findViewById(R.id.tanggal_detail);
        TextView ket = (TextView) findViewById(R.id.ket_detail);

        String[] content = getIntent().getStringArrayExtra("content");

        jumlah.setText(content[0]);
        tanggal.setText(content[1]);
        ket.setText(content[2]);
        judul.setText(content[3]);
    }

}
