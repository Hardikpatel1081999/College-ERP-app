package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public  class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.viewAdaptorViewHolder> {
    public Context c;
    public ArrayList<expense> arrylist;
    public ExpenseAdapter(Context context, ArrayList<expense> arrylist)
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
    public viewAdaptorViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforteacherexpense,parent,false);
       return  new viewAdaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, int position) {
        final expense exe = arrylist.get(position);
        holder.infoa.setText("AMOUNT : "+exe.getAmount()+"/-");
        holder.infop.setText("Payment Mode : "+exe.getPaytype());
        holder.infosr.setText("Sr no."+exe.getSr());
        holder.infod.setText("Date : "+exe.getCdate());
        holder.clickcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setData(Uri.parse(exe.getUrl()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrylist.size();
    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder {
        public TextView infoa,infop,infosr,infod;
        public CardView clickcard;
        public viewAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            clickcard= itemView.findViewById(R.id.clickcard);
            infoa= itemView.findViewById(R.id.tvuseramount);
            infop= itemView.findViewById(R.id.tvuserpmode);
            infosr= itemView.findViewById(R.id.tvusersr);
            infod = itemView.findViewById(R.id.tvuserdate);
        }
    }
}