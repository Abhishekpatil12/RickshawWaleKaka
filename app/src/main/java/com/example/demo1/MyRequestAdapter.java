package com.example.demo1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.MyViewHolder2>{

    Context context;
    ArrayList<RequestUser> list;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    static RecyclerviewClickListener1 listener1;


    public MyRequestAdapter(Context context, ArrayList<RequestUser> list, RecyclerviewClickListener1 listener1) {
        this.context = context;
        this.list = list;
        this.listener1 = listener1;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.autorequest,parent,false);
        return new MyViewHolder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        RequestUser requestUser = list.get(position);

        String str = requestUser.getProfilephoto();
        System.out.println("fhwir"+requestUser.getSchoolname());
        String phone = requestUser.getPhoneno();
        databaseReference = FirebaseDatabase.getInstance().getReference("Demo1");

        storageReference = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(phone+"/").child(str);

        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");

            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    holder.img1.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.name.setText("Name : "+requestUser.getName());
        holder.child.setText("Child : "+requestUser.getChild());
        holder.status.setText("Status : "+requestUser.getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,child,status;
        Button btn,btncall;
        ImageView img1;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.autonamereq);
            child = itemView.findViewById(R.id.childreq);
            status = itemView.findViewById(R.id.statusreq);
            img1 = itemView.findViewById(R.id.imageViewautoreq);
            btn = itemView.findViewById(R.id.viewdetailsreq);
            btncall = itemView.findViewById(R.id.callreq);
            btn.setOnClickListener(this);
            btncall.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener1.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerviewClickListener1
    {
        void onClick(View v,int position);
    }


}
