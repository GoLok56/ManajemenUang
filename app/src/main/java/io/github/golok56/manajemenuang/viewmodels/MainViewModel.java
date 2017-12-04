package io.github.golok56.manajemenuang.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import io.github.golok56.manajemenuang.R;
import io.github.golok56.manajemenuang.utility.PreferenceManager;

/**
 * @author Satria Adi Putra
 */
public class MainViewModel extends AndroidViewModel {
    private long backPressedDuration;

    private PreferenceManager mPref;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mPref = PreferenceManager.getInstance(application);
    }

    public boolean hasLogin() {
        return mPref.getUserName() != null;
    }

    public long getBackPressedDuration() {
        return backPressedDuration;
    }

    public void setBackPressedDuration(long newValue) {
        backPressedDuration = newValue;
    }

    public String getConfirmText() {
        return getApplication().getString(R.string.konfirmasi);
    }

}
