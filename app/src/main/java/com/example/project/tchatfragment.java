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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class tchatfragment extends Fragment {
    private RecyclerView chatinfolist;
    private DatabaseReference databaseReference;
    private ArrayList<College> list;
    private List<String> clist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tchatfragment, container, false);

        clist = new ArrayList<>();
        chatinfolist =view.findViewById(R.id.tchatview);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        chatinfolist.setHasFixedSize(true);
        chatinfolist.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();

        databaseReference =FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clist.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    Chat chatl = ds.getValue(Chat.class);

                    if (chatl.getMsgsender().equals(user.getEmail())) {
                        clist.add(chatl.getMsgreceiver());
                    }
                    if (chatl.getMsgreceiver().equals(user.getEmail())) {
                        clist.add(chatl.getMsgsender());
                    }
                }
                reading();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void reading() {
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("College");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    College college =ds.getValue(College.class);
                    for(String email: clist)
                    {
                        if(college.getEmail().equals(email))
                        {
                            if(list.size() != 0)
                            {
                                for( College collegex : list) {
                                    if (!college.getEmail().equals(collegex.getEmail())) {
                                        list.add(college);
                                    }
                                }
                            }else
                            {
                                list.add(college);
                            }
                        }
                    }
                }
                fuseradaptor fuser= new fuseradaptor(getContext(),list,true);
                chatinfolist.setAdapter(fuser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}