package com.img.masterjee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class History extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
TextView de,w,h;
Firebase firebase;
    String value0;
    TextView ordpend,ordappr;
    ListView lv;
    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        de=findViewById(R.id.des);
        w=findViewById(R.id.wi);
        h=findViewById(R.id.hi);
        Firebase.setAndroidContext(this);
lv=findViewById(R.id.lissst);

ordappr=findViewById(R.id.orderaccept);
ordpend=findViewById(R.id.Pendind);


ordappr.setVisibility(View.GONE);


        final SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        final String aa=prefs.getString("phone",null);
//        // Toast.makeText(this, ""+aa, Toast.LENGTH_SHORT).show();
//        final DatabaseReference myRef = database.getReference("designs/"+aa);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String a = dataSnapshot.child("Design1").getValue(String.class);
//                String b = dataSnapshot.child("Design2").getValue(String.class);
//                String c = dataSnapshot.child("Design3").getValue(String.class);
//                String d = dataSnapshot.child("Design4").getValue(String.class);
//                String e = dataSnapshot.child("Design5").getValue(String.class);
//
//
//
//
//                    }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }});
//
//
//        final DatabaseReference myRef2 = database.getReference("orders/"+aa);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//              //  String ph = dataSnapshot.child("phn").getValue(String.class);
//                String us = dataSnapshot.child("design").getValue(String.class);
//                //String tot = dataSnapshot.child("Total").getValue(String.class);
//                String wid = dataSnapshot.child("Width").getValue(String.class);
//                String len = dataSnapshot.child("Length").getValue(String.class);
//
//
//
//
//
//
//
//
//            }   public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//
//            }
//        });
//
//

        firebase=new Firebase("https://masterjee-d5ab5.firebaseio.com/designs/"+aa);

        adapter=new ArrayAdapter<String>(History.this,R.layout.support_simple_spinner_dropdown_item,arrayList);

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




        DatabaseReference accept = database.getReference("Accepted");
        accept.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 value0 = dataSnapshot.getValue(String.class);

                if(value0.equals(aa)){

                    ordappr.setVisibility(View.VISIBLE);
                    ordpend.setVisibility(View.GONE);
                }






            }   public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });




    }
}
