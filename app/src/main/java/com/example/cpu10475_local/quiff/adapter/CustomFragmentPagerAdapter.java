package com.example.cpu10475_local.quiff.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cpu10475_local.quiff.fragments.Fragment1;
import com.example.cpu10475_local.quiff.fragments.Fragment2;
import com.example.cpu10475_local.quiff.fragments.Fragment3;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            if (fragment1 == null)
                fragment1 = new Fragment1();
            return fragment1;
        }
        else if(position == 1) {
            if (fragment2 == null)
                fragment2 = new Fragment2();
            return fragment2;
        }
        else if(position==2)
        {
            if(fragment3==null)
                fragment3 = new Fragment3();
            return fragment3;
        }
        else
            return  null;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "Title - " + String.valueOf(position);
    }


}
