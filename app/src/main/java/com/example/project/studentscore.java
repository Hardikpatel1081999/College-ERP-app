package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class studentscore extends AppCompatActivity {
TextView scorescored,total;
private Button ssdone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentscore);
        scorescored = findViewById(R.id.scorescored);
        total= findViewById(R.id.total);
        ssdone = findViewById(R.id.donebtn);

        scorescored.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("OUT OF "+String.valueOf(getIntent().getIntExtra("total",0)));

        ssdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });
    }
}