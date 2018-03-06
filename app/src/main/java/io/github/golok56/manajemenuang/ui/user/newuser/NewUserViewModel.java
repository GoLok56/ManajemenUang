package io.github.golok56.manajemenuang.ui.user.newuser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.utility.PreferenceManager;

/**
 * @author Satria Adi Putra
 */

public class NewUserViewModel extends AndroidViewModel {
    private PreferenceManager mPref;

    public NewUserViewModel(Application app) {
        super(app);
        mPref = PreferenceManager.getInstance(app);
    }

    public void createUser(String name, String nominal) {
        mPref.setUserName(name);
        mPref.setWalet(nominal);
    }

    public String getValidationText() {
        return getApplication().getString(R.string.validasi_dialog);
    }

}
