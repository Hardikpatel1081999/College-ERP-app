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

public class student_profileview extends AppCompatActivity {
    CircleImageView tvimage;
    TextView etname,etapplicationno,etdoa,etadress,etzip,etdob,etnationality,etphoneno,etephoneno,tvdivrno,
            tvsemester,tvemail,etbranch,etgender,etstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profileview);
        tvimage = findViewById(R.id.tvimg);
        etname= findViewById(R.id.tvname);
        etapplicationno=findViewById(R.id.tvappno);
        etdoa=findViewById(R.id.tvdoa);
        etadress=findViewById(R.id.tvaddress);
        etzip=findViewById(R.id.tvzip);
        etdob=findViewById(R.id.tvdob);
        etnationality=findViewById(R.id.tvnation);
        etphoneno=findViewById(R.id.tvphone);
        etephoneno=findViewById(R.id.tvephone);
        tvdivrno=findViewById(R.id.tvclass);
        tvsemester=findViewById(R.id.tvsem);
        tvemail=findViewById(R.id.tvemail);
        etbranch=findViewById(R.id.tvbranch);
        etgender=findViewById(R.id.tvgender);
        etstate=findViewById(R.id.tvstate);

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
                    String year = " " + ds.child("year").getValue().toString();
                    String div = " " + ds.child("divrollno").getValue().toString();
                    String sem= " " + ds.child("semester").getValue().toString();
                    String e = " " + ds.child("email").getValue().toString();

                    String c = year+"/"+div;
                    try{
                       etname.setText(n);
                       etapplicationno.setText(ano);
                       etdoa.setText(doa);
                       etadress.setText(a);
                       etzip.setText(z);
                       etdob.setText(dob);
                       etstate.setText(s);
                       etgender.setText(gender);
                       etnationality.setText(nt);
                       etphoneno.setText(p);
                       etephoneno.setText(pe);
                       etbranch.setText(b);
                       tvdivrno.setText(c);
                       tvsemester.setText(sem);
                       tvemail.setText(e);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                tvimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(student_profileview.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(student_profileview.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(student_profileview.this, "please Contact to Admin", Toast.LENGTH_SHORT).show();
            }
        });


    }
}