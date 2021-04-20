package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class teacher_chat_1 extends AppCompatActivity {
    TabLayout t_tabLayout;
    ViewPager t_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chat_1);
        t_tabLayout = findViewById(R.id.ttab_layout);
        t_viewpager = findViewById(R.id.tview_layout);
        teacher_chat_1.ViewPageAdaptor viewp = new teacher_chat_1.ViewPageAdaptor(getSupportFragmentManager());
        viewp.addFragment(new tchatfragment(),"Chat");
        viewp.addFragment(new tuserfragment(),"Student");
        t_viewpager.setAdapter(viewp);
        t_tabLayout.setupWithViewPager(t_viewpager);
    }
    class ViewPageAdaptor extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;
        ViewPageAdaptor(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}