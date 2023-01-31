package com.example.demo1;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Addchilddetails extends AppCompatActivity {

    ImageView photo;

    long i;
    String item,ide="c";
    String[] gender = {"Male", "Female", "Other"};

    Button childphoto,btnsave;
    EditText childname,childrelation,childage,childstd,childdiv,schoolname,schooltime,house, society, street, pin ;
    AutoCompleteTextView autoCompleteTxt;
    AutoCompleteTextView astate,adistrict,acity,aschoolname;
    String[] state = {"Maharashtra","Gujarat","Karnataka"};
    String[] district = {"Mumbai","Dhule","Nashik","Pune"};
    String[] taluka = {"Shirpur","Dhule city","Sakri","Shinkheda"};
    String[] schoolnames = {"Agrasen High School","Jay Hind High School","Maharana Pratap High School"};
    String str8="",str9="",str10="",str16="";
    CheckBox check;
    Uri imageuri;
    String id;
    ArrayAdapter<String> arrayAdapterstate,arrayAdapterdistrict,arrayAdaptercity,arrayAdapterschool;
    ArrayAdapter<String> adapterItems;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4;
    StorageReference storageReference;
    ProgressDialog progressDialog,progressDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addchilddetails);




        childname = findViewById(R.id.Name);
        childrelation = findViewById(R.id.relation);
        childage = findViewById(R.id.Age);
        childstd = findViewById(R.id.Standard);
        childdiv = findViewById(R.id.division);
        schooltime = findViewById(R.id.schoolTime);
        house = findViewById(R.id.HouseNo);
        society = findViewById(R.id.Society);
        street = findViewById(R.id.Street);
        pin = findViewById(R.id.Pin);
        photo = findViewById(R.id.photo);
        childphoto = findViewById(R.id.addphoto);
        btnsave = findViewById(R.id.button);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        check = findViewById(R.id.checkBox);
        photo = findViewById(R.id.photo);
        acity = findViewById(R.id.City);
        adistrict = findViewById(R.id.District);
        astate = findViewById(R.id.State);
        aschoolname = findViewById(R.id.school_name);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");

        String mobphone = getIntent().getStringExtra("phone");



        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone).child("address");
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone);

        func();

        arrayAdapterschool = new ArrayAdapter<String>(this,R.layout.list_item,schoolnames);
        aschoolname.setAdapter(arrayAdapterschool);

        aschoolname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                str16 = adapterView.getItemAtPosition(i).toString();
            }
        });



        adistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str9 = adapterView.getItemAtPosition(i).toString();
                System.out.println("str9 "+str9);

            }
        });

        acity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str8 = adapterView.getItemAtPosition(i).toString();
            }
        });

        astate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                str10 = adapterView.getItemAtPosition(i).toString();
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check.isChecked())
                {



                    databaseReference1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            progressDialog.show();

                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    System.out.println("I am in SSSSSSSSSSSSS");

                                    System.out.println(dataSnapshot.getValue());

                                    house.setText((CharSequence) dataSnapshot.child("houseno").getValue());
                                    society.setText((CharSequence) dataSnapshot.child("society").getValue());
                                    street.setText((CharSequence) dataSnapshot.child("street").getValue());
                                    pin.setText((CharSequence) dataSnapshot.child("pincode").getValue());
                                    acity.setText((CharSequence) dataSnapshot.child("city").getValue());
                                    adistrict.setText((CharSequence) dataSnapshot.child("district").getValue());
                                    astate.setText((CharSequence) dataSnapshot.child("state").getValue());

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
                            }

                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }

                        }
                    });

                }
                else
                {
                    house.setText("");
                    society.setText("");
                    street.setText("");
                    pin.setText("");

                    func();
                }
            }
        });

        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Iterator");



        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone).child("child");


        databaseReference3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();

                        i = (long) dataSnapshot.child("Val").getValue();
                        ide = ide + i;
                        i = i +1;

                        databaseReference3.child("Val").setValue(i);



                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Could not fetch data", Toast.LENGTH_LONG).show();
                }

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Child");
        storageReference = FirebaseStorage.getInstance().getReference("Childphotos").child(mobphone+"/");


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                String str1 = childname.getText().toString();
                String str2 = childrelation.getText().toString();
                String str12 = childage.getText().toString();
                String str14 = childstd.getText().toString();
                String str15 = childdiv.getText().toString();
                String str18 = schooltime.getText().toString();

                String str4 = house.getText().toString();
                String str5 = society.getText().toString();
                String str6 = street.getText().toString();
                String str7 = pin.getText().toString();


                str8 = acity.getText().toString();
                str9 = adistrict.getText().toString();
                str10 = astate.getText().toString();
                String str20 = autoCompleteTxt.getText().toString();

                if(str20.equals("Gender"))
                {
                    Toast.makeText(Addchilddetails.this, "Select Gender", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(str10.equals("State"))
                {
                    Toast.makeText(Addchilddetails.this, "Select State", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(str9.equals("District"))
                {
                    Toast.makeText(Addchilddetails.this, "Select District", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }


                if(str8.equals("Taluka"))
                {
                    Toast.makeText(Addchilddetails.this, "Select Taluka", Toast.LENGTH_SHORT).show();

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str1))
                {
                    childname.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str2))
                {
                    childrelation.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str12))
                {
                    childage.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;

                }
                if(TextUtils.isEmpty(str14))
                {
                    childstd.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;

                }
                if(TextUtils.isEmpty(str15))
                {
                    childdiv.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;

                }

                if(TextUtils.isEmpty(str18))
                {
                    schooltime.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;

                }

                if(TextUtils.isEmpty(str7))
                {
                    pin.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str6))
                {
                    street.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str5))
                {
                    society.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                if(TextUtils.isEmpty(str4))
                {
                    house.setError("Field cant be empty");

                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }



                Parentdata parentdata = new Parentdata(str4,str5,str6,str7,str8,str9,str10);

                databaseReference.child(ide).child("id").setValue(ide);
                databaseReference.child(ide).child("name").setValue(str1);
                databaseReference.child(ide).child("relation").setValue(str2);
                databaseReference.child(ide).child("age").setValue(str12);
                databaseReference.child(ide).child("standard").setValue(str14);
                databaseReference.child(ide).child("division").setValue(str15);
                databaseReference.child(ide).child("schoolname").setValue(str16);
                databaseReference.child(ide).child("schooltime").setValue(str18);
                databaseReference.child(ide).child("gender").setValue(str20);
                databaseReference.child(ide).child("parentnumber").setValue(mobphone);

                databaseReference.child(ide).child("assignedAutoDriver").setValue("");

                databaseReference.child(ide).child("address").setValue(parentdata);
                databaseReference4.child("Val").setValue("3");
                databaseReference2.child(ide).setValue("");


                if(imageuri==null)
                {
                    Toast.makeText(Addchilddetails.this, "Select Image", Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                    return;
                }

                ContentResolver contentResolver = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String imgext =  mime.getExtensionFromMimeType(contentResolver.getType(imageuri));


                if(imageuri!=null)
                {

                    progressDialog.show();
                    String uploadname = System.currentTimeMillis()+"."+imgext;
                    StorageReference filerefrence = storageReference.child(uploadname);

                    filerefrence.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.show();
                            Toast.makeText(getApplicationContext(),"Upload Successfully",Toast.LENGTH_LONG).show();
                            databaseReference.child(ide).child("childphoto").setValue(uploadname);
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
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"NO File Selected",Toast.LENGTH_LONG).show();
                }


                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_LONG).show();
                /*
                Intent intent = new Intent(getApplicationContext(),Homepage.class);
                intent.putExtra("phone",mobphone);
                startActivity(intent);

                 */

            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                        if(result!=null)
                        {
                            photo.setImageURI(result);
                            imageuri = result;
                        }

                    }
                });

        childphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                mGetContent.launch("image/*");

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }

            }
        });


    }

    private void func() {
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, gender);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();

            }
        });

        arrayAdaptercity = new ArrayAdapter<String>(this,R.layout.list_item,taluka);
        arrayAdapterdistrict = new ArrayAdapter<String>(this,R.layout.list_item,district);
        arrayAdapterstate = new ArrayAdapter<String>(this,R.layout.list_item,state);

        acity.setAdapter(arrayAdaptercity);
        adistrict.setAdapter(arrayAdapterdistrict);
        astate.setAdapter(arrayAdapterstate);
    }
}