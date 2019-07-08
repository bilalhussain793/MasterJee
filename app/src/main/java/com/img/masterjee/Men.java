package com.img.masterjee;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Men extends AppCompatActivity {
Button bt1,bt2,bt3,bt4,bt5;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men);
        bt1=findViewById(R.id.b);
        bt2=findViewById(R.id.design2);
        bt3=findViewById(R.id.design3);
        bt4=findViewById(R.id.design4);
        bt5=findViewById(R.id.design5);



        final SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        final String aa=prefs.getString("phone",null);
       // Toast.makeText(this, ""+aa, Toast.LENGTH_SHORT).show();
        final DatabaseReference myRef2 = database.getReference("designs/"+aa);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Men.this, "click", Toast.LENGTH_SHORT).show();



                myRef2.child("Design1").setValue("Design1");





            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef2.child("Design2").setValue("Design2");

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Design3").setValue("Design3");
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Design4").setValue("Design4");
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Design5").setValue("Design5" +
                        "");
            }
        });
    }
}
