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

public class admin_profileview extends AppCompatActivity {
    CircleImageView atvimage;
    TextView atname,atemail,atadress,atzip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profileview);
        atvimage = findViewById(R.id.tva_image);
       atname= findViewById(R.id.tva_name);
        atadress=findViewById(R.id.tva_address);
        atzip=findViewById(R.id.tva_zip);
        atemail=findViewById(R.id.tva_email);

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
                    String a = " " + ds.child("address").getValue().toString();
                    String z = " " + ds.child("zip").getValue().toString();
                    String e = " " + ds.child("email").getValue().toString();
                    try{
                        atname.setText(n);
                        atadress.setText(a);
                        atzip.setText(z);
                        atemail.setText(e);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                atvimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(admin_profileview.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(admin_profileview.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(admin_profileview.this, "please Contact to ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}