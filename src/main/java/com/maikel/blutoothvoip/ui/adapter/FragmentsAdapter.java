package com.maikel.blutoothvoip.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by maikel on 2018/3/24.
 */

public class FragmentsAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public FragmentsAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm);
        this.fragments = fragments;
    }

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0:fragments.size();
    }
}
