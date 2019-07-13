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

public class Men extends AppCompatActivity {
Button bt1,bt2,bt3,bt4,bt5,place,confirm;
TextView selet,tot,cu;
EditText et,lent,wid,siz,shou;

int s=0;
int r=0;
LinearLayout ln;
String Length,Width,siz1,shou1 ;
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
selet=findViewById(R.id.selected);
et=findViewById(R.id.no);
place=findViewById(R.id.cc);
tot=findViewById(R.id.total);
lent=findViewById(R.id.len);
wid=findViewById(R.id.width);
confirm=findViewById(R.id.ok);
ln=findViewById(R.id.layout);
cu=findViewById(R.id.current);


siz=findViewById(R.id.size);
shou=findViewById(R.id.sholder);




ln.setVisibility(View.GONE);
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

                cu.setText("1000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Design1");
                ln.setVisibility(View.VISIBLE);

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef2.child("Design2").setValue("Design2");
                selet.append("Design2");
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
                myRef2.child("Design3").setValue("Design3");
                selet.append("Design3");
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
                myRef2.child("Design4").setValue("Design4");
                cu.setText("9000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Design4");
                ln.setVisibility(View.VISIBLE);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef2.child("Design5").setValue("Design5");
                cu.setText("7000");
                r=s+(Integer.parseInt(cu.getText().toString()));
                s=r;
                tot.setText(r+"");

                selet.append("Design5");
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


                    Toast.makeText(Men.this, "Order placed", Toast.LENGTH_SHORT).show();
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
                siz1 =siz.getText().toString();
                shou1=shou.getText().toString();


                if(Length.length()==0&&Width.length()==0&&siz1.length()==0&&shou1.length()==0){
                    et.setError("Empty!");
                }else {
                    myRef2.child("Length").setValue(Length);
                    myRef2.child("Width").setValue(Width);
                    myRef2.child("Total").setValue(tot.getText().toString());
                    myRef2.child("Shoulder").setValue(shou1);
                    myRef2.child("Size").setValue(siz1);
                    ln.setVisibility(View.GONE);
                    Toast.makeText(Men.this, "Size Confirmed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
