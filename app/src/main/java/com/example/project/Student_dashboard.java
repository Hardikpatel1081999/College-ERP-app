package com.example.project;

import androidx.annotation.NonNull;
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

public class Student_dashboard extends AppCompatActivity {
    Toolbar toolbar2;
    CardView student_timetable,student_notice,student_book,student_feedback,student_quiztest,student_chat,student_attendv;
    CircleImageView studentprofileimage;
    TextView studentdata,studentname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        studentprofileimage = findViewById(R.id.studentimage);
        studentdata= findViewById(R.id.studentdata);
        student_book = findViewById(R.id.student_book);
        student_timetable =findViewById(R.id.student_timetable);
        student_notice = findViewById(R.id.student_notice);
        studentname= findViewById(R.id.studentname);
        student_feedback = findViewById(R.id.student_feedback);
        toolbar2 =findViewById(R.id.student_toolbar);
        student_quiztest = findViewById(R.id.student_test);
        student_chat=findViewById(R.id.student_chat);
        student_attendv = findViewById(R.id.student_attendv);
        setSupportActionBar(toolbar2);

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();
        final DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");

        student_attendv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            String b = ds.child("branch").getValue().toString();
                            String y= ds.child("year").getValue().toString();
                            String d= ds.child("divrollno").getValue().toString();
                            String x = String.valueOf(d.charAt(0));
                            Intent a = new Intent(Student_dashboard.this, student_attendanceview.class);
                            a.putExtra("str1",b);
                            a.putExtra("str2",y);
                            a.putExtra("str3",x);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Student_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        student_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            String e = ds.child("email").getValue().toString();
                            String n = ds.child("name").getValue().toString();
                            String t = ds.child("select").getValue().toString();
                            Intent a = new Intent(Student_dashboard.this,feedback.class);
                            a.putExtra("str1",e);
                            a.putExtra("str2",n);
                            a.putExtra("str3",t);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Student_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        student_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            String b = ds.child("branch").getValue().toString();
                            String y= ds.child("year").getValue().toString();
                            String d= ds.child("divrollno").getValue().toString();
                           String x = String.valueOf(d.charAt(0));
                            Intent a = new Intent(Student_dashboard.this, student_book.class);
                            a.putExtra("str1",b);
                            a.putExtra("str2",y);
                            a.putExtra("str3",x);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Student_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        student_quiztest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            String b = ds.child("branch").getValue().toString();
                            String y= ds.child("year").getValue().toString();
                            String d= ds.child("divrollno").getValue().toString();
                            String x = String.valueOf(d.charAt(0));
                            Intent a = new Intent(Student_dashboard.this, com.example.project.student_quiztest.class);
                            a.putExtra("b",b);
                            a.putExtra("y",y);
                            a.putExtra("d",x);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Student_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        student_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Student_dashboard.this,Student_timetable.class);
                startActivity(a);
            }
        });
        student_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r =user.getEmail();
                Intent a = new Intent(Student_dashboard.this,student_chat_1.class);
                a.putExtra("userid",r);
                startActivity(a);
            }
        });
        student_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Student_dashboard.this,student_notice.class);
                startActivity(a);
            }
        });
        Query q = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String n = " " + ds.child("name").getValue().toString();
                    String b = " " + ds.child("branch").getValue().toString();
                    String year = " " + ds.child("year").getValue().toString();
                    String div = " " + ds.child("divrollno").getValue().toString();

                    String c = b+"-"+year+"/"+div;
                    try{
                        studentname.setText(n);
                        studentdata.setText(c);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                studentprofileimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Student_dashboard.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Student_dashboard.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Student_dashboard.this, "data present", Toast.LENGTH_SHORT).show();
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
                Intent x = new Intent(Student_dashboard.this, student_profileview.class);
                startActivity(x);
                return true;
            case R.id.item2:
                FirebaseAuth.getInstance().signOut();
                Intent y = new Intent(Student_dashboard.this, login.class);
                startActivity(y);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}