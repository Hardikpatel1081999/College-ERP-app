package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.LogRecord;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceAdaptor extends RecyclerView.Adapter<AttendanceAdaptor.AttendanceadaptorViewHolder> {
    public Context c;
    public Integer i = 0;
    public List<String> lists = new ArrayList<String>();
    public List<String> lists1 = new ArrayList<String>();

    public List<String> listx = new ArrayList<String>();
    public List<String> listx1 = new ArrayList<String>();
    public ArrayList<College> list;
    String btrim,ytrim1,ds,ctDate;
    public AttendanceAdaptor(Context context, ArrayList<College> list, String btrim, String ds, String ytrim1, String ctDate)
    {
        c =context;
        this.list =list;
        this.btrim =btrim;
        this.ytrim1 =ytrim1;
        this.ds =ds;
        this.ctDate =ctDate;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public AttendanceadaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendancelayview,parent,false);
        return new AttendanceadaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttendanceadaptorViewHolder holder, int position) {
        final College college = list.get(position);
        holder.sbtnnext.setVisibility(View.GONE);
        if(position == list.size() -1){
            holder.sbtnnext.setVisibility(View.VISIBLE);
        }
        holder.Studentname.setText(college.getName());
        lists.add(" ");
        lists1.add(" ");
        listx.add(college.getName());
        listx1.add(college.getEmail());
        holder.Studentname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      if(i == 0) {
                          holder.Studentname.setChecked(true);
                          lists.add(college.getName());
                          lists1.add(college.getEmail());
                          listx.remove(college.getName());
                          listx1.remove(college.getEmail());
                      }
                      else if(i == 1){
                          holder.Studentname.setChecked(false);
                          lists.remove(college.getName());
                          lists1.remove(college.getEmail());
                      }
                      else {
                          i=0;
                          holder.Studentname.setChecked(true);
                          lists.add(college.getName());
                          lists1.add(college.getEmail());
                          listx.remove(college.getName());
                          listx1.remove(college.getEmail());

                      }
                    }
                },50);
            }
        });
        lists.remove(" ");
        lists1.remove(" ");
        holder.sbtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(c,dt_attendance.class);
                intent.putExtra("length",lists.size());
                intent.putExtra("classb",btrim);
                intent.putExtra("cd",ctDate);
                intent.putExtra("clasd",ds );
                intent.putExtra("classy",ytrim1);
                intent.putExtra("studentpname", (Serializable) lists);
                intent.putExtra("studentemail", (Serializable) lists1);
                intent.putExtra("studentaname", (Serializable) listx);
                intent.putExtra("studentaemail", (Serializable) listx1);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AttendanceadaptorViewHolder extends RecyclerView.ViewHolder{
        public CheckedTextView Studentname;
        public Button sbtnnext;
        public AttendanceadaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            Studentname= itemView.findViewById(R.id.studentname);
            sbtnnext = itemView.findViewById(R.id.sbtnnext);
        }
    }
}
