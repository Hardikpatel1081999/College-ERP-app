package com.example.project;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.project.ui.main.SectionsPagerAdapter1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.project.ui.main.SectionsPagerAdapter;

public class Student_timetable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        SectionsPagerAdapter1 sectionsPagerAdapter1 = new SectionsPagerAdapter1(this, getSupportFragmentManager());
        ViewPager viewPager1 = findViewById(R.id.view_pager1);
        viewPager1.setAdapter(sectionsPagerAdapter1);
        TabLayout tabs = findViewById(R.id.tabs1);
        tabs.setupWithViewPager(viewPager1);

    }
}