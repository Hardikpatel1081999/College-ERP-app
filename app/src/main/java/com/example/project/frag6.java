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

public class frag6 extends Fragment {
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
                    final String n = ds.child("name").getValue().toString();
                    final String s = ds.child("branch").getValue().toString();
                    Query cc = FirebaseDatabase.getInstance().getReference("Time").orderByChild("timename").equalTo(n);
                    cc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String ss =  ds.child("tbranch").getValue().toString();
                                String nn =  ds.child("timename").getValue().toString();
                                String j = ds.getRef().getKey();
                                if(nn.contentEquals(n)){
                                    if(ss.contentEquals(s))
                                    {
                                            DatabaseReference dx = databaseReference.child("Time").child(j).child("Saturday");
                                            dx.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    days ss = snapshot.getValue(days.class);
                                                    try {
                                                        l1.setText(ss.lec1);
                                                        l2.setText(ss.lec2);
                                                        l3.setText(ss.lec3);
                                                        l4.setText(ss.lec4);
                                                        l5.setText(ss.lec5);
                                                        l6.setText(ss.lec6);
                                                        l7.setText(ss.lec7);
                                                        l8.setText(ss.lec8);
                                                        l9.setText(ss.lec9);
                                                        l10.setText(ss.lec10);
                                                        l11.setText(ss.lec11);
                                                    }catch (Exception e)
                                                    {
                                                        Toast.makeText(getContext(),"Saturday TimeTable is Not Alloted ",Toast.LENGTH_SHORT).show();
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            });
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
