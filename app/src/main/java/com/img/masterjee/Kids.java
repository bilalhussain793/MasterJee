package com.img.masterjee;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Kids extends AppCompatActivity {

    Button bt1,bt2,bt3,bt4,bt5,place,confirm,f5;
    TextView selet,tot,cu;
    EditText et,lent,wid;

    int s=0;
    int r=0;
    LinearLayout ln;
    String Length,Width;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        bt1=findViewById(R.id.b);
        f5=findViewById(R.id.Fashion5);
        bt2=findViewById(R.id.design2);
        bt3=findViewById(R.id.design3);
        bt4=findViewById(R.id.design4);
        bt5=findViewById(R.id.design5);
        selet=findViewById(R.id.selected);
        et=findViewById(R.id.no);
        place=findViewById(R.id.cc);
        tot=findViewById(R.id.total);
        lent=findViewById(R.id.len);
        wid=findViewById(R.id.width);
        confirm=findViewById(R.id.ok);
        ln=findViewById(R.id.layout);
        cu=findViewById(R.id.current);
        ln.setVisibility(View.GONE);
        final SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("LOGIN", MODE_PRIVATE);
        final String aa=prefs.getString("phone",null);
        // Toast.makeText(this, ""+aa, Toast.LENGTH_SHORT).show();
        final DatabaseReference myRef2 = database.getReference("designs/"+aa);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Kids.this, "click", Toast.LENGTH_SHORT).show();

                myRef2.child("Kids Fashion1").setValue("Kids Fashion1");

                cu.setText("1000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Fashion1");
                ln.setVisibility(View.VISIBLE);

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef2.child("Kids Fashion2").setValue("Kids Fashion2");
                selet.append("Kids Fashion2");
                ln.setVisibility(View.VISIBLE);

                cu.setText("2000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");


            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Kids Fashion3").setValue("Kids Fashion3");
                selet.append("Kids Fashion3");
                ln.setVisibility(View.VISIBLE);


                cu.setText("5000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");


            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Kids Fashion4").setValue("Kids Fashion4");
                cu.setText("9000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Kids Fashion4");
                ln.setVisibility(View.VISIBLE);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Kids Fashion5").setValue("Kids Fashion5");
                cu.setText("7000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Kids Fashion5");
                ln.setVisibility(View.VISIBLE);
            }
        });

        f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child("Kids Fashion5").setValue("Kids Fashion5");
                cu.setText("9000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Kids Fashion5");
                ln.setVisibility(View.VISIBLE);
            }
        });

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pno=et.getText().toString();
                if(pno.length()==0){
                    et.setError("Empty!");
                }else {



                    myRef2.child("Payout").setValue(pno);

                    Toast.makeText(Kids.this, "Order placed", Toast.LENGTH_SHORT).show();
                    et.setText("");
                    selet.setText("");

                }




            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Length=lent.getText().toString();
                Width=wid.getText().toString();
                if(Length.length()==0&&Width.length()==0){
                    et.setError("Empty!");
                }else {
                    myRef2.child("Length").setValue(Length);
                    myRef2.child("Width").setValue(Width);
                    myRef2.child("Total").setValue(tot.getText().toString());
                    ln.setVisibility(View.GONE);
                    Toast.makeText(Kids.this, "Size Confirmed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
