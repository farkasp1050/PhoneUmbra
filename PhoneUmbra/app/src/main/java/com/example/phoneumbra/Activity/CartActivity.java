package com.example.phoneumbra.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phoneumbra.Adapter.CartAdapter;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.R;
import com.example.phoneumbra.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends BaseActivity {
    ActivityCartBinding binding;
    ArrayList<Phones> list = new ArrayList<>();
    private int quantity = 0;
    private CartAdapter adapter;
    private double tax = 0.1;
    private double shipping = 5.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setRecyclerView();
        calculateTotal();

        binding.buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishShopping();
            }
        });
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        Phones object = (Phones) intent.getSerializableExtra("object");
        quantity = intent.getIntExtra("quantity", 0);

        if (object != null) {
            for (int i = 0; i < quantity; i++) {
                list.add(object);
            }
        }

        binding.backBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setRecyclerView() {
        adapter = new CartAdapter(CartActivity.this, list);
        binding.cardView.setAdapter(adapter);
        binding.cardView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }

    private void calculateTotal() {
        double totalPrice = 0;
        for (Phones phone : list) {
            totalPrice += phone.getPrice();
        }
        double totalTax = totalPrice * tax;
        double totalShipping = shipping;
        double grandTotal = totalPrice + totalTax + totalShipping;

        binding.Price.setText("$" + totalPrice);
        binding.totalTax.setText("$" + totalTax);
        binding.totalDelivery.setText("$" + totalShipping);
        binding.totalPrice.setText("$" + grandTotal);
    }

    private void finishShopping() {
        // Sikeres megrendelés értesítés
        displayOrderNotification();

        list.clear();
        adapter.notifyDataSetChanged();
        calculateTotal();
        Intent intent = new Intent(CartActivity.this, PhoneListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void displayOrderNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, "OrderChannel")
                .setSmallIcon(R.drawable.cart)
                .setContentTitle("Order placed successfully!")
                .setContentText("Your order has been successfully placed.")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
    }
}
