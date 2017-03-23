package kost.golok.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kost.golok.manajemenuang.fragment.RecordList;
import kost.golok.manajemenuang.fragment.UserInfo;

public class PageAdapter extends FragmentStatePagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecordList();
            case 1:
                return new UserInfo();
        }
        return null;
    }
}
