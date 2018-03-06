package io.github.golok56.manajemenuang.ui.base;

import android.support.v4.app.DialogFragment;
import android.widget.Toast;

/**
 * @author Satria Adi Putra
 */
public class BaseDialog extends DialogFragment {

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
