package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class student_chat_1 extends AppCompatActivity {
    TabLayout student_tabLayout;
    ViewPager student_viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat_1);
        student_tabLayout = findViewById(R.id.stab_layout);
        student_viewpager = findViewById(R.id.sview_layout);
        Intent intent = getIntent();
        final String uid = intent.getStringExtra("userid");
        ViewPageAdaptor viewp = new ViewPageAdaptor(getSupportFragmentManager());
        viewp.addFragment(new schatFragment(),"Chats");
        viewp.addFragment(new suserFragment(),"Teachers");
        student_viewpager.setAdapter(viewp);
        student_tabLayout.setupWithViewPager(student_viewpager);
    }
class ViewPageAdaptor extends FragmentPagerAdapter{
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