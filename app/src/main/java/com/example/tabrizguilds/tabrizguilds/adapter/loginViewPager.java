package com.example.tabrizguilds.tabrizguilds.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tabrizguilds.tabrizguilds.fragments.competitionFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.loginFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.referendumFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.registerFragment;

/**
 * Created by mohamadHasan on 08/12/2017.
 */

public class loginViewPager extends FragmentStatePagerAdapter {

    public loginViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                loginFragment RMsg = new loginFragment();
                return RMsg;
            case 1:
                registerFragment SMsg = new registerFragment();
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