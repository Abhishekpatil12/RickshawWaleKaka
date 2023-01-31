package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView txt;
    EditText edtnumber,edtpass;
    Button btn;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    //SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.gotoRegister);
        btn = findViewById(R.id.btnLogin);
        edtnumber = findViewById(R.id.inputNumber);
        edtpass = findViewById(R.id.inputPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data!!");

        //sharedPreferences = getSharedPreferences("MySharedRef",MODE_PRIVATE);

        //String phone =sharedPreferences.getString("phone","");
        //String pass = sharedPreferences.getString("pass","");

        /*
        if(phone!=null)
        {
            Intent intent = new Intent(MainActivity.this,Homepage.class);
            intent.putExtra("phone",phone);
            startActivity(intent);
        }

         */


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Check2");

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();



                String phone = edtnumber.getText().toString();
                String password = edtpass.getText().toString();



                if(TextUtils.isEmpty(phone))
                {
                    edtnumber.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;

                }

                if(TextUtils.isEmpty(password))
                {
                    edtpass.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }





                databaseReference.child(phone).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.isSuccessful())
                        {
                            if(task.getResult().exists())
                            {


                                DataSnapshot dataSnapshot = task.getResult();
                                String pass = (String) dataSnapshot.child("password").getValue();
                                String val = (String) dataSnapshot.child("Val").getValue();



                                if(password.equals(pass))
                                {
                                    if(progressDialog.isShowing())
                                    {
                                        progressDialog.dismiss();
                                    }

                                    /*
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("phone",phone);
                                    editor.putString("pass",password);
                                    editor.apply();

                                     */



                                    if(val.equals("3"))
                                    {
                                        Intent intent = new Intent(MainActivity.this,Homepage.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                    }

                                    if(val.equals("1"))
                                    {
                                        Intent intent = new Intent(MainActivity.this,Parentpersonaldetail.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                    }

                                    if(val.equals("2"))
                                    {
                                        Intent intent = new Intent(MainActivity.this,Addchilddetails.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                    }




                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Could not fetch data", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }




        });
    }
}