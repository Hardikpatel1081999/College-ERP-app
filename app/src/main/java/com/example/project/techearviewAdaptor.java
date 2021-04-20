package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URISyntaxException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.getIntent;

public class techearviewAdaptor extends RecyclerView.Adapter<techearviewAdaptor.viewAdaptorViewHolder> {

public Context c;
public  ArrayList<College> arrylist;
    public techearviewAdaptor(Context context, ArrayList<College> arrylist)
    {
        this.c = context;
        this.arrylist =arrylist;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public viewAdaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforteacher,parent,false);
        return new viewAdaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, int position) {
        College college = arrylist.get(position);
            String branch = college.getBranch();
            String bh = "Branch :"+branch+"-"+college.getYear()+"/"+college.getDivrollno()+"\t\t\t\t\t\t\t\t\t Sem :"+college.getSemester();
            String n = college.getEphone();
            String nu = "\t Contact : "+n ;
            holder.infoname.setText(" " +college.getName());
            holder.infobranch.setText(bh);
            holder.infoselect.setText(nu);
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference().child("College").child(college.getEmail()+".jpeg");
            storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.infoimage.setImageBitmap(bitmap);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });

       

    }

    @Override
    public int getItemCount() {
        return arrylist.size();

    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder{
        public TextView infoname,infobranch,infoselect;
        public CircleImageView infoimage;
        public viewAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            infoname= itemView.findViewById(R.id.tvuserinfol);
            infobranch= itemView.findViewById(R.id.tvuserinfob);
            infoselect= itemView.findViewById(R.id.tvuserinfos);
            infoimage = itemView.findViewById(R.id.userinfoimg);
        }
    }
}
