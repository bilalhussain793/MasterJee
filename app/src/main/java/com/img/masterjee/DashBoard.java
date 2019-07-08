package com.img.masterjee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DashBoard extends AppCompatActivity {
Button logout_btn;
ImageView imv;
TextView nam,phon;
String userphone;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button men,women,kid,ready,unstich
        ,history,contactus,size,about;
    private int animationCounter = 1;
    private Handler imageSwitcherHandler;
    ImageSwitcher imageSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        logout_btn=findViewById(R.id.logout);
        imv=findViewById(R.id.imv);
        nam=findViewById(R.id.name1);
        phon=findViewById(R.id.phone1);

        men=findViewById(R.id.men);
        women=findViewById(R.id.women);
        kid=findViewById(R.id.kid);
        ready=findViewById(R.id.ready);
        unstich=findViewById(R.id.unstich);
        history=findViewById(R.id.his);
        contactus=findViewById(R.id.cnt);
        size=findViewById(R.id.sze);
        about=findViewById(R.id.abt);


        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(DashBoard.this,Men.class));
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); unstich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });















        final SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        userphone = prefs.getString("phone", null);

        Picasso.with(DashBoard.this).load("https://firebasestorage.googleapis.com/v0/b/masterjee-d5ab5.appspot.com/o/images%2F" +
                "tailor%2F"+userphone+"?alt=media&token=21487e8b-6ac2-4ee5-a820-e3e622f7c29f").into(imv);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putInt("flg", 0);

                editor.putString("phone", "");
                editor.putString("acc type","");
                editor.apply();
                startActivity(new Intent(DashBoard.this, MainActivity.class));
                finish();
            }
        });

        String a=prefs.getString("phone",null);

        DatabaseReference myRef = database.getReference("users/"+a);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String phn = dataSnapshot.child("Phone").getValue(String.class);
                String us = dataSnapshot.child("Name").getValue(String.class);

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