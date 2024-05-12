package com.example.phoneumbra.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.databinding.ActivityPhoneDetailBinding;

public class PhoneDetailActivity extends BaseActivity {
    ActivityPhoneDetailBinding binding;
    private Phones object;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.backBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(PhoneDetailActivity.this)
                .load(object.getImagePath())
                .into(binding.picDetail);

        binding.priceTextDetail.setText("$"+object.getPrice());
        binding.titleTextDetail.setText(object.getTitle());
        binding.descTextDetail.setText(object.getDescription());
        binding.rateTextDetail.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTextDetail.setText((count*object.getPrice())+"$");

        binding.higherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                binding.quantityNumber.setText(Integer.toString(count));
                binding.totalTextDetail.setText((count*object.getPrice())+"$");
            }
        });

        binding.lowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    binding.quantityNumber.setText(Integer.toString(count));
                    binding.totalTextDetail.setText((count*object.getPrice())+"$");
                }
            }
        });

        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getIntentExtra() {
        object = (Phones) getIntent().getSerializableExtra("object");
        binding.backBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
