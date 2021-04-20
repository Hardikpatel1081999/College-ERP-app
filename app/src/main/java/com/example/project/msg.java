package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class msg extends AppCompatActivity {
TextView msguser;
ImageView senderuserimg;
ImageButton sendmsg;
EditText msgs;
RecyclerView mchatrecycler_view;
List<Chat> userchatlog;

StorageReference sref= FirebaseStorage.getInstance().getReference().child("College") ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        msguser = findViewById(R.id.msg_username);
        senderuserimg = findViewById(R.id.msg_profile_image);
        sendmsg = findViewById(R.id.btn_sendmsg);
        msgs = findViewById(R.id.text_sendmsg);
        mchatrecycler_view = findViewById(R.id.mrecycler_view);

        userchatlog = new ArrayList<>();
        mchatrecycler_view.setHasFixedSize(true);
        LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
        linear.setStackFromEnd(true);
        mchatrecycler_view.setLayoutManager(linear);

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent intent = getIntent();
        final String rrname = intent.getStringExtra("receivername");
        final String rrid = intent.getStringExtra("receiverid");

        msguser.setText(rrname);
        StorageReference storageReference = sref.child(rrid+".jpeg");
        storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                senderuserimg.setImageBitmap(bitmap);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(msg.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });


        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query q =databaseReference.child("College").orderByChild("email").equalTo(user.getEmail());
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            final String e = ds.child("email").getValue().toString();
                            String msg = msgs.getText().toString();
                            if (!msg.equals("")){
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                Chat chat= new Chat(e,rrid,msg);
                                reference.child("Chats").push().setValue(chat);
                            } else {
                                Toast.makeText(msg.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                            }
                            msgs.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(msg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        userchatlog = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userchatlog.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getMsgreceiver().equals(user.getEmail()) && chat.getMsgsender().equals(rrid) ||
                            chat.getMsgreceiver().equals(rrid) && chat.getMsgsender().equals(user.getEmail())){
                        userchatlog.add(chat);
                    }

                    msgAdaptor msgAdaptor = new msgAdaptor(msg.this,userchatlog, rrid);
                    mchatrecycler_view.setAdapter(msgAdaptor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(msg.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}