package com.tennismate.tennismate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String>   tabTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);

    }


    public void addFragments(Fragment fragment, String title){
        this.fragments.add(fragment);
        this.tabTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
