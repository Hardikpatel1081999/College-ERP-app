package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    EditText etemail,etpassword;
    Spinner spselect;
    Button btnsignin,btnshow;
    TextView tvforgot;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        spselect = findViewById(R.id.spselect);
        btnsignin = findViewById(R.id.btnsignin);
        btnshow = findViewById(R.id.btnshow);
        tvforgot = findViewById(R.id.tvforgot);

        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("College");
        final ArrayList<String> t = new ArrayList<>();
        t.add("Admin");
        t.add("Teacher");
        t.add("Student");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinnerlayout, t);
        spselect.setAdapter(arrayAdapter);

        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(login.this);
                final View customview = LayoutInflater.from(getBaseContext()).inflate(R.layout.forgetpassword,null);
                AlertDialog alertDialog = builder.setView(customview).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Reset Password", null)
                        .create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText etfemail = customview.findViewById(R.id.etfemail);
                        String forgetemail = etfemail.getText().toString().trim();
                        if (forgetemail.isEmpty()){
                            etfemail.setError("Please enter Email Address ");
                            etfemail.requestFocus();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(forgetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(login.this,"Recovery password Link is sended to your email",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    etfemail.setError("Entered email is wrong ");
                                    etfemail.requestFocus();
                                    return;
                                }

                            }
                        });
                    }
                });
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= etemail.getText().toString();
                final String password = etpassword.getText().toString();
                final String spselected=spselect.getSelectedItem().toString();

                if(TextUtils.isEmpty(email)){
                    etemail.setError("Enter the email ");
                    etemail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etpassword.setError("enter the password");
                    etpassword.requestFocus();
                    return;
                }

            final AlertDialog.Builder pd = new AlertDialog.Builder(login.this);
               View ew = LayoutInflater.from(getBaseContext()).inflate(R.layout.progressalert,null);
             final AlertDialog progressDialog =  pd.setView(ew).create();
             progressDialog.setCanceledOnTouchOutside(false);
               progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                                    Query query = mDatabase.orderByChild("email").equalTo(email);
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot ds : snapshot.getChildren())
                                            {
                                                String ss = ds.child("select").getValue().toString();

                                                if(spselected.equals(ss))
                                                {
                                                    if(spselected.equals("Admin")){
                                                        progressDialog.dismiss();
                                                        Intent a = new Intent (login.this,Admin_dashboard.class);
                                                        startActivity(a);
                                                        finish();
                                                    }
                                                   else if(spselected.equals("Teacher")){
                                                        progressDialog.dismiss();
                                                        Intent a = new Intent (login.this,Teacher_dashboard.class);
                                                        startActivity(a);
                                                        finish();
                                                    }
                                                    else {
                                                        progressDialog.dismiss();
                                                        Intent a = new Intent (login.this,Student_dashboard.class);
                                                        startActivity(a);
                                                        finish();
                                                    }
                                                }
                                             else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(login.this, "Please try again", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            progressDialog.dismiss();
                                            Toast.makeText(login.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(login.this,"Please Check your Password and Email again!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btnshow.getText().toString();
                if(s.equals("Show")){
                    etpassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    btnshow.setText("Hide");
                } else{
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnshow.setText("Show");
                }
            }
        });
    }
}