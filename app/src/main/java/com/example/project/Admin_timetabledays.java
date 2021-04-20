package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Admin_timetabledays extends AppCompatActivity {
    TextView atime1,atime2,atime3,atime4,atime5,atime6,atime7,atime8,atime9,atime10,atime11;
    Spinner day;
    EditText alec1,alec2,alec3,alec4,alec5,alec6,alec7,alec8,alec9,alec10,alec11;
    Button btntimetable;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Time");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_timetabledays);
        atime1 =findViewById(R.id.atime1);
        atime2 =findViewById(R.id.atime2);
        atime3 =findViewById(R.id.atime3);
        atime4 =findViewById(R.id.atime4);
        atime5 =findViewById(R.id.atime5);
        atime6 =findViewById(R.id.atime6);
        atime7 =findViewById(R.id.atime7);
        atime8 =findViewById(R.id.atime8);
        atime9 =findViewById(R.id.atime9);
        atime10 =findViewById(R.id.atime10);
        atime11 =findViewById(R.id.atime11);
        day =findViewById(R.id.day);
        alec1 =findViewById(R.id.lec1);
        alec2 =findViewById(R.id.lec2);
        alec3 =findViewById(R.id.lec3);
        alec4 =findViewById(R.id.lec4);
        alec5 =findViewById(R.id.lec5);
        alec6 =findViewById(R.id.lec6);
        alec7 =findViewById(R.id.lec7);
        alec8 =findViewById(R.id.lec8);
        alec9 =findViewById(R.id.lec9);
        alec10 =findViewById(R.id.lec10);
        alec11 =findViewById(R.id.lec11);
        btntimetable = findViewById(R.id.btnsetTimetable);
        Intent intent=getIntent();
        final String j = intent.getStringExtra("ref");

        final ArrayList<String> t1 = new ArrayList<>();
        t1.add("-- Days in a Week --");
        t1.add("Monday");
        t1.add("Tuesday");
        t1.add("Wednesday");
        t1.add("Thrusday");
        t1.add("Friday");
        t1.add("Saturday");
        t1.add("Sunday");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.spinnerlayout, t1){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        day.setAdapter(arrayAdapter1);
        final int[] x = {0};
        btntimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    x[0]++;
                String dayess= day.getSelectedItem().toString();
                    if(x[0]<=7){
                        DatabaseReference dx = databaseReference.child(j);
                        String lec1 =alec1.getText().toString();
                        String lec2 =alec2.getText().toString();
                        String lec3 =alec3.getText().toString();
                        String lec4 =alec4.getText().toString();
                        String lec5 =alec5.getText().toString();
                        String lec6 =alec6.getText().toString();
                        String lec7 =alec7.getText().toString();
                        String lec8 =alec8.getText().toString();
                        String lec9 =alec9.getText().toString();
                        String lec10 =alec10.getText().toString();
                        String lec11 =alec11.getText().toString();
                        days d = new days(lec1,lec2,lec3,lec4,lec5,lec6,lec7,lec8,lec9,lec10,lec11);
                        dx.child(dayess).setValue(d);
                        Toast.makeText(Admin_timetabledays.this,dayess+"  Timetable Added",Toast.LENGTH_SHORT).show();
                    }
                    if(x[0]>7){
                        Toast.makeText(Admin_timetabledays.this,dayess + "Already Exists",Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(Admin_timetabledays.this,Admin_dashboard.class);
                        startActivity(a);
                    }

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Admin_timetabledays.this,Admin_dashboard.class);
        startActivity(intent);
    }
}