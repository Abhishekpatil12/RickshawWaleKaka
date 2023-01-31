package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RequestUser> list;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3;
    MyRequestAdapter myRequestAdapter;
    MyRequestAdapter.RecyclerviewClickListener1 listener1;
    String childname="",status="",autoname="";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);

        String mobphone = getIntent().getStringExtra("phone");

        recyclerView = findViewById(R.id.recviewrequest);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickListener();
        myRequestAdapter = new MyRequestAdapter(this,list,listener1);
        recyclerView.setAdapter(myRequestAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone).child("Request");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.show();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    RequestUser requestUser = new RequestUser();

                    String childid = dataSnapshot.getKey();
                    String autonumber = (String) dataSnapshot.getValue();

                    databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Demo1").child(autonumber);

                    databaseReference2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            progressDialog.show();

                            if(task.isSuccessful())
                            {
                                if(task.getResult().exists())
                                {
                                    DataSnapshot dataSnapshot = task.getResult();

                                    autoname = (String) dataSnapshot.child("name").getValue();
                                    System.out.println("name : "+autoname);
                                    requestUser.setName(autoname);

                                    String str = (String) dataSnapshot.child("ProfilePhoto").getValue();
                                    System.out.println(str+"122444");
                                    requestUser.setProfilephoto(str);
                                    requestUser.setPhoneno(autonumber);



                                }
                            }

                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                        }
                    });

                    databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Child").child(childid);

                    databaseReference1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            progressDialog.show();
                            if(task.isSuccessful())
                            {
                                if(task.getResult().exists())
                                {
                                    DataSnapshot dataSnapshot = task.getResult();

                                    childname = (String) dataSnapshot.child("name").getValue();
                                    String str = (String) dataSnapshot.child("schoolname").getValue();
                                    requestUser.setChild(childname);
                                    requestUser.setSchoolname(str);


                                }
                            }

                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                        }
                    });

                    databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Demo1").child(autonumber);

                    databaseReference3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            progressDialog.show();

                            if(task.isSuccessful())
                            {
                                if(task.getResult().exists())
                                {
                                    DataSnapshot dataSnapshot = task.getResult();

                                    status = (String) dataSnapshot.child("Request").child(childid).getValue();
                                    requestUser.setStatus(status);
                                    System.out.println("status : "+status);

                                    list.add(requestUser);
                                    myRequestAdapter.notifyDataSetChanged();


                                }
                            }

                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                        }
                    });





                    System.out.println("123"+childname);

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    void setOnClickListener() {

        listener1 = new MyRequestAdapter.RecyclerviewClickListener1() {
            @Override
            public void onClick(View v, int position) {

                Toast.makeText(RequestStatus.this, "Hello", Toast.LENGTH_SHORT).show();

                Button b1 = findViewById(v.getId());

                if(b1.getText().equals("Call"))
                {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+list.get(position).getPhoneno()));
                    startActivity(i);
                }
                else if(b1.getText().equals("View Details"))
                {
                    Intent intent = new Intent(getApplicationContext(),RickshawDetails.class);
                    intent.putExtra("driverphoneno",list.get(position).getPhoneno());
                    intent.putExtra("schoolname",list.get(position).getSchoolname());
                    intent.putExtra("phone","8975200645");
                    intent.putExtra("id","1");
                    startActivity(intent);
                }


            }
        };
    }


    public void readData(MyCallback myCallback) {
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Demo1").child("8380885374");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.child("name").getValue();
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public interface MyCallback {
        void onCallback(String value);
    }


}