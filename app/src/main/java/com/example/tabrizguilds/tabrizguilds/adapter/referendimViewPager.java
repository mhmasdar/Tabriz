package com.example.tabrizguilds.tabrizguilds.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tabrizguilds.tabrizguilds.fragments.competitionFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.referendumFragment;

/**
 * Created by mohamadHasan on 23/11/2017.
 */

public class referendimViewPager extends FragmentStatePagerAdapter {

    public referendimViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                referendumFragment RMsg = new referendumFragment();
                return RMsg;
            case 1:
                competitionFragment SMsg = new competitionFragment();
                return SMsg;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
