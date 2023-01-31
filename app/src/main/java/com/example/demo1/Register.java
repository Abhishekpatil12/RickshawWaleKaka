package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText edtname,edtemail,edtphone,edtpass,edtpass2;
    Button btn;
    TextView txt;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");


        edtname = findViewById(R.id.inputName);
        edtemail = findViewById(R.id.inputemail);
        edtphone = findViewById(R.id.inputPhone);
        edtpass = findViewById(R.id.editTextTextPassword);
        edtpass2 = findViewById(R.id.editTextTextPassword2);
        txt = findViewById(R.id.gotoLogin);

        btn = findViewById(R.id.button);


        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Check2");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                String name = edtname.getText().toString();
                String email = edtemail.getText().toString();
                String phone = edtphone.getText().toString();
                String password = edtpass.getText().toString();
                String password2 = edtpass2.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    edtname.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

                if(TextUtils.isEmpty(email))
                {
                    edtemail.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

                if(TextUtils.isEmpty(phone))
                {
                    edtphone.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

                if(TextUtils.isEmpty(password))
                {
                    edtpass.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

                if(TextUtils.isEmpty(password2))
                {
                    edtpass2.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }



                if(password.equals(password2) && !password.equals("") && !password2.equals("") )
                {
                    RegisterData registerData = new RegisterData(name,phone,email,password);

                    databaseReference.child(phone).setValue(registerData);
                    databaseReference.child(phone).child("Request").setValue("");
                    databaseReference.child(phone).child("child").setValue("");

                    databaseReference.child(phone).child("Val").setValue("1");
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                    Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Password not same",Toast.LENGTH_LONG).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }

                }

            }
        });


    }
}