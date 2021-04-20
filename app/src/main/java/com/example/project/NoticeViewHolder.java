package com.example.project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeViewHolder extends RecyclerView.ViewHolder{
    TextView notice_name;
    TextView notice_type;
    CardView notice_card;
    ImageButton notice_delete;
    public NoticeViewHolder(@NonNull View itemView) {
        super(itemView);
notice_name = itemView.findViewById(R.id.tvnoticename);
notice_type = itemView.findViewById(R.id.tvnoticetype);
notice_delete =itemView.findViewById(R.id.noticecarddelete);
notice_card = itemView.findViewById(R.id.noticecard);
    }
}
