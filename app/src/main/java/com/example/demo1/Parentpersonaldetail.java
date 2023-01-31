package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Parentpersonaldetail extends AppCompatActivity {

    Button button;
    EditText Name, email, phone, phone2, house, society, street, pin;
    DatabaseReference databaseReference;
    String mobphone="";
    AutoCompleteTextView astate,adistrict,acity;
    ArrayAdapter<String> arrayAdapterstate,arrayAdapterdistrict,arrayAdaptercity;
    String[] state = {"Maharashtra","Gujarat","Karnataka"};
    String[] district = {"Mumbai","Dhule","Nashik","Pune"};
    String[] taluka = {"Shirpur","Dhule city","Sakri","Shinkheda"};
    String str9="",str10="",str11="";
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentpersonaldetail);

        mobphone = getIntent().getStringExtra("phone");



        button =(Button) findViewById(R.id.searchauto);
        Name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        phone2 = findViewById(R.id.Phone2);
        house = findViewById(R.id.HouseNo);
        society = findViewById(R.id.Society);
        street = findViewById(R.id.Street);
        pin = findViewById(R.id.Pin);
        acity = findViewById(R.id.City);
        adistrict = findViewById(R.id.District);
        astate = findViewById(R.id.State);


        arrayAdaptercity = new ArrayAdapter<String>(this,R.layout.list_item,taluka);
        arrayAdapterdistrict = new ArrayAdapter<String>(this,R.layout.list_item,district);
        arrayAdapterstate = new ArrayAdapter<String>(this,R.layout.list_item,state);

        acity.setAdapter(arrayAdaptercity);
        adistrict.setAdapter(arrayAdapterdistrict);
        astate.setAdapter(arrayAdapterstate);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");


        adistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str10 = adapterView.getItemAtPosition(i).toString();
            }
        });

        acity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str9 = adapterView.getItemAtPosition(i).toString();
            }
        });

        astate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str11 = adapterView.getItemAtPosition(i).toString();
            }
        });





        databaseReference = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone);

        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        DataSnapshot dataSnapshot = task.getResult();

                        Name.setText((CharSequence) dataSnapshot.child("name").getValue());
                        email.setText((CharSequence) dataSnapshot.child("email").getValue());
                        phone.setText((CharSequence) dataSnapshot.child("mobilenumber").getValue());
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
                }

            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                String str4 = phone2.getText().toString();
                String str5 = house.getText().toString();
                String str6 = society.getText().toString();
                String str7 = street.getText().toString();
                String str8 = pin.getText().toString();

                str9 = acity.getText().toString();
                str10 = adistrict.getText().toString();
                str11 = astate.getText().toString();

                if(str11.equals("State"))
                {
                    Toast.makeText(Parentpersonaldetail.this, "Select State", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(str10.equals("District"))
                {
                    Toast.makeText(Parentpersonaldetail.this, "Select District", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(str9.equals("Taluka"))
                {
                    Toast.makeText(Parentpersonaldetail.this, "Select Taluka", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str4))
                {
                    phone2.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str5))
                {
                    house.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str6))
                {
                    society.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str7))
                {
                    street.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str8))
                {
                    pin.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }


                //Parentdata parentdata = new Parentdata(str4);

                databaseReference.child("alternateno").setValue(str4);

                Parentdata parentdata1 = new Parentdata(str5,str6,str7,str8,str9,str10,str11);

                databaseReference.child("address").setValue(parentdata1);



                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.isSuccessful())
                        {
                            if(task.getResult().exists())
                            {
                                DataSnapshot dataSnapshot = task.getResult();

                                String val = (String) dataSnapshot.child("Val").getValue();


                                if(val.equals("1"))
                                {
                                    databaseReference.child("Val").setValue("2");
                                    Intent intent = new Intent(getApplicationContext(),Addchilddetails.class);
                                    intent.putExtra("phone",mobphone);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                                }


                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }



                Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_LONG).show();


            }
        });


    }
}