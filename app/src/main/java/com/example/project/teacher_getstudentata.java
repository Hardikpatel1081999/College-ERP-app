package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
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

public class teacher_getstudentata extends AppCompatActivity {
    RecyclerView studentnamelist;
    EditText ssearch;
    ArrayList<College> namelist,namelist1;
    String j;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_getstudentata);
        namelist = new ArrayList<>();
        namelist1 = new ArrayList<>();
        ssearch = findViewById(R.id.ssearch);
        studentnamelist =findViewById(R.id.studentnamelist);

        studentnamelist.setHasFixedSize(true);
        studentnamelist.setLayoutManager(new LinearLayoutManager(this));
      databaseReference= FirebaseDatabase.getInstance().getReference();
        Intent intent=getIntent();
        j = intent.getStringExtra("str");

        Query z =databaseReference.child("College").orderByChild("select").equalTo("Student");
        z.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                namelist1.clear();
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    String s = ds.child("branch").getValue().toString();
                    if(s.contentEquals(j.trim())){
                        College college = ds.getValue(College.class);
                        namelist1.add(college);
                    }
                }
                techearviewAdaptor view = new techearviewAdaptor(teacher_getstudentata.this,namelist1);
                studentnamelist.setAdapter(view);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ssearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty())
                {
                    search(s.toString());
                }
                else {
                    namelist.clear();
                    techearviewAdaptor view = new techearviewAdaptor(teacher_getstudentata.this,namelist1);
                    studentnamelist.setAdapter(view);
                }
            }
        });


    }


    private void search(String s) {
        Query q =databaseReference.child("College").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    namelist.clear();
                    for(DataSnapshot ds :snapshot.getChildren())
                    {
                        String s = ds.child("branch").getValue().toString();
                        String sel = ds.child("select").getValue().toString();
                        if(sel.equals("Student")){
                        if(s.contentEquals(j.trim())){
                            College college = ds.getValue(College.class);
                            namelist.add(college);
                        }}
                    }
                    techearviewAdaptor view = new techearviewAdaptor(teacher_getstudentata.this,namelist);
                   studentnamelist.setAdapter(view);
                    view.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
}