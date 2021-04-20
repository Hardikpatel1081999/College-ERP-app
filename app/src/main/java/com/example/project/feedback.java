package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedback extends AppCompatActivity {
RatingBar ratingBar;
EditText comment;
Button Submit;
String rb = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ratingBar = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.comment);
        Submit = findViewById(R.id.feedback);

        final DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("Feedback");

        Intent intent=getIntent();
        final String e = intent.getStringExtra("str1");
        final String n = intent.getStringExtra("str2");
        final String t = intent.getStringExtra("str3");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rb = String.valueOf(rating);
            }

        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb.equals("0.0")){
                    Toast.makeText(feedback.this," Please Rate Us ",Toast.LENGTH_LONG).show();
                }
                else{
                    feedbackdata feedbackdata = new feedbackdata(e,n,t, comment.getText().toString(),rb);
                    String uploadId = mdatabaseRef.push().getKey();
                    mdatabaseRef.child(uploadId).setValue(feedbackdata);
                    final Dialog d = new Dialog(feedback.this);
                    d.setContentView(R.layout.thankyoudialog);
                    d.show();
                    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                }
            }
        });
    }
}