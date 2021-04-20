package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

public class student_Qans extends AppCompatActivity {
    private TextView squestionview,snoofquestion;
    private LinearLayout optionscontainer;
    private Button snext;
    private int count=0;
    private List<Quizquestion> qqlist;
    private int position=0;
    private int score=0;
    private String category;
    private int setNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__qans);
        squestionview = findViewById(R.id.squestionview);
        snoofquestion = findViewById(R.id.snoofquestion);
        optionscontainer = findViewById(R.id.optionscontainer);
        snext  = findViewById(R.id.snext);
        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);
        final AlertDialog.Builder pd = new AlertDialog.Builder(student_Qans.this);
        View ew = LayoutInflater.from(getBaseContext()).inflate(R.layout.progressalert,null);
        final AlertDialog progressDialog =  pd.setView(ew).create();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        qqlist= new ArrayList<>();
        progressDialog.show();

        FirebaseAuth mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
         DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");

        Query r = mdatabaseRef.orderByChild("email").equalTo(user.getEmail());
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()) {
                    String b = ds.child("branch").getValue().toString();
                    String y= ds.child("year").getValue().toString();
                    String d= ds.child("divrollno").getValue().toString();
                    String x = String.valueOf(d.charAt(0));
                    FirebaseDatabase.getInstance().getReference("Quiz").child(b).child(y).child("SETS_OF"+category+"_"+x).child(category).child("questions").orderByChild("chapno").equalTo(String.valueOf(setNo)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                qqlist.add(dataSnapshot.getValue(Quizquestion.class));
                            }
                            if(qqlist.size() > 0){

                                for(int i=0;i<4;i++){
                                    optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                        @Override
                                        public void onClick(View v) {
                                            checkAnswer((Button)v);
                                        }

                                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                        private void checkAnswer(Button v) {
                                            enableOption(false);
                                            snext.setEnabled(true);
                                            snext.setAlpha(1);
                                            if(v.getText().toString().equals(qqlist.get(position).getCorrectans())){
                                                score++;
                                                v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#a8ffae")));
                                            }else{
                                                v.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff5947")));
                                                Button correctoption=(Button)optionscontainer.findViewWithTag(qqlist.get(position). getCorrectans());
                                                correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#a8ffae")));
                                            }

                                        }
                                    });
                                }

                                playAnim(squestionview,0,qqlist.get(position).getQues());

                                snext.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onClick(View v) {
                                        snext.setEnabled(false);
                                        snext.setAlpha(0.7f);
                                        enableOption(true);
                                        position++;
                                        if(position==qqlist.size()){
                                            Intent scoreIntent=new Intent(student_Qans.this,studentscore.class);
                                            scoreIntent.putExtra("score",score);
                                            scoreIntent.putExtra("total",qqlist.size());
                                            startActivity(scoreIntent);
                                            finish();
                                            return;
                                        }
                                        count=0;
                                        playAnim(squestionview,0,qqlist.get(position).getQues());
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();
                                final Dialog d = new Dialog(student_Qans.this);
                                d.setContentView(R.layout.noqu);
                                d.show();
                                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        finish();
                                    }
                                });
                            }
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(student_Qans.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(student_Qans.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean enable) {
        for(int i=0;i<4;i++){
            optionscontainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionscontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#efefef")));
            }
        }

    }

    private  void playAnim(final View view,final int value,final String data){
        view.animate().alpha(value).scaleY(value).scaleX(value).setDuration(300).setStartDelay(90).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value==0 && count<4){
                    String option="";
                    if(count==0){
                        option=qqlist.get(position).getOp1();
                    }else if(count==1){
                        option=qqlist.get(position).getOp2();
                    }else if(count==2){
                        option=qqlist.get(position).getOp3();
                    }else if(count==3){
                        option=qqlist.get(position).getOp4();
                    }
                    playAnim(optionscontainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(value==0){
                    try{
                        ((TextView)view).setText(data);

                        snoofquestion.setText(position+1+"/"+qqlist.size());
                    }catch(ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}