package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class teacher_notice extends AppCompatActivity {
    RecyclerView tnoticerecycle;
    ArrayList<Notice> noticelist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notice");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notice);
        noticelist = new ArrayList<>();
        tnoticerecycle = findViewById(R.id.tnoticerecycle);
        tnoticerecycle.setHasFixedSize(true);
        tnoticerecycle.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticelist.clear();
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    String s = ds.child("ntype").getValue().toString();
                    if(s.equals("Teacher")||s.equals("Both")){
                        Notice notice = ds.getValue(Notice.class);
                        noticelist.add(notice);
                    }
                }
                STviewAdaptor view = new STviewAdaptor(teacher_notice.this,noticelist);
                tnoticerecycle.setAdapter(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(teacher_notice.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}