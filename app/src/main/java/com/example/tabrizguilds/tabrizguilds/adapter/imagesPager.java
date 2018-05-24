package com.example.tabrizguilds.tabrizguilds.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tabrizguilds.tabrizguilds.fragments.galleryFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.usersImagesFragment;

/**
 * Created by Mohamad Hasan on 2/16/2018.
 */

public class imagesPager extends FragmentStatePagerAdapter {

    private Bundle args;

    public imagesPager(FragmentManager fm, Bundle args) {
        super(fm);
        this.args = args;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                galleryFragment RMsg = new galleryFragment();
                RMsg.setArguments(args);
                return RMsg;
            case 1:
                usersImagesFragment SMsg = new usersImagesFragment();
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
