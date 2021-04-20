package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_book extends AppCompatActivity {
    RecyclerView sbookrecycle;
    ArrayList<Book> booklist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_book);

        Intent intent=getIntent();
        final String b = intent.getStringExtra("str1");
        final String y = intent.getStringExtra("str2");
        final String d = intent.getStringExtra("str3");
        booklist = new ArrayList<>();
        sbookrecycle = findViewById(R.id.sbookrecycle);
        sbookrecycle.setHasFixedSize(true);
        sbookrecycle.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booklist.clear();
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    String s = ds.child("byear").getValue().toString();
                    String sa = ds.child("bdiv").getValue().toString();
                    String saa = ds.child("bbranch").getValue().toString();
                    if(saa.equals(b) && s.equals(y)&& sa.equals(d))
                    {
                        Book book = ds.getValue(Book.class);
                        booklist.add(book);
                    }
                }
                BviewAdaptor view = new BviewAdaptor(student_book.this,booklist);
                sbookrecycle.setAdapter(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(student_book.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}