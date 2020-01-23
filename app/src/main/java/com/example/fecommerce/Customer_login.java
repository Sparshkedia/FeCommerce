package com.example.fecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Customer_login extends AppCompatActivity {

    private TextInputLayout email,pass;
    private Button sign;
    private FirebaseUser mcurrentuser;
    private DatabaseReference mdata;
    private ProgressDialog mprogress;
    private FirebaseAuth mAuth;
    private String c_pass,c_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        mAuth=FirebaseAuth.getInstance();
        mprogress=new ProgressDialog(this);
        sign=(Button)findViewById(R.id.signin);
        sign=(Button)findViewById(R.id.signin);
        email=(TextInputLayout) findViewById(R.id.email);
        pass=(TextInputLayout)findViewById(R.id.pass);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_pass=pass.getEditText().getText().toString().trim();
                c_email=email.getEditText().getText().toString().trim();
                if(c_pass.isEmpty()||c_email.isEmpty()){
                    Toast.makeText(Customer_login.this,"One or more fields are empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogress.setTitle("Please Wait");
                    mprogress.show();
                    login();
                }
            }
        });
    }

    private void login() {

        mAuth.signInWithEmailAndPassword(c_email,c_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            mcurrentuser= FirebaseAuth.getInstance().getCurrentUser();
                            final String uid = mcurrentuser.getUid();
                            mdata = FirebaseDatabase.getInstance().getReference().child("Customers").child(uid);

                            mdata.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.getChildrenCount()==0){
                                        mprogress.dismiss();
                                        mAuth.signOut();
                                        Toast.makeText(Customer_login.this,"Please login from farmers Login...",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Customer_login.this,MainActivity.class));

                                    }
                                    else{
                                        Intent intent=new Intent(Customer_login.this,Customer_Itemview_recyclerview.class);
                                        startActivity(intent);
                                        finish();
                                        mprogress.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(Customer_login.this,"Something went wrong....",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage()+" Check email and password again.",Toast.LENGTH_LONG).show();
                            pass.getEditText().setText("");
                            mprogress.hide();
                        }
                    }
                });

    }
}
