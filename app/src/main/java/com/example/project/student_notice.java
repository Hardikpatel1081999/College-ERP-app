package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_notice extends AppCompatActivity {
    RecyclerView snoticerecycle;
    ArrayList<Notice> noticelist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notice");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice);
        noticelist = new ArrayList<>();
        snoticerecycle = findViewById(R.id.snoticerecycle);
        snoticerecycle.setHasFixedSize(true);
        snoticerecycle.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticelist.clear();
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    String s = ds.child("ntype").getValue().toString();
                    if(s.equals("Student")||s.equals("Both")){
                        Notice notice = ds.getValue(Notice.class);
                        noticelist.add(notice);
                    }
                }
                STviewAdaptor view = new STviewAdaptor(student_notice.this,noticelist);
                snoticerecycle.setAdapter(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}