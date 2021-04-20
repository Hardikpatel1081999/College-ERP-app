package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class STviewAdaptor extends RecyclerView.Adapter<STviewAdaptor.viewAdaptorViewHolder> {

public Context c;
public  ArrayList<Notice> arrylist;
    public STviewAdaptor(Context context, ArrayList<Notice> arrylist)
    {
        this.c = c;
        this.arrylist =arrylist;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public viewAdaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforstudentandteachernotice,parent,false);
        return new viewAdaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, int position) {
        final Notice notice = arrylist.get(position);
            String nname = notice.getNname();
            holder.nname.setText(nname);
            holder.ncard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent();
                    intent.setData(Uri.parse(notice.getUrl()));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrylist.size();

    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder{
        public TextView nname;
        public CardView ncard;
        public viewAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            nname = itemView.findViewById(R.id.snoticename);
            ncard  = itemView.findViewById(R.id.snoticecard);
        }
    }
}
