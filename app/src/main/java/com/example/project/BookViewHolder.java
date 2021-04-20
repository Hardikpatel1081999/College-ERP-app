package com.example.project;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookViewHolder extends RecyclerView.ViewHolder{
    TextView book_name;
    TextView book_type;
    CardView book_card;
    ImageButton book_delete;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
book_name = itemView.findViewById(R.id.tvnoticename);
book_type = itemView.findViewById(R.id.tvnoticetype);
book_delete =itemView.findViewById(R.id.noticecarddelete);
book_card = itemView.findViewById(R.id.noticecard);
    }
}
