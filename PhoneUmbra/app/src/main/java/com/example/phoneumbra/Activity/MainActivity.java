package com.example.phoneumbra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.phoneumbra.Adapter.BestPhonesAdapter;
import com.example.phoneumbra.Domain.Brand;
import com.example.phoneumbra.Domain.Location;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.Domain.Price;
import com.example.phoneumbra.Domain.Time;
import com.example.phoneumbra.R;
import com.example.phoneumbra.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initLocation();
        initTime();
        initPrice();
        initBestPhone();
        initBrand();
        setVariable();
    }

    private void setVariable() {
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.searchResult.getText().toString();
                if(text.isEmpty()){
                    Intent intent = new Intent(MainActivity.this, PhoneListActivity.class);
                    intent.putExtra("text", text);
                    intent.putExtra("isSearch", true);
                    startActivity(intent);
                }
            }
        });
    }

    private void initBestPhone() {
        DatabaseReference myRef = database.getReference("Phones");
        binding.progressBarBestPhones.setVisibility(View.VISIBLE);
        ArrayList<Phones> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestPhone").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Phones.class));
                    }
                    if(list.size() > 0){
                        binding.bestPhonesView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new BestPhonesAdapter(list);
                        binding.bestPhonesView.setAdapter(adapter);
                    }
                    binding.progressBarBestPhones.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBrand() {
        DatabaseReference myRef = database.getReference("Brand");
        binding.progressBarBrand.setVisibility(View.VISIBLE);
        ArrayList<Brand> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Brand brand = issue.getValue(Brand.class);
                        // Teljes elérési útvonal hozzáadása az ImagePath-hez
                        brand.setImagePath("https://firebasestorage.googleapis.com/v0/b/phoneumbrafinal.appspot.com/o/" + brand.getImagePath() + "?alt=media&token=ac07c519-dcb9-495d-92cc-8661bcba75f5");
                        list.add(brand);
                    }
                    binding.progressBarBrand.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initLocation() {
        DatabaseReference myRef = database.getReference("Locations");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initPrice() {
        DatabaseReference myRef = database.getReference("Prices");
        ArrayList<Price> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Price price = new Price();
                        price.setId(Integer.parseInt(issue.child("Id").getValue().toString()));
                        price.setName(issue.child("Name").getValue().toString());
                        list.add(price);
                    }
                    ArrayAdapter<Price> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.priceSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initTime() {
        DatabaseReference myRef = database.getReference("Times");
        ArrayList<Time> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Time time = new Time();
                        time.setId(Integer.parseInt(issue.child("Id").getValue().toString()));
                        time.setName(issue.child("Name").getValue().toString());
                        list.add(time);
                    }
                    ArrayAdapter<Time> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.timeSp.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}