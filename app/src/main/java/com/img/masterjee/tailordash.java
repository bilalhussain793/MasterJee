package com.img.masterjee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class tailordash extends AppCompatActivity {
    Button logout_btn;
    ImageView imv;
    String userphone,sizeofshirt,shoulderofshirt;
    TextView nam,phon,ttp,shlder,sized;

    TextView phn_order,design_order,pprice,width1,length1;
    Button order,myord;

    ListView lv;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Firebase firebase;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
Firebase fb;
String a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailordash);
        logout_btn=findViewById(R.id.logout);

        shlder=findViewById(R.id.Sholdert);
        sized=findViewById(R.id.Sizet);
        myord=findViewById(R.id.Myorder);
        imv=findViewById(R.id.imv);
        nam=findViewById(R.id.name1);
        phon=findViewById(R.id.phone1);
        Firebase.setAndroidContext(this);
        phn_order=findViewById(R.id.phnorder);
        design_order=findViewById(R.id.designorder);
        order=findViewById(R.id.accept);
        pprice=findViewById(R.id.pppp);
        width1=findViewById(R.id.wit1);
        length1=findViewById(R.id.len1);



        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        userphone = prefs.getString("phone", null);
        final String a=prefs.getString("phone",null);


        Picasso.with(tailordash.this).load("https://firebasestorage.googleapis.com/v0/b/masterjee-d5ab5.appspot.com/o/images%2F" +
                "tailor%2F"+userphone+"?alt=media&token=21487e8b-6ac2-4ee5-a820-e3e622f7c29f").into(imv);

        lv=findViewById(R.id.list);

        firebase=new Firebase("https://masterjee-d5ab5.firebaseio.com/designs");

        adapter=new ArrayAdapter<String>(tailordash.this,R.layout.support_simple_spinner_dropdown_item,arrayList);

        lv.setAdapter(adapter);

        firebase.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                arrayList.add(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        myord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tailordash.this,orderactvity.class));
            }
        });


        adapter=new ArrayAdapter(tailordash.this,R.layout.support_simple_spinner_dropdown_item,arrayList);

        lv.setAdapter(adapter);







        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  Toast.makeText(tailordash.this, ""+arrayList.get(i), Toast.LENGTH_SHORT).show();
            phn_order.setText(arrayList.get(i));


                DatabaseReference myRef4 = database.getReference("designs/"+arrayList.get(i));
                myRef4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String ds = dataSnapshot.child("Design1").getValue(String.class);
                        String o = dataSnapshot.child("Total").getValue(String.class);
                        String l = dataSnapshot.child("Width").getValue(String.class);
                        String w = dataSnapshot.child("Length").getValue(String.class);
                        String si = dataSnapshot.child("Size").getValue(String.class);
                        String sh = dataSnapshot.child("Shoulder").getValue(String.class);

                        design_order.setText(ds);
                        pprice.setText(o);
                       width1.setText(l);
                       length1.setText(w);
                       shlder.setText(sh);
                       sized.setText(si);


                    }



                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());

                    }
                });


            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String aa= design_order.getText().toString();
               String b=   phn_order.getText().toString();
                DatabaseReference ordref = database.getReference("orders/" +a);
                ordref.child("phn").setValue(b);
                ordref.child("design").setValue(aa);
                ordref.child("Width").setValue(width1.getText().toString());
                ordref.child("Length").setValue(length1.getText().toString());
                ordref.child("Total").setValue(pprice.getText().toString());






            }
        });




















        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                editor.putInt("flg", 0);
                editor.putString("phone", "");
                editor.putString("acc type","");
                editor.apply();
                startActivity(new Intent(tailordash.this, MainActivity.class));
                finish();
            }
        });




        DatabaseReference myRef = database.getReference("users/"+a);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String phn = dataSnapshot.child("Phone").getValue(String.class);
                String us = dataSnapshot.child("tailor").getValue(String.class);

                nam.setText(us);
                phon.setText(phn);

            }

            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });




    }
}
