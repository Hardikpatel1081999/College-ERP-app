package com.example.project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class chapgridAdapter extends BaseAdapter {
    private int chapcount=0;
    private String title;
    @Override
    public int getCount() {
        return chapcount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public chapgridAdapter(String sets, String title) {
        chapcount = Integer.parseInt(sets);
        this.title=title;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(convertView==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chaplistdesign,parent,false);
        }else {
            view=convertView;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent=new Intent(parent.getContext(),student_Qans.class);
                questionIntent.putExtra("category",title);
                questionIntent.putExtra("setNo",position+1);
                parent.getContext().startActivity(questionIntent);
            }
        });
        String no = String.valueOf(position+1);
        ((TextView)view.findViewById(R.id.tview)).setText("CHAPTER "+ no);
        return view;    }
}
