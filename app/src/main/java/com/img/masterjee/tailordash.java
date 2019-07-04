package com.img.masterjee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class tailordash extends AppCompatActivity {
    Button logout_btn;
    ImageView imv;
    String userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailordash);
        logout_btn=findViewById(R.id.logout);


        imv=findViewById(R.id.imv);

        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        userphone = prefs.getString("phone", null);
        Picasso.with(tailordash.this).load("https://firebasestorage.googleapis.com/v0/b/masterjee-d5ab5.appspot.com/o/images%2F" +
                "tailor%2F"+userphone+"?alt=media&token=21487e8b-6ac2-4ee5-a820-e3e622f7c29f").into(imv);







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

        String a=prefs.getString("phone",null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/"+a);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String phn = dataSnapshot.child("Phone").getValue(String.class);
                String pass = dataSnapshot.child("Password").getValue(String.class);

                Toast.makeText(tailordash.this, ""+phn, Toast.LENGTH_SHORT).show();


            }

            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });




    }
}
