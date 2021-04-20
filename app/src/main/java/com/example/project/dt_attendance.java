package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class dt_attendance extends AppCompatActivity {
Spinner tatime;
TextView tadate;
RecyclerView fslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt_attendance);
        tatime =findViewById(R.id.tatime);
        tadate = findViewById(R.id.tadate);
        fslist =  findViewById(R.id.fslist);
        fslist.setHasFixedSize(true);
        fslist.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<String> t4 = new ArrayList<>();
        t4.add("-- Lecture Time --");
        t4.add("8:00 am - 9:00 am");
        t4.add("9:00 am - 10:00 am");
        t4.add("10:15 am - 11:15 am");
        t4.add("11:15 am - 12:15 pm");
        t4.add("1:15 pm - 1:45 pm");
        t4.add("1:45 pm - 2:45 pm");
        t4.add("2:45 pm - 3:45 pm");
        t4.add("3:45 pm - 4:45 pm");
        t4.add("4:45 pm - 5:45 pm");

        ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, R.layout.spinnerlayout, t4){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        tatime.setAdapter(arrayAdapter4);

        int x = getIntent().getIntExtra("length",10);
        final ArrayList< String> listn = (ArrayList<String>) getIntent().getSerializableExtra("studentpname");
        final ArrayList< String> liste = (ArrayList<String>) getIntent().getSerializableExtra("studentemail");
        final ArrayList< String> listx = (ArrayList<String>) getIntent().getSerializableExtra("studentaname");
        final ArrayList< String> listxe = (ArrayList<String>) getIntent().getSerializableExtra("studentaemail");

        String cd = getIntent().getStringExtra("cd");
         final String classb = getIntent().getStringExtra("classb");
         final String classy = getIntent().getStringExtra("classy");
         final String classd = getIntent().getStringExtra("clasd");
         tadate.setText(cd);
        final String date =  tadate.getText().toString();



        tatime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View viewx, int position, long id) {
               switch (position)
               {
                   case 0:
                       break;
                   case 1:
                       finalpstudentAdaptor view = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist, t4.get(1),date);
                       fslist.setAdapter(view);
                       break;
                   case 2:
                       finalpstudentAdaptor view1 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(2),date);
                       fslist.setAdapter(view1);
                       break;
                   case 3:
                       finalpstudentAdaptor view2 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(3),date);
                       fslist.setAdapter(view2);
                       break;
                   case 4:
                       finalpstudentAdaptor view3 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(4),date);
                       fslist.setAdapter(view3);
                       break;
                   case 5:
                       finalpstudentAdaptor view4= new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(5),date);
                       fslist.setAdapter(view4);
                       break;
                   case 6:
                       finalpstudentAdaptor view5 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(6),date);
                       fslist.setAdapter(view5);
                       break;
                   case 7:
                       finalpstudentAdaptor view6 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(7),date);
                       fslist.setAdapter(view6);
                       break;
                   case 8:
                       finalpstudentAdaptor view7 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(8),date);
                       fslist.setAdapter(view7);
                       break;
                   case 9:
                       finalpstudentAdaptor view8 = new finalpstudentAdaptor(dt_attendance.this,listn,liste,listx,listxe,classb,classy,classd,fslist,t4.get(9),date);
                       fslist.setAdapter(view8);
                       break;

                   default:
                       throw new IllegalStateException("Unexpected value: " + position);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}