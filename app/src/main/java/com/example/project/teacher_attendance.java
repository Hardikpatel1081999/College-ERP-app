package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class teacher_attendance extends AppCompatActivity {
    Spinner tabranch,tayear,tadiv;
    Button taviews;
    RecyclerView studentxlist;
    EditText taadate;
    ArrayList<College> list1;
    DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("College");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);
        list1 = new ArrayList<>();
        tabranch =findViewById(R.id.tabranch);
        tayear =findViewById(R.id.tayear);
        tadiv =findViewById(R.id.tadiv);
        taadate = findViewById(R.id.taadate);
        taviews = findViewById(R.id.btnviews);
        studentxlist = findViewById(R.id.studentxlist);
        studentxlist.setHasFixedSize(true);
        studentxlist.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> t1 = new ArrayList<>();
        t1.add("- Year -");
        t1.add("FE");
        t1.add("SE");
        t1.add("TE");
        t1.add("BE");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.spinnerlayout, t1){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        tayear.setAdapter(arrayAdapter1);


        final ArrayList<String> t2 = new ArrayList<>();
        t2.add("-- Branch --");
        t2.add("COMP");
        t2.add("ELECTRONIC");
        t2.add("EXTC");
        t2.add("MECH");
        t2.add("IT");
        t2.add("CIVIL");
        t2.add("-");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.spinnerlayout, t2){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        tabranch.setAdapter(arrayAdapter2);

        final ArrayList<String> t3 = new ArrayList<>();
        t3.add("- Div -");
        t3.add("A");
        t3.add("B");
        t3.add("C");
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.spinnerlayout, t3){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        tadiv.setAdapter(arrayAdapter3);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final Calendar calendar = Calendar.getInstance();
        String currentDate = format.format(calendar.getTime()).replace("/","-");
        taadate.setText(currentDate);


        taviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list1.clear();
                        for(DataSnapshot ds :snapshot.getChildren())
                        {
                            String s = ds.child("year").getValue().toString();
                            String sa = ds.child("divrollno").getValue().toString();
                            String sax = String.valueOf(sa.charAt(0));
                            String saa = ds.child("branch").getValue().toString();
                            if(saa.equals(tabranch.getSelectedItem().toString().trim()) && s.equals(tayear.getSelectedItem().toString().trim())&& sax.equals(tadiv.getSelectedItem().toString().trim()))
                            {
                                College college = ds.getValue(College.class);
                                list1.add(college);
                            }
                        }
                        AttendanceAdaptor view = new AttendanceAdaptor(teacher_attendance.this,list1,tabranch.getSelectedItem().toString().trim(),tadiv.getSelectedItem().toString().trim(),tayear.getSelectedItem().toString().trim(),taadate.getText().toString());
                        studentxlist.setAdapter(view);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(teacher_attendance.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    }
