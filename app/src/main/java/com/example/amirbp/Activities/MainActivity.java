package com.example.amirbp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.amirbp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        allButtonTask();




    }

    private void allButtonTask() {
     binding.contactAddBtn.setOnClickListener(view -> {
        startActivity(new Intent(MainActivity.this, ContactAddActivity.class));
     });
    }
}