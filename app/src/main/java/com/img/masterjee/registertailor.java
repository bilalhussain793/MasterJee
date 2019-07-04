package com.img.masterjee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class registertailor extends AppCompatActivity {
    private EditText et_phone,et_username,et_email,et_password,et_confirmpass,et_year,refreledit;
    ArrayAdapter adapter_day,adapter_month;
    Button reg_btn;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String day,month,year,dob,nm,ph,em,pass,cnfm_pass,rf;
    ProgressDialog pd;
    ImageView iv;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertailor);

        et_username=findViewById(R.id.et_username);
        et_email=findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_password=findViewById(R.id.et_pass);
        et_confirmpass=findViewById(R.id.et_cp);
        refreledit=findViewById(R.id.refrel);
        iv=findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        // radioSexGroup=(RadioGroup)findViewById(R.id.r_group);

        reg_btn=findViewById(R.id.r_btn);



        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addusermethod();
            }
        });

    }

    private void addusermethod(){

        rf=refreledit.getText().toString();
        nm=et_username.getText().toString();
        em=et_email.getText().toString();
        ph=et_phone.getText().toString();
        pass=et_password.getText().toString();
        cnfm_pass=et_confirmpass.getText().toString();


        if(nm.length()==0){
            et_username.setError("Empty!");
        }else {
            if(em.length()==0){
                et_email.setError("Empty!");
            }else{
                if (pass.length()==0){
                    et_password.setError("Empty");
                }else {
                    if(cnfm_pass.length()==0){
                        et_confirmpass.setError("Empty");
                    }else {
                        if(ph.length()==0){
                            et_phone.setError("Empty");
                        }else{
//                            if(em.contains("@")&&em.contains(".com")){
                                if(pass.equals(cnfm_pass)){
// Write a message to the database
                                    pd=new ProgressDialog(registertailor.this);
                                    pd.setTitle("Loading....");
                                    pd.show();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("users/"+ph);

                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // This method is called once with the initial value and again
                                            // whenever data at this location is updated.
                                            String value = dataSnapshot.child("Email").getValue(String.class);
                                            String value2 = dataSnapshot.child("Phone").getValue(String.class);
                                            if(ph.equals(value2) ){

                                                et_phone.setError("Phone is already Registered");
                                                // Toast.makeText(Register.this, "Phone is already Registered", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                if(em.equals(value)){
                                                    et_email.setError("Email is already in use.");
                                                    //   Toast.makeText(Register.this, "Email is already in use.", Toast.LENGTH_SHORT).show();
                                                }else {

//                                                    int selectedId=radioSexGroup.getCheckedRadioButtonId();
//                                                    radioSexButton=(RadioButton)findViewById(selectedId);
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("users/"+ph);
                                                    myRef.child("acc type").setValue("Tailor");
                                                    myRef.child("tailor").setValue(nm);
                                                    myRef.child("shop").setValue(em);
                                                    //  myRef.child("Gender").setValue(radioSexButton);
                                                    myRef.child("Phone").setValue(ph);
                                                    myRef.child("Password").setValue(pass);
                                                    myRef.child("Refer To").setValue(rf);
                                                    uploadImage();
                                                    pd.dismiss();
                                                    finish();

                                                    startActivity(new Intent(registertailor.this,MainActivity.class));




                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Log.w("TAG", "Failed to read value.", error.toException());
                                        }
                                    });



                                }else {

                                    et_password.setError("Not Matched");
                                    et_confirmpass.setError("Not Matched");

                                }
//                            }else {
//                                et_email.setError("Email is not Correct");
//                                Toast.makeText(this, "Email is not correct", Toast.LENGTH_SHORT).show();
//                            }

                        }
                    }
                }
            }
        }
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                iv.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/tailor/"+ et_phone.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(registertailor.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(registertailor.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}


