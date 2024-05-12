package com.example.phoneumbra.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
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

import java.util.ArrayList;

public class BestPhonesAdapter extends RecyclerView.Adapter<BestPhonesAdapter.viewholder> {
    ArrayList<Phones> items;
    Context context;

    public BestPhonesAdapter(ArrayList<Phones> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestPhonesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_best_deal, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Phones phone = items.get(position);
        holder.titleText.setText(phone.getTitle());
        holder.priceText.setText("$" + phone.getPrice());
        holder.timeText.setText(phone.getTimeValue() + " min");
        holder.starText.setText("" + phone.getStar());

        Glide.with(context)
                .load(phone.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhoneDetailActivity.class);
                intent.putExtra("object", phone);
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
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            priceText = itemView.findViewById(R.id.priceText);
            starText = itemView.findViewById(R.id.starText);
            timeText = itemView.findViewById(R.id.timeText);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
