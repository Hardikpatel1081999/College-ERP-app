package com.example.project.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.project.R;
import com.example.project.frag1;
import com.example.project.frag2;
import com.example.project.frag3;
import com.example.project.frag4;
import com.example.project.frag5;
import com.example.project.frag6;
import com.example.project.frag7;
import com.example.project.sfrag1;
import com.example.project.sfrag2;
import com.example.project.sfrag3;
import com.example.project.sfrag4;
import com.example.project.sfrag5;
import com.example.project.sfrag6;
import com.example.project.sfrag7;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4,R.string.tab_text_5,R.string.tab_text_6,R.string.tab_text_7};
    private final Context mContext;


    public SectionsPagerAdapter1(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:fragment =new sfrag1();
                break;
            case 1:fragment =new sfrag2();
                break;
            case 2:fragment =new sfrag3();
                break;
            case 3:fragment =new sfrag4();
                break;
            case 4:fragment =new sfrag5();
                break;
            case 5:fragment =new sfrag6();
                break;
            case 6:fragment =new sfrag7();
                break;}
                return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "MONDAY";
            case 1:return "TUESDAY";
            case 2:return "WEDNESDAY";
            case 3:return "THRUSDAY";
            case 4:return "FRIDAY";
            case 5:return "SATURDAY";
            case 6:return "SUNDAY";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 7;
    }
}