package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class teacher_expenses extends AppCompatActivity {
ImageView ivproof,idate;
Spinner etpaymentmode;
EditText etamount,etdiscription,etsrno;
TextView etdate,alert;
Button submitebill,btnvieww;
private Uri imageUri;
private static final int PICK_IMAGE_REQUEST = 111,CICK_IMAGE_REQUEST = 112;;
private int STORAGE_PERMISSION_CODE = 1;
public  static final int RequestPermissionCode  = 1;
private StorageReference mStorageRef;
private DatabaseReference mDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_expenses);
        ivproof = findViewById(R.id.ivproof);
        etamount = findViewById(R.id.etamount);
        etsrno = findViewById(R.id.etsrno);
        etdate = findViewById(R.id.etdate);
        alert = findViewById(R.id.alert);
        idate =findViewById(R.id.isdate);
        etdiscription = findViewById(R.id.etdiscript);
        etpaymentmode = findViewById(R.id.etpaymode);
        submitebill = findViewById(R.id.btnsubmitbill);
        btnvieww= findViewById(R.id.btnvieww);
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference("expense");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("expense");

        final Calendar calendar = Calendar.getInstance();
        Intent intent=getIntent();
        final String j = intent.getStringExtra("email");
        final ArrayList<String> t = new ArrayList<>();
        t.add("-- Payment_Mode --");
        t.add(" Cash ");
        t.add(" Net Banking ");
        t.add(" Credit Card/Dedit Card ");
        t.add(" Digital app(Pytm / GooglePay) ");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinnerlayout, t){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0)
                    return false;
                else
                    return true;
            }
        };
        etpaymentmode.setAdapter(arrayAdapter);
        btnvieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(teacher_expenses.this,teacher_expenseview.class);
                intent.putExtra("str",j);
                startActivity(intent);
            }
        });
        idate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
                etdate.setText(currentDate);
            }
        });
        ivproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(teacher_expenses.this);
                dialog.setContentView(R.layout.cameragallerychoose);
                TextView camera = dialog.findViewById(R.id.camera);
                TextView gallery = dialog.findViewById(R.id.gallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        choosingimage();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        captureimage();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        submitebill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String srno = etsrno.getText().toString().trim();
                final String amont = etamount.getText().toString().trim();
                final String edate = etdate.getText().toString().trim();
                final String etpaymode = etpaymentmode.getSelectedItem().toString().trim();
                final String edis = etdiscription.getText().toString().trim();

                if (TextUtils.isEmpty(srno)) {
                    etsrno.setError("Please Enter the Srno. ");
                    etsrno.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(amont)) {
                    etamount.setError("Please Enter the amount");
                    etamount.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(edate)) {
                    etdate.setError("Please get the current date");
                    etdate.requestFocus();
                    return;
                }
                else if (etpaymode.equals("-- Payment_Mode --")) {
                    Toast.makeText(getApplicationContext(), "Select user branch", Toast.LENGTH_SHORT).show();
                    etpaymentmode.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(edis)) {
                    etdiscription.setError("Please write the discription");
                    etdiscription.requestFocus();
                    return;
                }
                else if (ivproof.getDrawable()== null) {
                    alert.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    final Query t = FirebaseDatabase.getInstance().getReference("College").orderByChild("email").equalTo(user.getEmail());
                    t.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds : snapshot.getChildren())
                            {
                                final String n =ds.child("name").getValue().toString();
                                final String p =ds.child("phoneno").getValue().toString();
                                final String b =ds.child("branch").getValue().toString();
                                final String e =ds.child("email").getValue().toString();

                                try
                                {
                                    if(imageUri != null){
                                        StorageReference fileReference = mStorageRef.child(e+srno+".jpeg");
                                        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                                while (!uri.isComplete());
                                                Uri url = uri.getResult();
                                                expense ee = new expense(srno,e,amont,edate,etpaymode,n,p,b,edis,url.toString());
                                                String uploadId = mDatabaseRef.push().getKey();
                                                mDatabaseRef.child(uploadId).setValue(ee);
                                                imageUri.equals("");
                                                Toast.makeText(teacher_expenses.this,"Done",Toast.LENGTH_LONG).show();

                                            }
                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(teacher_expenses.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else{
                                        ivproof.setDrawingCacheEnabled(true);
                                        ivproof.buildDrawingCache();
                                        Bitmap bitmap = ((BitmapDrawable) ivproof.getDrawable()).getBitmap();
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                        byte[] data = baos.toByteArray();
                                        UploadTask uploadTask = mStorageRef.child(e+srno+".jpeg").putBytes(data);
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                                while (!uri.isComplete());
                                                Uri url = uri.getResult();
                                                expense ee = new expense(srno,e,amont,edate,etpaymode,n,p,b,edis,url.toString());
                                                String uploadId = mDatabaseRef.push().getKey();
                                                mDatabaseRef.child(uploadId).setValue(ee);
                                                Toast.makeText(teacher_expenses.this,"Done",Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(teacher_expenses.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                                catch (Exception e1)
                                {
                                    Toast.makeText(teacher_expenses.this, "e()", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void choosingimage() {
        if (ContextCompat.checkSelfPermission(
                teacher_expenses.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
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
                    ActivityCompat.requestPermissions(teacher_expenses.this,
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

    private void captureimage() {
        if (ContextCompat.checkSelfPermission(teacher_expenses.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
          Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(cameraIntent,CICK_IMAGE_REQUEST);
        }
        else {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            View customview = LayoutInflater.from(getBaseContext()).inflate(R.layout.camerapermissonalert,null);
            AlertDialog alertDialog = builder.setView(customview).setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(teacher_expenses.this,new String[]{
                            Manifest.permission.CAMERA}, RequestPermissionCode);
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
         if(requestCode==CICK_IMAGE_REQUEST&&resultCode== RESULT_OK){
             Bitmap bitmap = (Bitmap) data.getExtras().get("data");
             ivproof.setBackgroundResource(R.drawable.c);
             ivproof.setImageBitmap(bitmap);

        }
        else if(requestCode==PICK_IMAGE_REQUEST&&resultCode== RESULT_OK && data != null && data.getData() != null){
            imageUri =data.getData();
            ivproof.setBackgroundResource(R.drawable.c);
            ivproof.setImageURI(imageUri);
        }
        else {
             Toast.makeText(teacher_expenses.this,"No Image selected", Toast.LENGTH_SHORT).show();
         }
    }
}