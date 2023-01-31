package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
import java.util.ArrayList;
import java.util.HashMap;

public class Homepage extends AppCompatActivity {


    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    CardView cardView;
    TextView name,age,email;
    ImageView imgphoto;
    String item;
    TextView txtSchool;
    NavigationView navigationView;
    ArrayList<String> items,items2;
    HashMap<String,String> map = new HashMap<>();
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4;
    Button btnsearch,btnassigned,btncall,btnviewdetais;
    String schoolname;
    StorageReference storageReference;
    String mob="";
    String sname="";
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    int flag=0;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //map = new HashMap<>();
        items = new ArrayList<>();
        items2 = new ArrayList<>();

        sharedPreferences = getSharedPreferences("MySharedRef",MODE_PRIVATE);

        String mobphone = getIntent().getStringExtra("phone");
        //String mobphone = sharedPreferences.getString("phone","");



        drawerLayout = findViewById(R.id.drawer_layout);
        linearLayout = findViewById(R.id.layoutvisible);
        navigationView = findViewById(R.id.navigationView);
        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        txtSchool = findViewById(R.id.txtschoolname);
        btnsearch = findViewById(R.id.autosearch);
        name = findViewById(R.id.autoname1);
        age = findViewById(R.id.autoage1);
        email = findViewById(R.id.autoemail1);
        imgphoto = findViewById(R.id.imghome);
        btnassigned = findViewById(R.id.assigned);
        btncall = findViewById(R.id.btncall);
        cardView = findViewById(R.id.cardviewvis);
        btnviewdetais = findViewById(R.id.btndetails);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        map.put("abc","roman");




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Log.i("MENU_DRAWER_TAG","Home item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_search:
                        Log.i("MENU_DRAWER_TAG","Search item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_fillparentdetails:
                        //Log.i("MENU_DRAWER_TAG","Share item is clicked");
                        Intent intent = new Intent(Homepage.this,Parentpersonaldetail.class);
                        intent.putExtra("phone",mobphone);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_fillchilddetails:
                        //Log.i("MENU_DRAWER_TAG","Share item is clicked");
                        Intent intent1 = new Intent(Homepage.this,Addchilddetails.class);
                        intent1.putExtra("phone",mobphone);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_help:
                        Log.i("MENU_DRAWER_TAG","Help item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_request:
                        //Log.i("MENU_DRAWER_TAG","Help item is clicked");
                        Intent intent2 = new Intent(Homepage.this,RequestStatus.class);
                        intent2.putExtra("phone",mobphone);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        //Log.i("MENU_DRAWER_TAG","Help item is clicked");
                        /*
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedRef",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                         */
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }


                return true;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.show();

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Check2").child(mobphone).child("child");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.show();

                items.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    System.out.println("In data1-");
                    items.add(dataSnapshot.getKey());
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

        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Child");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog.show();


                System.out.println("In data2-");
                items2.clear();
                map.clear();


                int i=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    if(i<items.size())
                    {

                        if(items.get(i).equals(dataSnapshot.getKey()))
                        {

                            items2.add((String) dataSnapshot.child("name").getValue());
                            String str1 = (String) dataSnapshot.child("name").getValue();
                            String str2 = (String) dataSnapshot.child("id").getValue();
                            System.out.println(items2+"sjdjsk");
                            map.put(str1,str2);
                            System.out.println(map.get(str1));
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

        //String str = map.get(item);
        //System.out.println(str);
        //System.out.println(map);






        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,items2);
        System.out.println("item "+items2);
        for(String s:items2)
        {
            for (int i = 0; i < arrayAdapter.getCount(); i++)
            {
                System.out.println("In data3-");
                System.out.println(arrayAdapter.getItem(i)+"dfuyfyuy"+s);
                if (arrayAdapter.getItem(i).equals(s))
                    arrayAdapter.remove(s);
            }
            arrayAdapter.add(s);
        }
        //arrayAdapter.notifyDataSetChanged();
        autoCompleteTextView.setAdapter(arrayAdapter);

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                progressDialog.show();
                item = adapterView.getItemAtPosition(i).toString();
                //System.out.println(map);
                String str = map.get(item);
                System.out.println("In auto-");
                System.out.println("str "+str);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Child").child(str);
                databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Child").child(str);

                databaseReference3.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (task.isSuccessful())
                        {
                            if (task.getResult().exists())
                            {
                                txtSchool.setVisibility(View.VISIBLE);
                                DataSnapshot dataSnapshot = task.getResult();
                                txtSchool.setText("School name :- "+(CharSequence) dataSnapshot.child("schoolname").getValue());
                                String autophone = (String) dataSnapshot.child("assignedAutoDriver").getValue();
                                String scname = (String) dataSnapshot.child("schoolname").getValue();
                                sname = scname;
                                mob = autophone;
                                if(autophone.equals(""))
                                {
                                    //Toast.makeText(Homepage.this, "AutoDriver not assigned yet", Toast.LENGTH_SHORT).show();
                                    flag=1;
                                    return;
                                }
                                else
                                {
                                    databaseReference4 = FirebaseDatabase.getInstance().getReference().child("Demo1").child(autophone);
                                }



                            }
                        }
                        else
                        {

                        }

                    }
                });


                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }

            }
        });



        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog.show();

                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.isSuccessful())
                        {
                            if(task.getResult().exists())
                            {
                                DataSnapshot dataSnapshot = task.getResult();
                                schoolname = (String) dataSnapshot.child("schoolname").getValue();
                                String id = (String) dataSnapshot.child("id").getValue();
                                //System.out.println("Hello");
                                Intent intent = new Intent(Homepage.this,ShowAutoDriver.class);
                                System.out.println(schoolname);
                                intent.putExtra("schoolname",schoolname);
                                intent.putExtra("id",id);
                                intent.putExtra("phone",mobphone);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"New page",Toast.LENGTH_LONG).show();
                            }
                        }



                    }
                });

                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }

            }
        });



        btnassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag!=1)
                {


                    progressDialog.show();

                    cardView.setVisibility(View.VISIBLE);

                    databaseReference4.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {


                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot dataSnapshot = task.getResult();

                                    name.setText("Name : " + (CharSequence) dataSnapshot.child("name").getValue());
                                    email.setText("Email : " + (CharSequence) dataSnapshot.child("email").getValue());
                                    age.setText("Age : " + (CharSequence) dataSnapshot.child("age").getValue());

                                    String photo = (String) dataSnapshot.child("ProfilePhoto").getValue();
                                    String phone = (String) dataSnapshot.child("mobileNo").getValue();

                                    storageReference = FirebaseStorage.getInstance().getReference("DriverProfilePhotos/").child(phone + "/").child(photo);


                                    try {

                                        File localfile = File.createTempFile("tempfile", ".jpg");

                                        storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                                imgphoto.setImageBitmap(bitmap);
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
                            }

                        }
                    });

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(Homepage.this, "autodriver not assigned yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+mob));
                startActivity(i);
            }
        });

        btnviewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RickshawDetails.class);
                intent.putExtra("driverphoneno",mob);
                intent.putExtra("schoolname",sname);
                intent.putExtra("phone",mobphone);
                //System.out.println("123"+sname);
                intent.putExtra("id","1");
                startActivity(intent);
            }
        });






    }
}