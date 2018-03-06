package io.github.golok56.manajemenuang.views.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity

import io.github.golok56.manajemenuang.ui.transaction.TransactionListFragment
import io.github.golok56.manajemenuang.views.fragment.UserInfoFragment

/**
 * @author Satria Adi Putra
 */
class PageAdapter
/**
 * Constructor untuk PageAdapter yang memanggil Parent Constructor [FragmentStatePagerAdapter]
 *
 * @param fm Fragment Manager yang didapat dari [AppCompatActivity.getSupportFragmentManager]
 */
(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    /**
     * @return Jumlah tab yang ada
     */
    override fun getCount(): Int {
        return 2
    }

    /**
     * @param pos Posisi tab yang aktif
     * @return Fragment sesuai tab aktif
     */
    override fun getItem(pos: Int): Fragment {
        when (pos) {
            1 -> return TransactionListFragment()
            else -> return UserInfoFragment()
        }
    }

    /**
     * @param pos Posisi tab yang aktif
     * @return Judul sesuai tab yang aktif
     */
    override fun getPageTitle(pos: Int): CharSequence? {
        when (pos) {
            1 -> return "History"
            else -> return "Info"
        }
    }

}
