package com.example.phoneumbra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.phoneumbra.Activity.PhoneDetailActivity;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.viewholder>{
    ArrayList<Phones> items;
    Context context;

    public PhoneListAdapter(ArrayList<Phones> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PhoneListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_phones, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneListAdapter.viewholder holder, int position) {
        holder.titleText.setText(items.get(position).getTitle());
        holder.timeText.setText(items.get(position).getTimeValue()+" min");
        holder.priceText.setText("$"+ items.get(position).getPrice());
        holder.starText.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhoneDetailActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleText, priceText, starText, timeText;
        ImageView image;
        public viewholder(@NonNull View itemView){
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            priceText = itemView.findViewById(R.id.priceText);
            starText = itemView.findViewById(R.id.starNumberText);
            timeText = itemView.findViewById(R.id.timeText);
            image = itemView.findViewById(R.id.img);
        }
    }
}
