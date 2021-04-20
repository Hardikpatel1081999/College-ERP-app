package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QviewAdaptor extends RecyclerView.Adapter<QviewAdaptor.viewAdaptorViewHolder> {
    public Context c;
    public ArrayList<Quiz> arrylist;

    public QviewAdaptor(Context context, ArrayList<Quiz> arrylist)
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforslist,parent,false);
        return new QviewAdaptor.viewAdaptorViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, int position) {
        final Quiz quiz = arrylist.get(position);
        holder.nbsubname.setText(quiz.getSname());
        holder.ncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent=new Intent(c,quizchaplist.class);
                setIntent.putExtra("title",holder.nbsubname.getText().toString());
                setIntent.putExtra("sets",quiz.getChapno());
                c.startActivity(setIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrylist.size();
    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder {
        public TextView nbsubname;
        public CardView ncard;
        public viewAdaptorViewHolder(@NonNull View itemView) {
                super(itemView);
            nbsubname = itemView.findViewById(R.id.subxname);
            ncard  = itemView.findViewById(R.id.ssubcard);
            }
        }
    }
