package com.example.project;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class viewAdaptor extends RecyclerView.Adapter<viewAdaptor.viewAdaptorViewHolder> {

public Context c;
public  ArrayList<College> list;
DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("College");
StorageReference sref= FirebaseStorage.getInstance().getReference().child("College") ;
    Dialog d;
    public viewAdaptor(Context context, ArrayList<College> list)
    {
        c =context;
        this.list =list;
    }




    @Override
    public long getItemId(int position) {
        return position;
    }





    @NonNull
    @Override
    public viewAdaptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.textviewforlist,parent,false);
        return new viewAdaptorViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final viewAdaptorViewHolder holder, final int position) {
        final College college = list.get(position);
        final String select = college.getSelect();
        d = new Dialog(c);
        d.setContentView(R.layout.update_delete);

        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        View view = LayoutInflater.from(c).inflate(R.layout.deletedialog,null);
        final AlertDialog alertDialog = alert.setView(view)
                .setPositiveButton("Yes Delete it !",null)
                .setNegativeButton("Cancel",null )
                .create();


        if(select.equals("Teacher"))
        {
            final String b = college.getBranch();
            String bh = "Branch :"+ b+"\t\t\t EDU : "+college.divrollno;
            String n =college.getEphone();
            String nu = "\t Contact : "+n ;


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CircleImageView cimage =d.findViewById(R.id.cimg);
                    Button update =d.findViewById(R.id.update);
                    final TextView cappno = d.findViewById(R.id.cappno);
                    final TextView cname=d.findViewById(R.id.cname);
                    final TextView cadress=d.findViewById(R.id.caddress);
                    final TextView czip=d.findViewById(R.id.czip);
                    final TextView cphone=d.findViewById(R.id.cphone);
                    final TextView cephone=d.findViewById(R.id.cephone);
                    final TextView cyear=d.findViewById(R.id.cyear);
                    final TextView cbranch=d.findViewById(R.id.cbranch);

                    cappno.setText(college.getApplicationno());
                    cname.setText("Name :"+college.getName());
                    cadress.setText("Address :"+college.getAddress());
                    czip.setText("Zip :"+college.getZip());
                    cyear.setText("Qualification :"+college.getDivrollno());
                    cphone.setText("Contact no :"+college.getPhoneno());
                    cephone.setText("Emergency Contact no :"+college.getEphone());
                    cbranch.setText("Branch :"+college.getBranch());
                    StorageReference ss = sref.child(college.getEmail()+".jpeg");
                    ss.getBytes(2924 * 2924).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            cimage.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(c, "not able to get the image", Toast.LENGTH_SHORT).show();
                                }
                            });


                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Intent a = new Intent(c,aupdate.class);
                            a.putExtra("udemail",college.getEmail());
                            c.startActivity(a);
                        }
                    });
d.show();
                }
            });


            holder.carddelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                    Button p = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button n = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    p.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          Query q = dRef.orderByChild("email").equalTo(college.getEmail());
                            q.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(final DataSnapshot ds : snapshot.getChildren())
                                    {
                                        StorageReference storageReference = sref.child(college.getEmail()+".jpeg");
                                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                final String j =  ds.getRef().getKey();
                                                dRef.child(j).removeValue();
                                                Toast.makeText(c,"Record Deleted",Toast.LENGTH_SHORT).show();
                                                alertDialog.dismiss();
                                            }
                                        }).addOnCanceledListener(new OnCanceledListener() {
                                            @Override
                                            public void onCanceled() {
                                                Toast.makeText(c,"Record  Not Deleted",Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
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
            holder.infoname.setText(" " + college.getName());
            holder.infobranch.setText(bh);
            holder.infoselect.setText(nu);
            StorageReference storageReference = sref.child(college.getEmail()+".jpeg");
            storageReference.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap  bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.infoimage.setImageBitmap(bitmap);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }

        if(select.equals("Student"))
        {
            String b = college.getBranch();
            String bh = "Branch :"+ b+"-"+college.getYear()+"/"+college.getDivrollno()+"\t\t\t\t\t\t\t\t\t Sem :"+college.getSemester();
            String n = college.getEphone();
            String nu = "\t Contact : "+n ;
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CircleImageView cimage =d.findViewById(R.id.cimg);
                    Button update =d.findViewById(R.id.update);
                    final TextView cappno = d.findViewById(R.id.cappno);
                    final TextView cname=d.findViewById(R.id.cname);
                    final TextView cadress=d.findViewById(R.id.caddress);
                    final TextView czip=d.findViewById(R.id.czip);
                    final TextView cphone=d.findViewById(R.id.cphone);
                    final TextView cephone=d.findViewById(R.id.cephone);
                    final TextView cyear=d.findViewById(R.id.cyear);
                    final TextView cbranch=d.findViewById(R.id.cbranch);
                    StorageReference sa =sref.child(college.getEmail()+".jpeg");
                    sa.getBytes(2024 * 2024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            cimage.setImageBitmap(bitmap);

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(c, "not able to get the image", Toast.LENGTH_SHORT).show();
                                }
                            });
                    cappno.setText(college.getApplicationno());
                    cname.setText("Name :"+college.getName());
                    cadress.setText("Address :"+college.getAddress());
                    czip.setText("Zip :"+college.getZip());
                    cphone.setText("Contact no :"+college.getPhoneno());
                    cephone.setText("Emergency Contact no :"+college.getEphone());
                    cbranch.setText("Branch :"+college.getBranch());
                    cyear.setText("Class :"+college.getYear()+"-"+college.getDivrollno()+" ("+college.getSemester()+")");

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Intent a = new Intent(c,aupdate.class);
                            a.putExtra("udemail",college.getEmail());
                            c.startActivity(a);
                        }
                    });

                    d.show();
                }
            });


            holder.carddelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     final String e =college.getEmail();
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

                                    Query q = dRef.orderByChild("email").equalTo(college.getEmail());
                                    q.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(final DataSnapshot ds : snapshot.getChildren())
                                            {

                                                StorageReference storageReference = sref.child(college.getEmail()+".jpeg");
                                                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        final String j =  ds.getRef().getKey();
                                                        dRef.child(j).removeValue();
                                                        Toast.makeText(c,"Record Deleted",Toast.LENGTH_SHORT).show();
                                                        alertDialog.dismiss();
                                                    }
                                                }).addOnCanceledListener(new OnCanceledListener() {
                                                    @Override
                                                    public void onCanceled() {
                                                        Toast.makeText(c,"Record  Not Deleted",Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(c,error.getMessage(),Toast.LENGTH_LONG).show();
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

            holder.infoname.setText(" " +college.getName());
            holder.infobranch.setText(bh);
            holder.infoselect.setText(nu);
            StorageReference sv = sref.child(college.getEmail()+".jpeg");
            sv.getBytes(2924 * 2924).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.infoimage.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(c,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        }



}

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class viewAdaptorViewHolder extends RecyclerView.ViewHolder{
        public TextView infoname,infobranch,infoselect;
        public CircleImageView infoimage;
        public  CardView cardView;
        ImageButton carddelete;
        public viewAdaptorViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            carddelete = itemView.findViewById(R.id.carddelete);
            infoname= itemView.findViewById(R.id.tvuserinfol);
            infobranch= itemView.findViewById(R.id.tvuserinfob);
            infoselect= itemView.findViewById(R.id.tvuserinfos);
            infoimage = itemView.findViewById(R.id.userinfoimg);

        }
    }
}
