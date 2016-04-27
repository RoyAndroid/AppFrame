package com.appframe.biz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appframe.biz.extend.BasePagerFragment;
import com.appframe.lib.Log.L;

import java.util.List;

/**
 * Created by Roy
 * Date: 16/4/24
 */
public class FragmentPageAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = FragmentPageAdapter.class.getName();

    private List<BasePagerFragment> pageFragments;
    private FragmentManager manager;

    public FragmentPageAdapter(FragmentManager fm, List<BasePagerFragment> pageFragments) {
        super(fm);
        this.manager = fm;
        this.pageFragments = pageFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageFragments.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int i) {
        L.d(TAG, pageFragments.get(i).toString());
        return pageFragments.get(i);
    }

    @Override
    public int getCount() {
        return pageFragments.size();
    }

    public void removeAll() {
        if (pageFragments != null && pageFragments.size() > 0) {
            for (BasePagerFragment pagerFragment : pageFragments) {
                manager.beginTransaction().remove(pagerFragment).commit();
            }
        }
    }
}

