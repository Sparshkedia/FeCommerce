package com.example.fecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Customer_Registration extends AppCompatActivity {

    private TextInputLayout name,mobile,address,email,pass;
    private Button sign;
    private String c_name,c_email,c_mobile,c_address,c_pass;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;
    private ProgressDialog mprogress;
    private String ud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__registration);

// storing refernce of each textfield/buttons
        mAuth=FirebaseAuth.getInstance();
        mprogress=new ProgressDialog(this);
        sign=(Button)findViewById(R.id.signin);
        email=(TextInputLayout) findViewById(R.id.email);
        name=(TextInputLayout)findViewById(R.id.name);
        mobile=(TextInputLayout)findViewById(R.id.mobile);
        address=(TextInputLayout)findViewById(R.id.address);
        pass=(TextInputLayout)findViewById(R.id.pass);

//adding event listener on signin button
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c_pass=pass.getEditText().getText().toString().trim();
                c_name=name.getEditText().getText().toString().trim();
                c_email=email.getEditText().getText().toString().trim();


                c_address=address.getEditText().getText().toString().trim();

                c_mobile=mobile.getEditText().getText().toString().trim();

                if(c_address.isEmpty()||c_mobile.isEmpty()||c_name.isEmpty()||c_pass.isEmpty()||c_email.isEmpty()){
                    Toast.makeText(Customer_Registration.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogress.setTitle("Signing Up");
                    mprogress.show();
                    signin();
                }
            }
        });
    }
//doing sigin and storing details of user in firebase
    private void signin() {

        mAuth.createUserWithEmailAndPassword(c_email, c_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //mprogress.hide();//added

                            FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                            ud=currentuser.getUid();

                            mdatabase= FirebaseDatabase.getInstance().getReference().child("Customers").child(ud);

                            HashMap<String ,String> map=new HashMap<>();
                            map.put("name",c_name);
                            map.put("address",c_address);
                            map.put("mobile",c_mobile);


                            mdatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        mprogress.dismiss(); //added
                                        Intent i = new Intent(Customer_Registration.this,Customer_login.class);
                                        startActivity(i);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                            mprogress.hide();
                            Log.e("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
