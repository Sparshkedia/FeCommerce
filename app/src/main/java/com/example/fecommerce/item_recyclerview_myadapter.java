package com.example.fecommerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class item_recyclerview_myadapter extends RecyclerView.Adapter<item_recyclerview_myadapter.Myviewholder> {


    Context context;
    ArrayList<item_recyclerview> arrayList;


    public item_recyclerview_myadapter(Context c,ArrayList p){
        context=c;
        arrayList=p;
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(context).inflate(R.layout.recyclerview_customer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

    holder.name.setText(arrayList.get(position).getName());
    holder.price.setText("Rs : "+arrayList.get(position).getPrice());
    holder.loc.setText(arrayList.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder{
        TextView name,loc,price;
        ImageView imageView;
        public Myviewholder(@NonNull View itemView) {

            super(itemView);
            //imageView=(ImageView)itemView.findViewById(R.id.imageView);
            name=(TextView)itemView.findViewById(R.id.I_name);
            loc=(TextView)itemView.findViewById(R.id.loc);
            price=(TextView)itemView.findViewById(R.id.price);


        }
    }
}
