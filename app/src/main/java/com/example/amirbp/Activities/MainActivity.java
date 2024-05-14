package com.example.amirbp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.amirbp.Adapter.ContactListAdapter;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.R;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ContactViewModel contactViewModel;
    private ContactListAdapter adapter;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animation = AnimationUtils.loadAnimation(this, R.anim.click_animation);


        allButtonTask();


        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(ContactViewModel.class, new ContactViewModel(getApplication())).build();

        contactViewModel = new ViewModelProvider(MainActivity.this, viewModelFactory).get(ContactViewModel.class);
        observedata();

        contactViewModel.callContactsListFromDB();


    }



    private void allButtonTask() {
     binding.contactAddBtn.setOnClickListener(view -> {
        startActivity(new Intent(MainActivity.this, ContactAddActivity.class));
     });
    }

    private void observedata() {
        contactViewModel.getContactsList().observe(MainActivity.this,contacts -> {
            if (contacts != null) {
                loadContactList(contacts);
            }
        });

        contactViewModel.getIs_delete().observe(MainActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact delete successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactViewModel.getIs_updated().observe(MainActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Contact update successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadContactList(List<Contact> contacts) {
        binding.recyclerviewId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(MainActivity.this, contacts,animation);
        binding.recyclerviewId.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        contactViewModel.callContactsListFromDB();
    }
}