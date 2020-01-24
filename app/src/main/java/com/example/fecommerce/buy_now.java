package com.example.fecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class buy_now extends AppCompatActivity {
    private Spinner spinner;
    TextView item_name,price;
    ImageView imageView;
    Button buy,add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);
        spinner=(Spinner)findViewById(R.id.qty);
        item_name=(TextView)findViewById(R.id.item_name);
        price=(TextView)findViewById(R.id.price);
        buy=(Button)findViewById(R.id.buy);
        add=(Button)findViewById(R.id.add);
        imageView=(ImageView)findViewById(R.id.imageView);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        //Log.e("tag",intent.getStringExtra("price"));
        String pric=intent.getStringExtra("price");
        //long w = Long.parseLong(intent.getStringExtra("weight"));
        item_name.setText(name);
        price.setText("Rs : "+pric+"/KG");


        String[] items=new String[500];
        for(int i=1;i<=500;i++){
            items[i-1]=String.valueOf(i);

        }

        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items));

    }
}
