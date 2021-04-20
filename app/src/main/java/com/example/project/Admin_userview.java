package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class Admin_userview extends AppCompatActivity {
RecyclerView userinfolist;
Button btstudent,btteacher;
EditText editText;
DatabaseReference databaseReference;
ArrayList<College> list,list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_userview);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        btstudent=findViewById(R.id.btstudent);
        editText = findViewById(R.id.editText);
        btteacher=findViewById(R.id.btteacher);
        userinfolist =findViewById(R.id.usernamelist);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View customview = LayoutInflater.from(getBaseContext()).inflate(R.layout.adminviewalert,null);
        final AlertDialog alertDialog = builder.setView(customview).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);




         databaseReference= FirebaseDatabase.getInstance().getReference();
        userinfolist.setHasFixedSize(true);
        userinfolist.setLayoutManager(new LinearLayoutManager(this));


        editText.addTextChangedListener(new TextWatcher() {
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
                    btteacher.setTextColor(Color.GRAY);
                    btstudent.setTextColor(Color.GRAY);
                    search(s.toString());
                }
                else {
                    btteacher.setTextColor(Color.BLACK);
                    btstudent.setTextColor(Color.BLACK);
                   search(" ");
                }
            }
        });


        btteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btteacher.setTextColor(Color.BLACK);
                btstudent.setTextColor(Color.GRAY);
                Query q =databaseReference.child("College").orderByChild("select").equalTo("Teacher");
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list1.clear();
                        for(DataSnapshot ds :snapshot.getChildren())
                        {
                            College college = ds.getValue(College.class);
                            list1.add(college);
                        }
                        viewAdaptor view = new viewAdaptor(Admin_userview.this,list1);
                        userinfolist.setAdapter(view);
                        view.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });


        btstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btstudent.setTextColor(Color.BLACK);
                btteacher.setTextColor(Color.GRAY);
                Query q =databaseReference.child("College").orderByChild("select").equalTo("Student");
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list1.clear();
                        for(DataSnapshot ds :snapshot.getChildren())
                        {
                            College college = ds.getValue(College.class);
                            list1.add(college);
                        }
                        viewAdaptor view = new viewAdaptor(Admin_userview.this,list1);
                        userinfolist.setAdapter(view);
                        view.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

    }

    private void search(String s) {

        Query q =databaseReference.child("College").orderByChild("name").startAt(s).endAt(s+"\uf8ff");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds :snapshot.getChildren())
                {
                    String sel = ds.child("select").getValue().toString();
                    if(sel.equals("Teacher")||sel.equals("Student")){
                        College college = ds.getValue(College.class);
                        list.add(college);
                    }
                }
                viewAdaptor view = new viewAdaptor(Admin_userview.this,list);
                userinfolist.setAdapter(view);
                view.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Admin_userview.this,Admin_dashboard.class);
        startActivity(intent);
    }

}