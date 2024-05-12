package com.example.phoneumbra.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private ArrayList<Phones> mPhoneList;

    public CartAdapter(Context context, ArrayList<Phones> phoneList) {
        mContext = context;
        mPhoneList = phoneList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_phone_detail, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Phones currentPhone = mPhoneList.get(position);
        holder.title.setText(currentPhone.getTitle());
        holder.price.setText("$" + currentPhone.getPrice());
        Glide.with(mContext)
                .load(currentPhone.getImagePath())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mPhoneList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, price;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.titleText);
            price = itemView.findViewById(R.id.priceText);
        }
    }
}
