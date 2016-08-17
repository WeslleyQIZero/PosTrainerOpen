package com.wiseass.postrainer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.ui.fragment.FragmentPagerImage;
import com.viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;

/**
 * Jake Wharton is a Baller, straight up.
 * Created by Ryan on 11/08/2016.
 */
public class CustomPagerAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {
    private ArrayList<Integer> imageResourceIds;

    public CustomPagerAdapter(FragmentManager fm, ArrayList<Integer> imageResourceIds) {
        super(fm);
        this.imageResourceIds = imageResourceIds;

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentPagerImage.newInstance(imageResourceIds.get(position));
    }

    @Override
    public int getIconResId(int index) {
        return R.drawable.ic_alarm_black_48dp;
    }

    @Override
    public int getCount() {
        return imageResourceIds.size();
    }
}
