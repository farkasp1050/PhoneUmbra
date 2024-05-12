package com.example.phoneumbra.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.phoneumbra.Adapter.PhoneListAdapter;
import com.example.phoneumbra.Domain.Phones;
import com.example.phoneumbra.R;
import com.example.phoneumbra.databinding.ActivityPhoneListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhoneListActivity extends BaseActivity {
    ActivityPhoneListBinding binding;
    private RecyclerView.Adapter adapterListForPhones;
    private int brandId;
    private String brandName;
    private String searchText;
    private boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();

        initList();
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Phones");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Phones> list = new ArrayList<Phones>();

        Query query;
        if(isSearch){
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText+'\uf8ff');
        }else{
            query = myRef.orderByChild("BrandId").equalTo(brandId);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Phones phone = issue.getValue(Phones.class);
                        if(!isSearch && phone.getBrandId() == brandId) {
                            list.add(phone);
                        } else if(isSearch && phone.getTitle().contains(searchText)) {
                            list.add(phone);
                        }
                    }

                    if(list.size() > 0){
                        binding.phoneListView.setLayoutManager(new GridLayoutManager(PhoneListActivity.this, 2));
                        adapterListForPhones = new PhoneListAdapter(list);
                        binding.phoneListView.setAdapter(adapterListForPhones);
                    } else {
                        adapterListForPhones = new PhoneListAdapter(new ArrayList<>());
                        binding.phoneListView.setAdapter(adapterListForPhones);
                    }

                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getIntentExtra() {
        brandId = getIntent().getIntExtra("BrandId", 0);
        brandName = getIntent().getStringExtra("Brand");
        searchText = getIntent().getStringExtra("text");
        isSearch = getIntent().getBooleanExtra("isSearch", false);

        // Frissítjük a címet
        binding.titleText.setText(brandName);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
