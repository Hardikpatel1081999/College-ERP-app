package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class admin_circular extends AppCompatActivity {
    FloatingActionButton addnotes;
    RecyclerView noticerecycle;
    Uri noticeuri;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notice");
    private FirebaseRecyclerOptions<Notice> options;
    private FirebaseRecyclerAdapter<Notice, NoticeViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_circular);
        addnotes = findViewById(R.id.addnotice);
        noticerecycle = findViewById(R.id.noticerecycle);
       noticerecycle.setHasFixedSize(true);
        noticerecycle.setLayoutManager(new LinearLayoutManager(this));

        AlertDialog.Builder alert = new AlertDialog.Builder(admin_circular.this);
        View view = LayoutInflater.from(admin_circular.this).inflate(R.layout.deletedialog,null);
        final AlertDialog alertDialog = alert.setView(view)
                .setPositiveButton("Yes Delete it !",null)
                .setNegativeButton("Cancel",null )
                .create();
        options =  new FirebaseRecyclerOptions.Builder<Notice>().setQuery(databaseReference,Notice.class).build();
        adapter =new FirebaseRecyclerAdapter<Notice, NoticeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NoticeViewHolder holder, final int position, @NonNull final Notice model) {
                holder.notice_name.setText("" + model.getNname());
                holder.notice_type.setText(""+model.getNtype());
                holder.notice_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent();
                        intent.setData(Uri.parse(model.getUrl()));
                        startActivity(intent);
                    }
                });
                holder.notice_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                        Button p = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button n = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        p.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Query q = databaseReference.orderByChild("location").equalTo(model.getLocation());
                                q.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot ds :snapshot.getChildren())
                                        {
                                            final String j = ds.getRef().getKey();
                                            StorageReference s = FirebaseStorage.getInstance().getReference("Notice").child(model.getLocation());
                                            s.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    databaseReference.child(j).removeValue();
                                                    Toast.makeText(admin_circular.this,"Notice Deleted",Toast.LENGTH_LONG).show();
                                                    alertDialog.dismiss();
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(admin_circular.this,error.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        n.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });

                    }
                });
            }

            @NonNull
            @Override
            public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewfornotice,parent,false);
                return new NoticeViewHolder(v);
            }
        };
        adapter.startListening();
        noticerecycle.setAdapter(adapter);




        addnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Dialog  d = new Dialog(admin_circular.this);
                d.setContentView(R.layout.addnoticedialog);
                final EditText noticename = d.findViewById(R.id.notcename);
                final Spinner noticetype = d.findViewById(R.id.noticetype);
                Button noticeupload = d.findViewById(R.id.noticeupload);
                final Button noticeselect = d.findViewById(R.id.selectnotice);
                noticename.requestFocus();
                final ArrayList<String> t = new ArrayList<>();
                t.add("--View_User--");
                t.add("Both");
                t.add("Teacher");
                t.add("Student");
                ArrayAdapter arrayAdapter = new ArrayAdapter(admin_circular.this, R.layout.spinnerlayout, t){
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0)
                            return false;
                        else
                            return true;
                    }
                };
                noticetype.setAdapter(arrayAdapter);
                noticeselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"SELECT THE PDF FILE"),1);
                    }
                });

                noticeupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(noticeuri!=null)
                        {
                            final String location = System.currentTimeMillis()+".pdf";
                            final String type = noticetype.getSelectedItem().toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            final StorageReference sref = storageReference.child("Notice").child(location);
                                    sref.putFile(noticeuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){

                                            Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uri.isComplete());
                                            Uri url = uri.getResult();
                                            Notice notice= new Notice(noticename.getText().toString(),type,location,url.toString());
                                            String uploadId = databaseReference.push().getKey();
                                            databaseReference.child(uploadId).setValue(notice);
                                            Toast.makeText(admin_circular.this,"FILE UPLOADED",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }
                });
                d.show();
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            noticeuri = data.getData();
            Toast.makeText(admin_circular.this,"Notice file Selected",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(admin_circular.this,"Notice file  is not Selected",Toast.LENGTH_SHORT).show();
    }
    }