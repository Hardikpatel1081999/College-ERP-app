package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class student_quiztest extends AppCompatActivity {
    RecyclerView Subjectlist;
    ArrayList<Quiz> slist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiztest);
        Intent intent=getIntent();
        final String b = intent.getStringExtra("b");
        final String y = intent.getStringExtra("y");
        final String d = intent.getStringExtra("d");
        slist = new ArrayList<>();
        Subjectlist = findViewById(R.id.subjectlist);
        Subjectlist.setHasFixedSize(true);
        Subjectlist.setLayoutManager(new LinearLayoutManager(this));
        final AlertDialog.Builder pd = new AlertDialog.Builder(student_quiztest.this);
        View ew = LayoutInflater.from(getBaseContext()).inflate(R.layout.progressalert,null);
        final AlertDialog progressDialog =  pd.setView(ew).create();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quiz").child(b).child(y).child(d);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds :snapshot.getChildren())
                {
                        Quiz quiz = ds.getValue(Quiz.class);
                        slist.add(quiz);
                }
                QviewAdaptor view = new QviewAdaptor(student_quiztest.this,slist);
                Subjectlist.setAdapter(view);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(student_quiztest.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}