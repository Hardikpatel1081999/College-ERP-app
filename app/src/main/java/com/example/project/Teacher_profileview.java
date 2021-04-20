package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class Teacher_profileview extends AppCompatActivity {
    CircleImageView tvt_image;
    TextView ettname,ettapplicationno,ettdoa,ettadress,ettzip,ettdob,ettnationality,ettphoneno,ettephoneno,tvtdivrno,
            tvtemail,ettbranch,ettgender,ettstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profileview);
        tvt_image = findViewById(R.id.tvt_image);
        ettname= findViewById(R.id.tvt_name);
        ettapplicationno=findViewById(R.id.tvt_appno);
        ettdoa=findViewById(R.id.tvt_doa);
        ettadress=findViewById(R.id.tvt_address);
        ettzip=findViewById(R.id.tvt_zip);
        ettdob=findViewById(R.id.tvt_dob);
        ettnationality=findViewById(R.id.tvt_nation);
        ettphoneno=findViewById(R.id.tvt_phone);
        ettephoneno=findViewById(R.id.tvt_ephone);
        tvtdivrno=findViewById(R.id.tvt_education);
        tvtemail=findViewById(R.id.tvt_email);
        ettbranch=findViewById(R.id.tvt_branch);
        ettgender=findViewById(R.id.tvt_gender);
        ettstate=findViewById(R.id.tvt_state);

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");


        Query t = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
        t.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String n = " " + ds.child("name").getValue().toString();
                    String ano= " " + ds.child("applicationno").getValue().toString();
                    String doa = " " + ds.child("doa").getValue().toString();
                    String a = " " + ds.child("address").getValue().toString();
                    String z = " " + ds.child("zip").getValue().toString();
                    String s = " " + ds.child("state").getValue().toString();
                    String dob = " " + ds.child("dob").getValue().toString();
                    String gender = " " + ds.child("gender").getValue().toString();
                    String nt = " " + ds.child("nationallity").getValue().toString();
                    String p = " " + ds.child("phoneno").getValue().toString();
                    String pe = " " + ds.child("ephone").getValue().toString();
                    String b = " " + ds.child("branch").getValue().toString();
                    String div = " " + ds.child("divrollno").getValue().toString();
                    String e = " " + ds.child("email").getValue().toString();

                    try{
                        ettname.setText(n);
                        ettapplicationno.setText(ano);
                        ettdoa.setText(doa);
                        ettadress.setText(a);
                        ettzip.setText(z);
                        ettdob.setText(dob);
                        ettstate.setText(s);
                        ettgender.setText(gender);
                        ettnationality.setText(nt);
                        ettphoneno.setText(p);
                        ettephoneno.setText(pe);
                        ettbranch.setText(b);
                        tvtdivrno.setText(div);
                        tvtemail.setText(e);

                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2924 * 2924).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                tvt_image.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Teacher_profileview.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Teacher_profileview.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Teacher_profileview.this, "Please contact to Admin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}