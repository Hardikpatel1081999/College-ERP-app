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

public class Teacher_dashboard extends AppCompatActivity {
    Toolbar toolbar1;
    CardView studentview,teachertimewtable,teacher_notice,teacher_book,expenditure,teacher_feedback,quizl,teacher_chat,teacher_attendance;
    CircleImageView tprofileimage;
    TextView tdata,tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        tprofileimage = findViewById(R.id.teacherimage);
        tdata= findViewById(R.id.teacherbranch);
        tname= findViewById(R.id.teachername);
        studentview = findViewById(R.id.studentview);
        teacher_notice = findViewById(R.id.teacher_notice);
        teacher_book = findViewById(R.id.teacherbook);
        teachertimewtable = findViewById(R.id.timetableview);
        expenditure = findViewById(R.id.expenditure);
        teacher_feedback = findViewById(R.id.teacher_feedback);
        teacher_chat = findViewById(R.id.teacher_chat);
        quizl = findViewById(R.id.quizl);
        toolbar1 =findViewById(R.id.teacher_toolbar);
        teacher_attendance= findViewById(R.id.attendance);
        setSupportActionBar(toolbar1);

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();
        final DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");
        teachertimewtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Teacher_dashboard.this, teacher_timetable.class);
                startActivity(a);
            }
        });
        teacher_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Teacher_dashboard.this, teacher_notice.class);
                startActivity(a);
            }
        });
        teacher_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Teacher_dashboard.this, teacher_chat_1.class);
                startActivity(a);
            }
        });
        quizl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Teacher_dashboard.this, teacher_quiz.class);
                startActivity(a);
            }
        });
        teacher_feedback.setOnClickListener(new View.OnClickListener() {
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
                            Intent a = new Intent(Teacher_dashboard.this,feedback.class);
                            a.putExtra("str1",e);
                            a.putExtra("str2",n);
                            a.putExtra("str3",t);

                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Teacher_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        teacher_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Teacher_dashboard.this, teacher_attendance.class);
                startActivity(a);
            }
        });

        studentview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            final String s = " " + ds.child("branch").getValue().toString();

                            Intent a = new Intent(Teacher_dashboard.this, teacher_getstudentata.class);
                            a.putExtra("str",s);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Teacher_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        teacher_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            final String sa = ds.child("email").getValue().toString();
                            Intent a = new Intent(Teacher_dashboard.this, teacher_book.class);
                            a.putExtra("str1",sa);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Teacher_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        expenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
                r.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()) {
                            final String sa = " " + ds.child("email").getValue().toString();

                            Intent a = new Intent(Teacher_dashboard.this, teacher_expenses.class);
                            a.putExtra("email",sa);
                            startActivity(a);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Teacher_dashboard.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




        Query t = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
        t.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    String n = " " + ds.child("name").getValue().toString();
                    String b = " " + ds.child("branch").getValue().toString();
                    String c = tdata.getText().toString()+" ("+b+")";
                    try{
                        tname.setText(n);
                        tdata.setText(c);
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference storageReference = firebaseStorage.getReference().child("College").child(ds.child("email").getValue().toString()+".jpeg");
                        storageReference.getBytes(2924 * 2924).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                tprofileimage.setImageBitmap(bitmap);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Teacher_dashboard.this, "not able to get the image", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(Teacher_dashboard.this, "No data present", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Teacher_dashboard.this, "data present", Toast.LENGTH_SHORT).show();
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
                Intent x = new Intent(Teacher_dashboard.this, Teacher_profileview.class);
                startActivity(x);
                return true;
            case R.id.item2:
                FirebaseAuth.getInstance().signOut();
                Intent y= new Intent(Teacher_dashboard.this, login.class);
                startActivity(y);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}