package com.example.fecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Customer_Itemview_recyclerview extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<item_recyclerview> items;
    item_recyclerview_myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__itemview_recyclerview);
        items=new ArrayList<item_recyclerview>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Items").child("Vegetables");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("tag",dataSnapshot.toString());
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    item_recyclerview it =data.getValue(item_recyclerview.class);
                    items.add(it);
                }
                adapter=new item_recyclerview_myadapter(Customer_Itemview_recyclerview.this,items);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Customer_Itemview_recyclerview.this,"Something went wrong....",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
