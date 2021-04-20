package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class aupdate extends AppCompatActivity {
TextView upemail;
Button suupdate;
CircleImageView suimage;
EditText etname,etapplicationno,etdoa,etadress,etzip,etdob,etphoneno,etephoneno,tvdivrno,
            tvsemester,etbranch,etyear,etgender,etstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aupdate);
        suupdate = findViewById(R.id.suupdate);
        suimage = findViewById(R.id.suimg);
        upemail = findViewById(R.id.suemail);
        etname= findViewById(R.id.suname);
        etapplicationno=findViewById(R.id.suappno);
        etdoa=findViewById(R.id.sudoa);
        etadress=findViewById(R.id.suaddress);
        etzip=findViewById(R.id.suzip);
        etdob=findViewById(R.id.sudob);
        etphoneno=findViewById(R.id.suphone);
        etephoneno=findViewById(R.id.suephone);
        tvdivrno=findViewById(R.id.sudroll);
        tvsemester=findViewById(R.id.susem);
        etbranch=findViewById(R.id.subranch);
        etgender=findViewById(R.id.augender);
        etstate=findViewById(R.id.sustate);
        etyear=findViewById(R.id.suclass);
        Intent intent = getIntent();
        final String str = intent.getStringExtra("udemail");
        upemail.setText(str);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");
        Query q = mdatabaseRef.orderByChild("email").equalTo(str);
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
                    String p = " " + ds.child("phoneno").getValue().toString();
                    String pe = " " + ds.child("ephone").getValue().toString();
                    String b = " " + ds.child("branch").getValue().toString();
                    String year = " " + ds.child("year").getValue().toString();
                    String div = " " + ds.child("divrollno").getValue().toString();
                    String sem= " " + ds.child("semester").getValue().toString();
                    try{
                        etname.setText(n);
                        etapplicationno.setText(ano);
                        etdoa.setText(doa);
                        etadress.setText(a);
                        etzip.setText(z);
                        etdob.setText(dob);
                        etstate.setText(s);
                        etgender.setText(gender);
                        etphoneno.setText(p);
                        etephoneno.setText(pe);
                        etbranch.setText(b);
                        etyear.setText(year);
                        tvdivrno.setText(div);
                        tvsemester.setText(sem);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(str+".jpeg");
                        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                suimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(aupdate.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(aupdate.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        suupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query q = mdatabaseRef.orderByChild("email").equalTo(str);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            String n = ds.getKey();
                            final String name = etname.getText().toString().trim();
                            final String applicationno = etapplicationno.getText().toString().trim();
                            final String doa = etdoa.getText().toString().trim();
                            final String address = etadress.getText().toString().trim();
                            final String zip = etzip.getText().toString().trim();
                            final String state = etstate.getText().toString().trim();
                            final String dob = etdob.getText().toString().trim();
                            final String gender = etgender.getText().toString().trim();
                            final String nationallity = ds.child("nationallity").getValue().toString();
                            final String phoneno = etphoneno.getText().toString().trim();
                            final String ephone = etephoneno.getText().toString().trim();
                            final String select = ds.child("select").getValue().toString();
                            final String branch = etbranch.getText().toString().trim();
                            final String year = etyear.getText().toString().trim();
                            final String divrollno = tvdivrno.getText().toString().trim();
                            final String semester = tvsemester.getText().toString().trim();
                            final String email = upemail.getText().toString().trim();
                            final String password = ds.child("password").getValue().toString();
                            DatabaseReference dx = FirebaseDatabase.getInstance().getReference("College").child(n);
                            College c = new College(name,applicationno,doa,address,zip,state,dob,gender,nationallity,phoneno,ephone,select,branch,year,divrollno,semester,email,password);
                            dx.setValue(c);
                            final AlertDialog.Builder pd = new AlertDialog.Builder(aupdate.this);
                            View ew = LayoutInflater.from(getBaseContext()).inflate(R.layout.done,null);
                            final AlertDialog progressDialog =  pd.setView(ew).create();
                            progressDialog.setCanceledOnTouchOutside(true);
                            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            progressDialog.show();
                            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    Intent intent = new Intent(aupdate.this,Admin_userview.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });

    }
}