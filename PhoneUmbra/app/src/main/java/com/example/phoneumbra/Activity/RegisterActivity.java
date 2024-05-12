package com.example.phoneumbra.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.phoneumbra.R;
import com.example.phoneumbra.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends BaseActivity {
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        setVariable();
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setVariable() {
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.userEdt.getText().toString();
                String password = binding.passEdt.getText().toString();

                if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 character long!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i(TAG, "onComplete: ");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else{
                            Log.i(TAG, "onFailure: "+task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        binding.textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

}