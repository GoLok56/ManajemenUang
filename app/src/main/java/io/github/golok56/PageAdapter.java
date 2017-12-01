package io.github.golok56;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import io.github.golok56.manajemenuang.fragment.RecordList;
import io.github.golok56.manajemenuang.fragment.UserInfo;

/**
 * Custom adapter untuk mengatur menu tab pada {@link kost.golok56.manajemenuang.activity.TransactionRecord}
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    /**
     * Constructor untuk PageAdapter yang memanggil Parent Constructor {@link FragmentStatePagerAdapter}
     *
     * @param fm Fragment Manager yang didapat dari {@link AppCompatActivity#getSupportFragmentManager()}
     */
    public PageAdapter(FragmentManager fm) {
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
                return new RecordList();
            default:
                return new UserInfo();
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
