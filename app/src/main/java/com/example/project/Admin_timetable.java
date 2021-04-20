package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_timetable extends AppCompatActivity {
Button abtnnext;
Spinner abranch,aclass;
EditText adiv,fromtilldate;
AutoCompleteTextView ateachername;
RadioButton radiotype;
RadioGroup rgrp;

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_timetable);
        abtnnext =findViewById(R.id.btnsetnext);
        abranch=findViewById(R.id.abranch);
        aclass =findViewById(R.id.aclass);
        adiv =findViewById(R.id.adiv);
        rgrp = findViewById(R.id.rgrp);
        fromtilldate =findViewById(R.id.fromtodate);
        ateachername = findViewById(R.id.ateachername);
        ateachername.setThreshold(1);

        final ArrayList<String> t1 = new ArrayList<>();
        t1.add("-- Year_Of_Education --");
        t1.add("FE");
        t1.add("SE");
        t1.add("TE");
        t1.add("BE");
        t1.add("-");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.spinnerlayout, t1){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        aclass.setAdapter(arrayAdapter1);


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
        abranch.setAdapter(arrayAdapter2);
        final List<String> names= new ArrayList<String>();
Query q = databaseReference.child("College").orderByChild("select").equalTo("Teacher");
   q.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           for(DataSnapshot ds : snapshot.getChildren())
           {
            String name =  ds.child("name").getValue().toString();
            names.add(name);
           }
       }

       @Override
       public void onCancelled(@NonNull DatabaseError error) {
           Toast.makeText(Admin_timetable.this, error.getMessage(),Toast.LENGTH_SHORT).show();

       }
   });
        ArrayAdapter Adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,names);
        ateachername.setAdapter(Adapter);

      abtnnext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int selectedId = rgrp.getCheckedRadioButtonId();
              radiotype= (RadioButton) findViewById(selectedId);
              String ttype= radiotype.getText().toString();
              String tname = ateachername.getText().toString();
              String tbranch = abranch.getSelectedItem().toString();
              String tclass = aclass.getSelectedItem().toString();
              String tdiv = adiv.getText().toString();
              String tdate = fromtilldate.getText().toString();
          DatabaseReference td = databaseReference.child("Time");
          Time time = new Time(tname,ttype,tbranch,tclass,tdiv,tdate);
          String uploadId =td.push().getKey();
          td.child(uploadId).setValue(time);
              Intent a = new Intent(Admin_timetable.this,Admin_timetabledays.class);
              a.putExtra("ref",uploadId);
              startActivity(a);
          }
      });



    }
}