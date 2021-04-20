package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_dashboard extends AppCompatActivity {
    CardView adduser,viewuser,timetable,notice,expens;
    Toolbar toolbar;
    CircleImageView adminprofileimage;
    TextView adminname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        toolbar =findViewById(R.id.toolbar);
        adduser = findViewById(R.id.adduser);
        viewuser = findViewById(R.id.viewuser);
        timetable = findViewById(R.id.timetable);
        adminprofileimage = findViewById(R.id.Adminimage);
        adminname= findViewById(R.id.Adminname);
        notice = findViewById(R.id.notice);
        expens = findViewById(R.id.expenseoft);
        setSupportActionBar(toolbar);

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");

        Query q = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String n = " " + ds.child("name").getValue().toString();
                    try{
                     adminname.setText(n);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                          adminprofileimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Admin_dashboard.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Admin_dashboard.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin_dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(Admin_dashboard.this,adduser.class);
                startActivity(a);
            }
        });
        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(Admin_dashboard.this,Admin_userview.class);
                startActivity(a);
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(Admin_dashboard.this,Admin_timetable.class);
                startActivity(a);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(Admin_dashboard.this,admin_circular.class);
                startActivity(a);
            }
        });
        expens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_dashboard.this,a_expenseview.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menuu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId())
       {
           case R.id.item1:
               Intent x = new Intent(Admin_dashboard.this, admin_profileview.class);
               startActivity(x);
               return true;
           case R.id.item2:
               FirebaseAuth.getInstance().signOut();
               Intent y = new Intent(Admin_dashboard.this, login.class);
               startActivity(y);
               finish();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }

    }


}