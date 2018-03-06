package io.github.golok56.manajemenuang.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import io.github.golok56.manajemenuang.ui.transaction.list.TransactionListFragment;
import io.github.golok56.manajemenuang.ui.user.info.UserInfoFragment;

/**
 * @author Satria Adi Putra
 */
public class MainAdapter extends FragmentStatePagerAdapter {

    /**
     * Constructor untuk MainAdapter yang memanggil Parent Constructor {@link FragmentStatePagerAdapter}
     *
     * @param fm Fragment Manager yang didapat dari {@link AppCompatActivity#getSupportFragmentManager()}
     */
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * @return Jumlah tab yang ada
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * @param pos Posisi tab yang aktif
     * @return Fragment sesuai tab aktif
     */
    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 1:
                return new TransactionListFragment();
            default:
                return new UserInfoFragment();
        }
    }

    /**
     * @param pos Posisi tab yang aktif
     * @return Judul sesuai tab yang aktif
     */
    @Override
    public CharSequence getPageTitle(int pos) {
        switch (pos) {
            case 1:
                return "History";
            default:
                return "Info";
        }
    }

}
