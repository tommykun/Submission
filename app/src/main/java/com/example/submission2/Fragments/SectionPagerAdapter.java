package com.example.submission2.Fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.submission2.R;

public class SectionPagerAdapter extends FragmentPagerAdapter {


    @StringRes
    private static final int[] JUDUL_TAB = new int[]{R.string.title_movie,R.string.title_tv};
    private final Context context;

    public SectionPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case   0:
                return FarFragMovie.newInstance("movie");
            case 1:
                return FarFragTv.newInstance("tv");
                default:
                    return null;
        }

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(JUDUL_TAB[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
