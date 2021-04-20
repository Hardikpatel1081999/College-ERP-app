package com.example.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class finalpstudentAdaptor extends RecyclerView.Adapter<finalpstudentAdaptor.finalpstudentadaptorViewHolder> {

    public Context c;
    public Integer i = 0;
    public ArrayList<String> list,liste,listx,listxe;
    public String classb,classy,classd,tatime,tadate;
    public RecyclerView fslist;
    public finalpstudentAdaptor(Context context, ArrayList<String> list, ArrayList<String> liste, ArrayList<String> listx, ArrayList<String> listxe, String classb, String classy, String classd, RecyclerView fslist, String trim, String tadate)
    {
        c =context;
        this.list =list;
        this.liste =liste;
        this.listx =listx;
        this.listxe =listxe;
        this.classb =classb;
        this.classy =classy;
        this.classd =classd;
        this.fslist = fslist;
        tatime = trim;
        this.tadate = tadate;
    }

    @NonNull
    @Override
    public finalpstudentAdaptor.finalpstudentadaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.finalpslay,parent,false);
        return new finalpstudentadaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final finalpstudentAdaptor.finalpstudentadaptorViewHolder holder, final int position) {

        final String pstudentt = list.get(position);
        final String pstudentemail = liste.get(position);

        final AlertDialog.Builder alert = new AlertDialog.Builder(c);
        View view = LayoutInflater.from(c).inflate(R.layout.deletedialog,null);
        final AlertDialog alertDialog = alert.setView(view)
                .setPositiveButton("Yes Delete it !",null)
                .setNegativeButton("Cancel",null )
                .create();

        holder.sbtndone.setVisibility(View.GONE);
        if(position == list.size() -1){
            holder.sbtndone.setVisibility(View.VISIBLE);
        }
        holder.pstudent.setText(pstudentt);
        if(list.size() == 1){
            holder.pibtn.setVisibility(View.GONE);
        }
        holder.pibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                final Button p = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button n = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        list.remove(pstudentt);
                        liste.remove(pstudentemail);
                        listx.add(pstudentt);
                        listxe.add(pstudentemail);
                        finalpstudentAdaptor finalpstudentAdaptor = new finalpstudentAdaptor(c,list,liste, listx, listxe, classb,classy,classd, fslist, tatime,tadate);
                        fslist.setAdapter(finalpstudentAdaptor);
                    }
                });

                n.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        final AlertDialog.Builder alertx = new AlertDialog.Builder(c);
        View viewxx = LayoutInflater.from(c).inflate(R.layout.progressalert,null);
        final AlertDialog alertDialogx = alertx.setView(viewxx).create();
        alertDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        holder.sbtndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogx.show();
                FirebaseAuth mauth = FirebaseAuth.getInstance();
                FirebaseUser user = mauth.getCurrentUser();
                final String teacheremail = user.getEmail();
                DatabaseReference mdatabaseRef = FirebaseDatabase.getInstance().getReference("College");
                String dd = tadate;
                final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("AttendanceData").child(classb).child(classy).child(classd).child(dd);


                Query q = mdatabaseRef.orderByChild("email").equalTo(teacheremail);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds :snapshot.getChildren())
                        {
                            String teachername = ds.child("name").getValue().toString().trim();
                            for(i = 0;i<list.size();i++)
                            {
                                String uploadId = dref.push().getKey();
                                AttendanceData attendanceData = new AttendanceData(liste.get(i),list.get(i),teachername,teacheremail);
                                dref.child(tatime).child("PRESENT").child(uploadId).setValue(attendanceData);
                            }
                            for(i = 0;i<listx.size();i++) {
                                String uploadIdx = dref.push().getKey();
                                AttendanceData attendanceDatax = new AttendanceData(listxe.get(i), listx.get(i), teachername, teacheremail);
                                dref.child(tatime).child("ABSENT").child(uploadIdx).setValue(attendanceDatax);
                                final Calendar calendar = Calendar.getInstance();
                                final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("AttendanceData").child(classb).child(classy).child(classd).child(tadate);
                                int x = calendar.get(Calendar.DAY_OF_WEEK);
                                String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
                                final String taday = days[x-1];
                                dref.child("day").setValue(taday);
                                alertDialogx.dismiss();
                                Toast.makeText(c,"Attendance Done ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(c,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class finalpstudentadaptorViewHolder extends RecyclerView.ViewHolder {
        public TextView pstudent;
        public ImageButton pibtn;
        public Button sbtndone;
        public finalpstudentadaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            pstudent =itemView.findViewById(R.id.pstudent);
            pibtn = itemView.findViewById(R.id.pdelete);
            sbtndone = itemView.findViewById(R.id.sbtndone);

        }
    }
}
