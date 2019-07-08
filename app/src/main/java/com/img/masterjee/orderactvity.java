package com.img.masterjee;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderactvity extends AppCompatActivity {
TextView design,phon;
ImageView im;
    String userphone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderactvity);
        design=findViewById(R.id.desiign);
        phon=findViewById(R.id.Phoone);
        im=findViewById(R.id.imgg);
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        userphone = prefs.getString("phone", null);
        final String a=prefs.getString("phone",null);


        DatabaseReference myRef = database.getReference("orders/"+a);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String ph = dataSnapshot.child("phn").getValue(String.class);
                String us = dataSnapshot.child("design").getValue(String.class);


                phon.setText(ph);
                design.setText(us);

            }   public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });





    }
}
