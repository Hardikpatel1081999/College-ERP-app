package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class sfrag5 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();
        final TextView l1 =view.findViewById(R.id.m1);
        final TextView l2 =view.findViewById(R.id.m2);
        final TextView l3 =view.findViewById(R.id.m3);
        final TextView l4 =view.findViewById(R.id.m4);
        final TextView l5 =view.findViewById(R.id.m5);
        final TextView l6 =view.findViewById(R.id.m6);
        final TextView l7 =view.findViewById(R.id.m7);
        final TextView l8 =view.findViewById(R.id.m8);
        final TextView l9 =view.findViewById(R.id.m9);
        final TextView l10 =view.findViewById(R.id.m10);
        final TextView l11=view.findViewById(R.id.m11);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query r = databaseReference.child("College").orderByChild("email").equalTo(user.getEmail());
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    final String y = ds.child("year").getValue().toString();
                    final String n = ds.child("divrollno").getValue().toString();
                    final char x = n.charAt(0);
                    final String s = ds.child("branch").getValue().toString();
                    Query cc = FirebaseDatabase.getInstance().getReference("Time").orderByChild("tbranch").equalTo(s);
                    cc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String ss =  ds.child("tbranch").getValue().toString();
                                String nn =  ds.child("tclass").getValue().toString();
                                String xx =  ds.child("div").getValue().toString();
                                Character xy = xx.charAt(0);
                                String j = ds.getRef().getKey();
                                if(ss.contentEquals(s)){
                                    if(nn.contentEquals(y))
                                    {
                                        if(xy.equals(x)){
                                            DatabaseReference dx = databaseReference.child("Time").child(j).child("Friday");
                                            dx.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    days sos = snapshot.getValue(days.class);
                                                    try {
                                                        l1.setText(sos.lec1);
                                                        l2.setText(sos.lec2);
                                                        l3.setText(sos.lec3);
                                                        l4.setText(sos.lec4);
                                                        l5.setText(sos.lec5);
                                                        l6.setText(sos.lec6);
                                                        l7.setText(sos.lec7);
                                                        l8.setText(sos.lec8);
                                                        l9.setText(sos.lec9);
                                                        l10.setText(sos.lec10);
                                                        l11.setText(sos.lec11);
                                                    }catch (Exception e)
                                                    {
                                                        Toast.makeText(getContext(),"Friday Timetable is Not  Alloted",Toast.LENGTH_SHORT);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                }

                            }

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

