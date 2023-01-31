package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAutoDriver extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<GetAutoDetails> list;
    ArrayList<String> list1;
    DatabaseReference databaseReference,databaseReference1;
    MyAdapter.RecyclerviewClickListener listener;
    MyAdapter adapter;
    Button btn;
    //String school="";
    String schoolname="";
    String id="";
    String mobphone="";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShowAutoDriver.this,Homepage.class);
        intent.putExtra("phone",mobphone);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_auto_driver);

        schoolname = getIntent().getStringExtra("schoolname");
        id = getIntent().getStringExtra("id");
        mobphone = getIntent().getStringExtra("phone");
        System.out.println("School "+schoolname);


        recyclerView = findViewById(R.id.recview);
        databaseReference = FirebaseDatabase.getInstance().getReference("SchoolsDemo").child(schoolname);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Demo1");




        list = new ArrayList<>();
        list1 = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnClickListener();
        adapter = new MyAdapter(this,list,listener);
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.show();



                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    list1.add((String) dataSnapshot.getKey());
                    System.out.println("list1"+list1);
                }

                databaseReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        progressDialog.show();
                        System.out.println(list1+"hdjdjd");

                        int i=0;
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(i<list1.size())
                            {
                                if(list1.get(i).equals(dataSnapshot.getKey()))
                                {
                                    GetAutoDetails getAutoDetails = dataSnapshot.getValue(GetAutoDetails.class);
                                    System.out.println("I am in "+list);
                                    list.add(getAutoDetails);
                                    adapter.notifyDataSetChanged();
                                    i++;
                                }
                            }
                            else
                            {
                                break;
                            }

                        }



                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }

    void setOnClickListener() {

        listener = new MyAdapter.RecyclerviewClickListener() {
            @Override
            public void onClick(View v, int position) {


                Intent intent = new Intent(getApplicationContext(),RickshawDetails.class);
                intent.putExtra("driverphoneno",list.get(position).getMobileNo());
                intent.putExtra("schoolname",schoolname);
                intent.putExtra("id",id);
                intent.putExtra("phone",mobphone);
                startActivity(intent);
            }
        };
    }
}