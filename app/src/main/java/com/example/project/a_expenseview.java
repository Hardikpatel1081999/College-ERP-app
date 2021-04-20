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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class a_expenseview extends AppCompatActivity {
    RecyclerView texpenserecycle;
    ArrayList<expense> expenselist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("expense");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_expenseview);
        expenselist = new ArrayList<>();
        texpenserecycle = findViewById(R.id.expenselistre);
        texpenserecycle.setHasFixedSize(true);
        texpenserecycle.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        Query z = databaseReference;
        z.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds :snapshot.getChildren())
                {
                        expense expense = ds.getValue(expense.class);
                        expenselist.add(expense);
                    }
                    ExpenseAdapter view = new ExpenseAdapter(a_expenseview.this,expenselist);
                    texpenserecycle.setAdapter(view);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(a_expenseview.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
});
    }
}