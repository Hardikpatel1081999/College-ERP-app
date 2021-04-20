package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class teacher_quiz extends AppCompatActivity {
Spinner tqyear,tqbranch;
EditText tsubname,tqdiv,tqchapcount;
Button tqnext;
private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quiz);
        tqbranch =findViewById(R.id.tqbranch);
        tqyear = findViewById(R.id.tqyear);
        tsubname = findViewById(R.id.tsubname);
        tqdiv = findViewById(R.id.tqdiv);
        tqnext = findViewById(R.id.tqnext);
        tqchapcount = findViewById(R.id.tqchapcount);
        final ArrayList<String> t1 = new ArrayList<>();
        t1.add("-- Year_Of_Education --");
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
        tqyear.setAdapter(arrayAdapter1);


        final ArrayList<String> t2 = new ArrayList<>();
        t2.add("-- Branch --");
        t2.add("COMP");
        t2.add("ELECTRONIC");
        t2.add("EXTC");
        t2.add("MECH");
        t2.add("IT");
        t2.add("CIVIL");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.spinnerlayout, t2){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        tqbranch.setAdapter(arrayAdapter2);
        tqnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef= FirebaseDatabase.getInstance().getReference("Quiz").child(tqbranch.getSelectedItem().toString()).child(tqyear.getSelectedItem().toString()).child(tqdiv.getText().toString()).child(tsubname.getText().toString());
                Quiz a = new Quiz(tsubname.getText().toString(),tqchapcount.getText().toString());
                mDatabaseRef.setValue(a);
                Intent i = new Intent(teacher_quiz.this,teacher_quizquestion.class);
                i.putExtra("b",tqbranch.getSelectedItem().toString());
                i.putExtra("y",tqyear.getSelectedItem().toString());
                i.putExtra("d",tqdiv.getText().toString());
                i.putExtra("s",tsubname.getText().toString());
                startActivity(i);
            }
        });
    }
}