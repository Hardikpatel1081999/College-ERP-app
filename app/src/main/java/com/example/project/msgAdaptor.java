package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class msgAdaptor extends RecyclerView.Adapter<msgAdaptor.Viewholder> {
    public static  final int MSG_ON_LEFT = 0;
    public static  final int MSG_ON_RIGHT = 1;

    public Context c;
    public List<Chat> xchat;
    public String imgurl;
    StorageReference sref= FirebaseStorage.getInstance().getReference().child("College") ;

    public msgAdaptor(Context context, List<Chat> xchat, String imageurl)
    {
        c = context;
        this.xchat =xchat;
        this.imgurl = imageurl;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public msgAdaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_ON_RIGHT) {
            View view = LayoutInflater.from(c).inflate(R.layout.right_msg_look, parent, false);
            return new msgAdaptor.Viewholder(view);
        } else {
            View view = LayoutInflater.from(c).inflate(R.layout.left_msg_look, parent, false);
            return new msgAdaptor.Viewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final msgAdaptor.Viewholder holder, int position) {
        final Chat cc = xchat.get(position);
        holder.show_message.setText(cc.getMsg());
        StorageReference storageReference = sref.child(imgurl+".jpeg");
        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.profile_image.setImageBitmap(bitmap);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return xchat.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (xchat.get(position).getMsgsender().equals(firebaseUser.getEmail())){
            return MSG_ON_RIGHT;
        } else {
            return MSG_ON_LEFT;
        }
    }
}
