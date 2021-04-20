package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class tuserfragment extends Fragment {
    private RecyclerView userinfolist;
    private DatabaseReference databaseReference;
    private ArrayList<College> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuserfragment, container, false);
        list = new ArrayList<>();
        userinfolist =view.findViewById(R.id.tuserxview);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        userinfolist.setHasFixedSize(true);
        userinfolist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();

        Query q1 =databaseReference.child("College").orderByChild("email").equalTo(user.getEmail());
        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    final String s1 = ds.child("branch").getValue().toString();
                    Query q =databaseReference.child("College").orderByChild("select").equalTo("Student");
                    q.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for(DataSnapshot ds :snapshot.getChildren())
                            {
                                final String s = ds.child("branch").getValue().toString();
                                if(s1.contentEquals(s)){
                                    College college = ds.getValue(College.class);
                                    list.add(college);
                                }
                            }
                            fuseradaptor view = new fuseradaptor(getContext(),list,false);
                            userinfolist.setAdapter(view);
                            view.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}