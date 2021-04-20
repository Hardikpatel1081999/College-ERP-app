package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class teacher_book extends AppCompatActivity {
    FloatingActionButton addbooks;
    RecyclerView bookrecycle;
    Uri bookuri;
    String ja;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Book");
    private FirebaseRecyclerOptions<Book> options;
    private FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_book);
        addbooks = findViewById(R.id.addbooks);
        bookrecycle = findViewById(R.id.bookrecycle);
        bookrecycle.setHasFixedSize(true);
        bookrecycle.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        ja =intent.getStringExtra("str1");
        AlertDialog.Builder alert = new AlertDialog.Builder(teacher_book.this);
        View view = LayoutInflater.from(teacher_book.this).inflate(R.layout.deletedialog,null);
        final AlertDialog alertDialog = alert.setView(view)
                .setPositiveButton("Yes Delete it !",null)
                .setNegativeButton("Cancel",null )
                .create();
        options =  new FirebaseRecyclerOptions.Builder<Book>().setQuery(databaseReference.orderByChild("bemail").equalTo(ja),Book.class).build();
        adapter =new FirebaseRecyclerAdapter<Book,BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BookViewHolder holder, final int position, @NonNull final Book model) {
                holder.book_name.setText("" + model.getBtopic());
                holder.book_type.setText(model.getBtname()+"("+model.getBbranch()+")");
                holder.book_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent();
                        intent.setData(Uri.parse(model.getUrl()));
                        startActivity(intent);
                    }
                });
                holder.book_delete.setOnClickListener(new View.OnClickListener() {
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
                                            StorageReference s = FirebaseStorage.getInstance().getReference("Book").child(model.getLocation());
                                            s.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    databaseReference.child(j).removeValue();
                                                    Toast.makeText(teacher_book.this,"Book Deleted",Toast.LENGTH_LONG).show();
                                                    alertDialog.dismiss();
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(teacher_book.this,error.getMessage(),Toast.LENGTH_LONG).show();
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
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewfornotice,parent,false);
                return new BookViewHolder(v);
            }
        };
        adapter.startListening();
        bookrecycle.setAdapter(adapter);



        addbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(teacher_book.this);
                d.setContentView(R.layout.addbooksdialog);
                final AutoCompleteTextView bteachername = d.findViewById(R.id.bteachername);
                final Spinner bbranch = d.findViewById(R.id.bbranch);
                final Spinner byear = d.findViewById(R.id.byear);
                final EditText bdiv= d.findViewById(R.id.bdiv);
                final EditText bookname= d.findViewById(R.id.bookname);
                final EditText booktopic = d.findViewById(R.id.booktopic);
                Button bookupload = d.findViewById(R.id.buploadbook);
                final Button bookselect = d.findViewById(R.id.bselectbook);
                bteachername.requestFocus();
                bteachername.setThreshold(1);
                final List<String> names= new ArrayList<String>();
                Query q = FirebaseDatabase.getInstance().getReference().child("College").orderByChild("select").equalTo("Teacher");
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            String name =  ds.child("name").getValue().toString();
                            names.add(name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(teacher_book.this, error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
                ArrayAdapter Adapter = new ArrayAdapter(teacher_book.this, R.layout.support_simple_spinner_dropdown_item,names);
                bteachername.setAdapter(Adapter);

                final ArrayList<String> t2 = new ArrayList<>();
                t2.add("-- Branch --");
                t2.add("COMP");
                t2.add("ELECTRONIC");
                t2.add("EXTC");
                t2.add("MECH");
                t2.add("IT");
                t2.add("CIVIL");
                t2.add("-");
                ArrayAdapter arrayAdapter2 = new ArrayAdapter(teacher_book.this, R.layout.spinnerlayout, t2){
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0)
                            return false;
                        else
                            return true;
                    }
                };
                bbranch.setAdapter(arrayAdapter2);

                final ArrayList<String> t3 = new ArrayList<>();
                t3.add("-- Year --");
                t3.add("FE");
                t3.add("SE");
                t3.add("TE");
                t3.add("BE");
                ArrayAdapter arrayAdapter3 = new ArrayAdapter(teacher_book.this, R.layout.spinnerlayout, t3){
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0)
                            return false;
                        else
                            return true;
                    }
                };
                byear.setAdapter(arrayAdapter3);

                bookselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"SELECT THE PDF FILE"),1);
                    }
                });

                bookupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bookuri!=null)
                        {
                            final String location = System.currentTimeMillis()+".pdf";
                            final String tname =bteachername.getText().toString();
                            final String tbranch = bbranch.getSelectedItem().toString();
                            final String tdiv =bdiv.getText().toString();
                            final String tyear = byear.getSelectedItem().toString();
                            final String bname = bookname.getText().toString();
                            final String btopic = booktopic.getText().toString();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                            final StorageReference sref = storageReference.child("Book").child(location);
                            sref.putFile(bookuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){

                                    Task<Uri> uri =taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uri.isComplete());
                                    Uri url = uri.getResult();
                                    Book book= new Book(tname,ja,tbranch,tyear,tdiv,bname,btopic,location,url.toString());
                                    String uploadId = databaseReference.push().getKey();
                                    databaseReference.child(uploadId).setValue(book);
                                    Toast.makeText(teacher_book.this,"BOOK UPLOADED",Toast.LENGTH_SHORT).show();
                                    d.dismiss();

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
            bookuri = data.getData();
            Toast.makeText(teacher_book.this,"Book file Selected",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(teacher_book.this,"Nook file  is not Selected",Toast.LENGTH_SHORT).show();
    }
}