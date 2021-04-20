package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.startActivity;

public class fuseradaptor extends RecyclerView.Adapter<fuseradaptor.Viewholder> {
    public Context c;
    public ArrayList<College> list;
     String theLastMsg;
    DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("College");
    StorageReference sref= FirebaseStorage.getInstance().getReference().child("College") ;
    private boolean ischat;
    public fuseradaptor(Context context, ArrayList<College> list, boolean ischat)
    {
        c = context;
        this.list =list;
        this.ischat = ischat;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userdesign,parent,false);
        return new fuseradaptor.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        final College college = list.get(position);
        holder.infoname.setText(college.getName());
        StorageReference storageReference = sref.child(college.getEmail()+".jpeg");
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
                        Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =new Intent(c,msg.class);
                a.putExtra("receivername",college.getName());
                a.putExtra("receiverid",college.getEmail());
                c.startActivity(a);
            }
        });
        if (ischat){
            theLastMsg = "default";
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Chat chat = snapshot.getValue(Chat.class);
                        if (firebaseUser != null && chat != null) {
                            if (chat.getMsgreceiver().equals(firebaseUser.getEmail()) && chat.getMsgsender().equals(college.getEmail()) ||
                                    chat.getMsgreceiver().equals(college.getEmail()) && chat.getMsgsender().equals(firebaseUser.getEmail())) {
                                theLastMsg = chat.getMsg();
                            }
                        }
                    }
                    switch (theLastMsg){
                        case  "default":
                            holder.lmsg.setText("No Message");
                            break;

                        default:
                            holder.lmsg.setText(theLastMsg);
                            break;
                    }
                    theLastMsg = "default";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(c,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        } else {
            holder.lmsg.setText(" ");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView infoname,lmsg;
        public CircleImageView infoimage;
        public RelativeLayout cardView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.pcard);
            infoname= itemView.findViewById(R.id.scusername);
            lmsg= itemView.findViewById(R.id.last_msg);
            infoimage = itemView.findViewById(R.id.profile_image);
        }
    }
}
