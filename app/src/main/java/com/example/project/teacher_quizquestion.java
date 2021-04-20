package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class teacher_quizquestion extends AppCompatActivity {
EditText tques,top1,top2,top3,top4,topans,tchap;
Button btntdone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_quizquestion);
        tchap = findViewById(R.id.tchap);
        tques = findViewById(R.id.tques);
        top1 = findViewById(R.id.top1);
        top2 = findViewById(R.id.top2);
        top3 = findViewById(R.id.top3);
        top4 = findViewById(R.id.top4);
        topans = findViewById(R.id.topans);
        btntdone = findViewById(R.id.btntdone);
        Intent intent=getIntent();
       String s =  intent.getStringExtra("s");
        String b =  intent.getStringExtra("b");
        String y =  intent.getStringExtra("y");
        String d =  intent.getStringExtra("d");
        final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Quiz").child(b).child(y).child("SETS_OF"+s+"_"+d).child(s).child("questions");
        btntdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quizquestion qq = new Quizquestion(tques.getText().toString(),top1.getText().toString(),top2.getText().toString(),top3.getText().toString(),top4.getText().toString(),topans.getText().toString(),tchap.getText().toString());
                String uploadId = mDatabaseRef.push().getKey();
                mDatabaseRef.child(uploadId).setValue(qq);
                Toast.makeText(teacher_quizquestion.this," DONE ",Toast.LENGTH_SHORT).show();
            }
        });
    }
}