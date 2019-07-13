package com.img.masterjee;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderactvity extends AppCompatActivity {
TextView design,phon,total,w,h,shldndr,szendr;
ImageView im;

Button btaa;

Button btn;
    String userphone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderactvity);
        design=findViewById(R.id.desiign);
        phon=findViewById(R.id.Phoone);
        im=findViewById(R.id.imgg);
        total=findViewById(R.id.Pricetotal);
        w=findViewById(R.id.www);
        h=findViewById(R.id.lengt);
        btaa=findViewById(R.id.click);

        shldndr=findViewById(R.id.shhhhh);
        szendr=findViewById(R.id.siiiiii);

        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        userphone = prefs.getString("phone", null);
        final String a=prefs.getString("phone",null);


        final DatabaseReference myRef = database.getReference("orders/"+a);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String ph = dataSnapshot.child("phn").getValue(String.class);
                String us = dataSnapshot.child("design").getValue(String.class);
                String tot = dataSnapshot.child("Total").getValue(String.class);
                String wid = dataSnapshot.child("Width").getValue(String.class);
                String len = dataSnapshot.child("Length").getValue(String.class);
                String sio = dataSnapshot.child("Size").getValue(String.class);
                String sho = dataSnapshot.child("Shoulder").getValue(String.class);

                phon.setText(ph);
                design.setText(us);
                total.setText(tot);
                w.setText(wid);
                h.setText(len);
                szendr.setText(sio);
                shldndr.setText(sho);






            }   public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });



btaa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        DatabaseReference ordref = database.getReference("orders/" +a);
        ordref.child("phn").setValue("");
        ordref.child("design").setValue("");
        ordref.child("Width").setValue("");
        ordref.child("Length").setValue("");
        ordref.child("Total").setValue("");

    }
});

    }
}
