package com.img.masterjee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button bt_login;
    EditText et_pn, et_ps;
    CheckBox checkBox;

    TextView reg_btn;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_pn = findViewById(R.id.et_phone_login);
        et_ps = findViewById(R.id.et_pass_login);

        bt_login = findViewById(R.id.bt_login);
        reg_btn = findViewById(R.id.tv_reg);

        checkBox = findViewById(R.id.cbx);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validlogin();

            }
        });



        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        int r = prefs.getInt("flg", 0);


        if (r == 2) {
            startActivity(new Intent(new Intent(MainActivity.this,DashBoard.class)));
            finish();
        }else{
            Toast.makeText(this, "Login here", Toast.LENGTH_SHORT).show();
        }
        String a=prefs.getString("acc type",null);


        if (a.equals("Tailor")) {

            startActivity(new Intent(MainActivity.this, tailordash.class));
        }
        if(a.equals("User"))
        {
            startActivity(new Intent(MainActivity.this, DashBoard.class));
            pd.dismiss();
        }







        reg_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
startActivity(new Intent(MainActivity.this,reguser.class));
    }
});
    }

    public void validlogin() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users/" + et_pn.getText().toString());
        pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Loading....");
        pd.show();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String phn = dataSnapshot.child("Phone").getValue(String.class);
                String pass = dataSnapshot.child("Password").getValue(String.class);
                String type = dataSnapshot.child("acc type").getValue(String.class);



                    if (et_pn.getText().toString().equals(phn)) {

                        if (et_ps.getText().toString().equals(pass)) {


                            if (checkBox.isEnabled()) {
                                SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                                editor.putInt("flg", 2);
                                editor.putString("phone", et_pn.getText().toString());
                                editor.putString("acc type",type);
                                editor.apply();







                                if (type.equals("Tailor")) {

                                    startActivity(new Intent(MainActivity.this, tailordash.class));
                                }
                                if(type.equals("User"))
                                {
                                    startActivity(new Intent(MainActivity.this, DashBoard.class));
                                    pd.dismiss();
                                }

                            }
                            else {
                                SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                                editor.putString("phone", et_pn.getText().toString());
                                editor.putString("acc type",type);
                                editor.apply();



                                if (type.equals("Tailor")) {

                                   startActivity(new Intent(MainActivity.this, tailordash.class));
                                }
                                if(type.equals("User"))
                                {
                                    startActivity(new Intent(MainActivity.this, DashBoard.class));
                                    pd.dismiss();
                                }
                            }
                        } else {

                            et_ps.setError("Wrong Password");
                            pd.dismiss();
                        }

                    } else {
                        et_pn.setError("Invalid Contact");
                        pd.dismiss();
                    }
//
  //              }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
                et_pn.setError("Invalid User");
            }
        });

    }



}