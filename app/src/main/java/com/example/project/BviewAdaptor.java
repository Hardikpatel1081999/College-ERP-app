package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BviewAdaptor extends RecyclerView.Adapter<BviewAdaptor.viewAdaptorViewHolder> {

public Context c;
public  ArrayList<Book> arrylist;
    public BviewAdaptor(Context context, ArrayList<Book> arrylist)
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforstudentandteachernotice,parent,false);
        return new viewAdaptorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, int position) {
        final Book book = arrylist.get(position);
            holder.nbookname.setText(book.getBname()+"("+book.getBtname()+")");
            holder.nbooktopic.setText(book.getBtopic());
             holder.ncard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent();
                    intent.setData(Uri.parse(book.getUrl()));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrylist.size();

    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder{
        public TextView nbooktopic,nbookname;
        public CardView ncard;
        public viewAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            nbookname = itemView.findViewById(R.id.sbookname);
            nbooktopic = itemView.findViewById(R.id.snoticename);
            ncard  = itemView.findViewById(R.id.snoticecard);
        }
    }
}
