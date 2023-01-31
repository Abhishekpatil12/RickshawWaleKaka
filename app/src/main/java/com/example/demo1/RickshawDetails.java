package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class RickshawDetails extends AppCompatActivity {

    FloatingActionButton call, request;
    TextView driver_name, phone, email, age, type, vehicle_no, capacity, school_name, address, time, areas, license_no, aadhar_no;
    ImageView driver_photo, vehicle_photo, license_photo, aadhar_photo;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3;
    StorageReference storageReference,storageReference1,storageReference2,storageReference3;
    TextView txt;
    ProgressDialog progressDialog;
    static  int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rickshaw_details);

        String mobileno = getIntent().getStringExtra("driverphoneno");
        System.out.println("kuhdiuqhdhquwdodhqw"+mobileno);
        String schoolname = getIntent().getStringExtra("schoolname");
        System.out.println("kuhdiuqhdhquwdodhqw"+schoolname);
        String id = getIntent().getStringExtra("id");
        String mobphone = getIntent().getStringExtra("phone");



        call = findViewById(R.id.call);
        request = findViewById(R.id.request);

        driver_name = findViewById(R.id.name1);
        phone = findViewById(R.id.phone_no);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);
        type = findViewById(R.id.type);
        vehicle_no = findViewById(R.id.vehicleNo);
        capacity = findViewById(R.id.capacity);
        school_name = findViewById(R.id.school_name);
        address = findViewById(R.id.school_add);
        time = findViewById(R.id.time);
        areas = findViewById(R.id.areas);
        license_no = findViewById(R.id.license_no);
        aadhar_no = findViewById(R.id.aadhar_no);
        txt = findViewById(R.id.textView35);

        driver_photo = findViewById(R.id.Driver_photo);
        vehicle_photo = findViewById(R.id.vehicle_photo);
        license_photo = findViewById(R.id.license_photo);
        aadhar_photo = findViewById(R.id.aadhar_photo);

        if(id.equals("1"))
        {
            request.setVisibility(View.GONE);
            txt.setVisibility(View.GONE);
        }

        if(ContextCompat.checkSelfPermission(RickshawDetails.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RickshawDetails.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Demo1").child(mobileno);

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("SchoolsDemo").child(schoolname).child(mobileno);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.show();

                String str="";

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    str = str + dataSnapshot.getKey();
                    str = str + ", ";

                }
                System.out.println(str);

                areas.setText(str);

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                progressDialog.show();

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();

                        driver_name.setText((CharSequence) dataSnapshot.child("name").getValue());
                        phone.setText((CharSequence) dataSnapshot.child("mobileNo").getValue());
                        email.setText((CharSequence) dataSnapshot.child("email").getValue());
                        age.setText((CharSequence) dataSnapshot.child("age").getValue());
                        type.setText((CharSequence) dataSnapshot.child("Vehicle").child("vehicleType").getValue());
                        vehicle_no.setText((CharSequence) dataSnapshot.child("Vehicle").child("noPlate").getValue());
                        capacity.setText((CharSequence) dataSnapshot.child("Vehicle").child("capacity").getValue());
                        school_name.setText(schoolname);
                        address.setText((CharSequence) dataSnapshot.child("Schools").child(schoolname).child("schoolAddress").getValue());
                        time.setText((CharSequence) dataSnapshot.child("Schools").child(schoolname).child("tripTime").getValue());
                        license_no.setText((CharSequence) dataSnapshot.child("VerificationDetails").child("licenseNo").getValue());
                        aadhar_no.setText((CharSequence) dataSnapshot.child("VerificationDetails").child("aadharNo").getValue());
                        String str = (String) dataSnapshot.child("ProfilePhoto").getValue();
                        String str1 = (String) dataSnapshot.child("VerificationDetails").child("aadharPhoto").getValue();
                        String str2 = (String) dataSnapshot.child("VerificationDetails").child("lincensePhoto").getValue();
                        String str3 = (String) dataSnapshot.child("Vehicle").child("vehiclePhoto").getValue();

                        storageReference = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(mobileno+"/").child(str);
                        storageReference1 = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(mobileno+"/").child(str1);
                        storageReference2 = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(mobileno+"/").child(str2);
                        storageReference3 = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(mobileno+"/").child(str3);


                        try {

                            File localfile = File.createTempFile("tempfile",".jpg");

                            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    progressDialog.show();

                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    driver_photo.setImageBitmap(bitmap);

                                    if(progressDialog.isShowing())
                                    {
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            File localfile1 = File.createTempFile("tempfile",".jpg");

                            storageReference1.getFile(localfile1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    progressDialog.show();
                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile1.getAbsolutePath());
                                    aadhar_photo.setImageBitmap(bitmap);
                                    if(progressDialog.isShowing())
                                    {
                                        progressDialog.dismiss();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            File localfile2 = File.createTempFile("tempfile",".jpg");

                            storageReference2.getFile(localfile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.show();
                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile2.getAbsolutePath());
                                    license_photo.setImageBitmap(bitmap);

                                    if(progressDialog.isShowing())
                                    {
                                        progressDialog.dismiss();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            File localfile3 = File.createTempFile("tempfile",".jpg");

                            storageReference3.getFile(localfile3).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.show();
                                    Bitmap bitmap = BitmapFactory.decodeFile(localfile3.getAbsolutePath());
                                    vehicle_photo.setImageBitmap(bitmap);
                                    if(progressDialog.isShowing())
                                    {
                                        progressDialog.dismiss();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else
                    {

                    }

                }

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }
        });

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+mobileno));
                startActivity(i);
            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Demo1").child(mobileno).child("Request");
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone).child("Request");

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference1.child(id).setValue("Pending");
                databaseReference3.child(id).setValue(mobileno);
                Toast.makeText(getApplicationContext(),"Request Send Successfully",Toast.LENGTH_LONG).show();
            }
        });






    }
}