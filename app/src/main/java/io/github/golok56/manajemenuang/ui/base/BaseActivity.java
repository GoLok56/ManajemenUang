package io.github.golok56.manajemenuang.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author Satria Adi Putra
 */
public class BaseActivity extends AppCompatActivity {

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
