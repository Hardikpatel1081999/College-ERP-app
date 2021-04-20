package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class student_attendanceview extends AppCompatActivity {
    EditText datesearch;
    ImageButton dbtns;
    GridLayout scr;
    TextView slec1,slec2,slec3,slec4,slec5,slec6,slec7,slec8,slec9,stusatus1,stusatus2,stusatus3,stusatus4,stusatus5,stusatus6,stusatus7,stusatus8,stusatus9,p1,p2,p3,p4,p5,p6,p7,p8,p9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendanceview);
        datesearch = findViewById(R.id.datesearch);
        scr = findViewById(R.id.scr);
        dbtns= findViewById(R.id.dbtns);
        slec1 = findViewById(R.id.slec1);
        slec2 = findViewById(R.id.slec2);
        slec3 = findViewById(R.id.slec3);
        slec4 = findViewById(R.id.slec4);
        slec5 = findViewById(R.id.slec5);
        slec6 = findViewById(R.id.slec51);
        slec7 = findViewById(R.id.slec6);
        slec8 = findViewById(R.id.slec7);
        slec9 = findViewById(R.id.slec8);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p51);
        p7 = findViewById(R.id.p6);
        p8 = findViewById(R.id.p7);
        p9 = findViewById(R.id.p8);
        stusatus1 = findViewById(R.id.stusatus1);
        stusatus2 = findViewById(R.id.stusatus2);
        stusatus3 = findViewById(R.id.stusatus3);
        stusatus4 = findViewById(R.id.stusatus4);
        stusatus5 = findViewById(R.id.stusatus5);
        stusatus6 = findViewById(R.id.stusatus51);
        stusatus7 = findViewById(R.id.stusatus6);
        stusatus8 = findViewById(R.id.stusatus7);
        stusatus9 = findViewById(R.id.stusatus8);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        final Calendar calendar = Calendar.getInstance();
        String currentDate = format.format(calendar.getTime());
        datesearch.setText(currentDate.replaceAll("/","-"));

        Intent intent=getIntent();
        final String b = intent.getStringExtra("str1");
        final String y = intent.getStringExtra("str2");
        final String d = intent.getStringExtra("str3");

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        final FirebaseUser user = mauth.getCurrentUser();

        datesearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scr.setVisibility(View.GONE);
                return false;
            }
        });
        final Dialog dx = new Dialog(student_attendanceview.this);
        dx.setContentView(R.layout.alpop);

        scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dx.show();
                dx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
        dbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Query databaseReference = FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                final String x = snapshot.child("day").getValue().toString();
                                scr.setVisibility(View.VISIBLE);
                                Query qq = FirebaseDatabase.getInstance().getReference("Time").orderByChild("ttype").equalTo("Student");
                                qq.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (final DataSnapshot ds : snapshot.getChildren()) {
                                            String xx1 = ds.child("tbranch").getValue().toString();
                                            String xx2 = ds.child("tclass").getValue().toString();
                                            String xx3 = ds.child("div").getValue().toString();
                                            String xr = ds.getRef().getKey();
                                            if (b.contentEquals(xx1) && y.contentEquals(xx2) && d.contentEquals(xx3)) {
                                                DatabaseReference der = FirebaseDatabase.getInstance().getReference("Time").child(xr).child(x);
                                                der.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String slec1x = snapshot.child("lec1").getValue().toString();
                                                        String slec2x = snapshot.child("lec2").getValue().toString();
                                                        String slec3x = snapshot.child("lec4").getValue().toString();
                                                        String slec4x = snapshot.child("lec5").getValue().toString();
                                                        String slec5x = snapshot.child("lec7").getValue().toString();
                                                        String slec6x = snapshot.child("lec8").getValue().toString();
                                                        String slec7x = snapshot.child("lec9").getValue().toString();
                                                        String slec8x = snapshot.child("lec10").getValue().toString();
                                                        String slec9x = snapshot.child("lec11").getValue().toString();
                                                        slec1.setText(slec1x);
                                                        slec2.setText(slec2x);
                                                        slec3.setText(slec3x);
                                                        slec4.setText(slec4x);
                                                        slec5.setText(slec5x);
                                                        slec6.setText(slec6x);
                                                        slec7.setText(slec7x);
                                                        slec8.setText(slec8x);
                                                        slec9.setText(slec9x);
                                                        final String[] timesloat = new String[] {
                                                                p1.getText().toString().toLowerCase(),
                                                                p2.getText().toString().toLowerCase(),
                                                                p3.getText().toString().toLowerCase(),
                                                                p4.getText().toString().toLowerCase(),
                                                                p5.getText().toString().toLowerCase(),
                                                                p6.getText().toString().toLowerCase(),
                                                                p7.getText().toString().toLowerCase(),
                                                                p8.getText().toString().toLowerCase(),
                                                                p9.getText().toString().toLowerCase()
                                                        };

                                                            DatabaseReference dx = FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[0]).child("PRESENT");
                                                            dx.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    for(DataSnapshot ds :snapshot.getChildren())
                                                                    {
                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                        {
                                                                            stusatus1.setText(" P ");
                                                                            stusatus1.setTextColor(Color.rgb(29,161,23));
                                                                        }
                                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[0]).child("ABSENT")
                                                                        .addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                for(DataSnapshot ds :snapshotx.getChildren())
                                                                                {
                                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                                    {
                                                                                        stusatus1.setText(" A ");
                                                                                        stusatus1.setTextColor(Color.rgb(229,67,9));
                                                                                    }
                                                                                }

                                                                            }
                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });

                                                                    }
                                                                }
                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                       FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[1]).child("PRESENT")
                                                       .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus2.setText(" P ");
                                                                        stusatus2.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[1]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus2.setText(" A ");
                                                                                            stusatus2.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                       FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[2]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus3.setText(" P ");
                                                                        stusatus3.setTextColor(Color.rgb(29,161,23));;
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[2]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus3.setText(" A ");
                                                                                            stusatus3.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[3]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus4.setText(" P ");
                                                                        stusatus4.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[3]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus4.setText(" A ");
                                                                                            stusatus4.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[4]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus5.setText(" P ");
                                                                        stusatus5.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[4]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus5.setText(" A ");
                                                                                            stusatus5.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                       FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[5]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus6.setText(" P ");
                                                                        stusatus6.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[5]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus6.setText(" A ");
                                                                                            stusatus6.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[6]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus7.setText(" P ");
                                                                        stusatus7.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[6]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus7.setText(" A ");
                                                                                            stusatus7.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[7]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus8.setText(" P ");
                                                                        stusatus8.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[7]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus8.setText(" A ");
                                                                                            stusatus8.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                                        FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[8]).child("PRESENT")
                                                        .addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for(DataSnapshot ds :snapshot.getChildren())
                                                                {
                                                                    String presentstudent = ds.child("stpemail").getValue().toString();
                                                                    if(presentstudent.contentEquals(user.getEmail()))
                                                                    {
                                                                        stusatus9.setText(" P ");
                                                                        stusatus9.setTextColor(Color.rgb(29,161,23));
                                                                    }
                                                                    FirebaseDatabase.getInstance().getReference("AttendanceData").child(b).child(y).child(d).child(datesearch.getText().toString()).child(timesloat[8]).child("ABSENT")
                                                                            .addValueEventListener(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot snapshotx) {
                                                                                    for(DataSnapshot ds :snapshotx.getChildren())
                                                                                    {
                                                                                        String presentstudent = ds.child("stpemail").getValue().toString();
                                                                                        if(presentstudent.contentEquals(user.getEmail()))
                                                                                        {
                                                                                            stusatus9.setText(" A ");
                                                                                            stusatus9.setTextColor(Color.rgb(229,67,9));
                                                                                        }
                                                                                    }

                                                                                }
                                                                                @Override
                                                                                public void onCancelled(@NonNull DatabaseError error) {
                                                                                    Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {
                                                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                scr.setVisibility(View.INVISIBLE);
                                final Dialog d = new Dialog(student_attendanceview.this);
                                d.setContentView(R.layout.noneat);
                                d.show();
                                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            }


                            }

                            @Override
                            public void onCancelled (@NonNull DatabaseError error){
                                Toast.makeText(student_attendanceview.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                         });


            }
        });
    }
}