package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;

public class adduser extends AppCompatActivity {
CircleImageView userprofileimg;
EditText etusername,etuserApplicationNo,etuserDOA,etuseradress,etuserzip,etuserDOB,etusernationality,etuserphoneno,etuserephoneno,etdivrno,etsemester,etemail,etpassword,etcpassword;
Spinner etuserselect,etuseryear,etuserbranch,etusergender,etuserstate;
Button signup;
private Uri imageUri;
private static final int PICK_IMAGE_REQUEST = 111;
private int STORAGE_PERMISSION_CODE = 1;
private StorageReference mStorageRef;
private DatabaseReference mDatabaseRef;
FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adduser);
        etusername= findViewById(R.id.etusername);
        etuserApplicationNo=findViewById(R.id.etuserApplicationNo_);
        etuserDOA=findViewById(R.id.etuserDOA);
        etuseradress=findViewById(R.id.etuseradress);
        etuserzip=findViewById(R.id.etuserzip);
        etuserDOB=findViewById(R.id.etuserDOB);
        etusernationality=findViewById(R.id.etusernationality);
        etuserphoneno=findViewById(R.id.etuserphoneno);
        etuserephoneno=findViewById(R.id.etuserephoneno);
        etdivrno=findViewById(R.id.etdivrno);
        etsemester=findViewById(R.id.etsemester);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        etcpassword=findViewById(R.id.etcpassword);
        etuserselect=findViewById(R.id.etuserselect);
        etuseryear=findViewById(R.id.etuseryear);
        etuserbranch=findViewById(R.id.etuserbranch);
        etusergender=findViewById(R.id.etusergender);
        etuserstate=findViewById(R.id.etuserstate);
        userprofileimg=findViewById(R.id.userprofileimg);
        signup=findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("College");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("College");


        final ArrayList<String> t = new ArrayList<>();
        t.add("-- User_Type --");
        t.add("Admin");
        t.add("Teacher");
        t.add("Student");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinnerlayout, t){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                 else
                    return true;
            }
        };
        etuserselect.setAdapter(arrayAdapter);


        final ArrayList<String> t1 = new ArrayList<>();
        t1.add("-- Year_Of_Education --");
        t1.add("FE");
        t1.add("SE");
        t1.add("TE");
        t1.add("BE");
        t1.add("-");
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.spinnerlayout, t1){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        etuseryear.setAdapter(arrayAdapter1);


        final ArrayList<String> t2 = new ArrayList<>();
        t2.add("-- Branch --");
        t2.add("COMP");
        t2.add("ELECTRONIC");
        t2.add("EXTC");
        t2.add("MECH");
        t2.add("IT");
        t2.add("CIVIL");
        t2.add("-");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.spinnerlayout, t2){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        etuserbranch.setAdapter(arrayAdapter2);


        final ArrayList<String> t3 = new ArrayList<>();
        t3.add("-- Gender --");
        t3.add("MALE");
        t3.add("FEMALE");
        t3.add("OTHER");
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.spinnerlayout, t3){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        etusergender.setAdapter(arrayAdapter3);


        final ArrayList<String> t4 = new ArrayList<>();
        t4.add("-- STATE --");
        t4.add("MAHARASHTRA");
        ArrayAdapter arrayAdapter4 = new ArrayAdapter(this, R.layout.spinnerlayout, t4){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        etuserstate.setAdapter(arrayAdapter4);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etusername.getText().toString().trim();
                final String applicationno = etuserApplicationNo.getText().toString().trim();
                final String doa = etuserDOA.getText().toString().trim();
                final String address = etuseradress.getText().toString().trim();
                final String zip = etuserzip.getText().toString().trim();
                final String state = etuserstate.getSelectedItem().toString().trim();
                final String dob = etuserDOB.getText().toString().trim();
                final String gender = etusergender.getSelectedItem().toString().trim();
                final String nationallity = etusernationality.getText().toString().trim();
                final String phoneno = etuserphoneno.getText().toString().trim();
                final String ephone = etuserephoneno.getText().toString().trim();
                final String select = etuserselect.getSelectedItem().toString().trim();
                final String branch = etuserbranch.getSelectedItem().toString().trim();
                final String year = etuseryear.getSelectedItem().toString().trim();
                final String divrollno = etdivrno.getText().toString().trim();
                final String semester = etsemester.getText().toString().trim();
                final String email = etemail.getText().toString().trim();
                final String password = etpassword.getText().toString().trim();
                final String cpassword = etcpassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    etusername.setError("Please Enter user name");
                    etusername.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(applicationno)){
                    etuserApplicationNo.setError("Please Enter user Application no.");
                    etuserApplicationNo.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(doa)){
                    etuserDOA.setError("Please Enter user DOA");
                    etuserDOA.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(address)){
                    etuseradress.setError("Please enter user address ");
                    etuseradress.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(zip)){
                    etuserzip.setError("Please enter user zip ");
                    etuserzip.requestFocus();
                    return;
                }
                if (state.equals("-- STATE --")) {
                    Toast.makeText(getApplicationContext(), "Select user state", Toast.LENGTH_SHORT).show();
                    etuserstate.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(dob)){
                    etuserDOB.setError("Please Enter user dob ");
                    etuserDOB.requestFocus();
                    return;
                }
               else if (gender.equals("-- GENDER --")) {
                    Toast.makeText(getApplicationContext(), "Select user gender", Toast.LENGTH_SHORT).show();
                    etusergender.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(nationallity)){
                    etusernationality.setError("Please enter user nationallity ");
                    etusernationality.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(phoneno)){
                    etuserphoneno.setError("Please enter user contact");
                    etuserphoneno.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(ephone)){
                    etuserephoneno.setError("Please enter guardian contact");
                    etuserephoneno.requestFocus();
                    return;
                }
                else if (select.equals("-- User_Type --")) {
                    Toast.makeText(getApplicationContext(), "Select user type", Toast.LENGTH_SHORT).show();
                    etuserselect.requestFocus();
                    return;
                }
                else if (branch.equals("-- Branch --")) {
                    Toast.makeText(getApplicationContext(), "Select user branch", Toast.LENGTH_SHORT).show();
                    etuserbranch.requestFocus();
                    return;
                }
                else if (year.equals("-- Year_Of_Education --")) {
                    Toast.makeText(getApplicationContext(), "Select user year of education", Toast.LENGTH_SHORT).show();
                    etuseryear.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(divrollno)){
                    etdivrno.setError("Enter div and rollNo");
                    etdivrno.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(semester)){
                    etsemester.setError("enter semester");
                    etsemester.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(email)){
                    etemail.setError("Enter the email ");
                    etemail.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    etpassword.setError("enter the password");
                    etpassword.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(cpassword)){
                    etcpassword.setError("Enter the password to verify");
                    etcpassword.requestFocus();
                    return;
                }
                else if (phoneno.length() < 10) {
                    etuserphoneno.setError("Please Check your contant no.");
                    etuserphoneno.requestFocus();
                    return;
                }
                else if (ephone.length() < 10) {
                    etcpassword.setError("Please Check your contant no.");
                    etcpassword.requestFocus();
                    return;
                }
                else if (password.length() < 6) {
                    etpassword.setError("ENTERED PASSWORD HAS LESS THAN 6 CHARACTER");
                    etpassword.requestFocus();
                    return;
                }
                else if (cpassword.length() < 6) {
                    etcpassword.setError("ENTERED PASSWORD HAS LESS THAN 6 CHARACTER");
                    etcpassword.requestFocus();
                    return;
                }
                else if(!password.contentEquals(cpassword)){
                    etcpassword.setError("Entered password is wrong ");
                    etcpassword.requestFocus();
                    return;
                }
                else
                    {
                        final AlertDialog.Builder pd = new AlertDialog.Builder(adduser.this);
                        View ew = LayoutInflater.from(getBaseContext()).inflate(R.layout.progressalert,null);
                        final AlertDialog progressDialog =  pd.setView(ew).create();
                        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "ERROR Please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.show();
                            if (imageUri != null) {
                                StorageReference fileReference = mStorageRef.child(email+".jpeg");
                                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        College upload = new College(name, applicationno, doa, address, zip, state, dob, gender,
                                                nationallity, phoneno, ephone, select, branch, year, divrollno, semester, email, password);
                                        String uploadId = mDatabaseRef.push().getKey();
                                        mDatabaseRef.child(uploadId).setValue(upload);
                                        progressDialog.dismiss();
                                        final Dialog d = new Dialog(adduser.this);
                                        d.setContentView(R.layout.done);
                                        d.show();
                                        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                finish();
                                            }
                                        });
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(adduser.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        }
                    }
                });
            }
            }
        });
        userprofileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosingimage();
            }
        });

    }

    private void choosingimage() {
        if (ContextCompat.checkSelfPermission(
                adduser.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent a = new Intent();
            a.setType("image/*");
            a.setAction(Intent.ACTION_GET_CONTENT);
            a.setAction(Intent.ACTION_PICK);
            startActivityForResult(a, PICK_IMAGE_REQUEST);
        }
        else
            {
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                View customview = LayoutInflater.from(getBaseContext()).inflate(R.layout.gallerypermissonalert,null);
                AlertDialog alertDialog = builder.setView(customview).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(adduser.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode== RESULT_OK && data != null && data.getData() != null){
         imageUri =data.getData();
         userprofileimg.setImageURI(imageUri);
        }

    }
}

