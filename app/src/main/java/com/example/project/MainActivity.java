package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {
    ImageView ivimg;
    TextView tvG;
    Animation animation,animation1;

    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivimg = findViewById(R.id.ivimg);
        tvG = findViewById(R.id.tvG);
        animation = AnimationUtils.loadAnimation(this,R.anim.a1);
        animation1 = AnimationUtils.loadAnimation(this,R.anim.a2);
        ivimg.startAnimation(animation);
        tvG.startAnimation(animation1);

    }
    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            DatabaseReference dref = FirebaseDatabase.getInstance().getReference("College");
            Query q = dref.orderByChild("email").equalTo(user.getEmail());
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String type = ds.child("select").getValue().toString();
                        if(type.contentEquals("Admin")){
                            Intent i = new Intent(MainActivity.this,Admin_dashboard.class);
                            startActivity(i);
                        }
                        if(type.contentEquals("Teacher")){
                            Intent i = new Intent(MainActivity.this,Teacher_dashboard.class);
                            startActivity(i);

                        }
                        if(type.contentEquals("Student")){
                            Intent i = new Intent(MainActivity.this,Student_dashboard.class);
                            startActivity(i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(MainActivity.this, login.class));
                }
            }, secondsDelayed * 2550);
        }

    }
}