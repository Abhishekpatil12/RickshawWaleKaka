package com.example.demo1;

import android.content.Context;
import android.content.Intent;
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
import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ArrayList<GetAutoDetails> list;
    static RecyclerviewClickListener listener;

    public MyAdapter(Context context, ArrayList<GetAutoDetails> list,RecyclerviewClickListener  listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.autoview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Demo1");


        GetAutoDetails getAutoDetails = list.get(position);
        holder.setIsRecyclable(false);
        String str = getAutoDetails.getProfilePhoto();
        String phone = getAutoDetails.getMobileNo();
        storageReference = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(phone+"/").child(str);
        try {

            File localfile = File.createTempFile("tempfile",".jpg");

            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    holder.img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        holder.name.setText("Name : "+getAutoDetails.getName());
        holder.email.setText("Email : "+getAutoDetails.getEmail());
        holder.age.setText("Age : "+getAutoDetails.getAge());





    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name,age,email;
        Button btn;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.autoname);
            age = itemView.findViewById(R.id.autoage);
            email = itemView.findViewById(R.id.autoemail);
            img = itemView.findViewById(R.id.imageView5);
            btn = itemView.findViewById(R.id.viewdetails);
            //itemView.setOnClickListener(this);
            btn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            listener.onClick(view,getAdapterPosition());

        }
    }

    public interface RecyclerviewClickListener
    {
        void onClick(View v,int position);
    }
}
