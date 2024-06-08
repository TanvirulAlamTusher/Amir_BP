package com.example.amirbp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.amirbp.Adapter.ContactListAdapter;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.R;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private ContactViewModel contactViewModel;
    private ContactListAdapter adapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<Contact> contactList;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animation = AnimationUtils.loadAnimation(this, R.anim.click_animation);
        navigationView = binding.navigationViewId;
        drawerLayout = binding.drawerLayoutId;

        setupNavigationDrawer();
        allButtonTask();
        searchViweTask();


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
                this.contactList = contacts;
            }
        });

    }

    private void loadContactList(List<Contact> contacts) {
        binding.recyclerviewId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(MainActivity.this, contacts,animation);
        binding.recyclerviewId.setAdapter(adapter);

    }
    private void setupNavigationDrawer() {
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.materialToolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.note_id) {
                 startActivity(new Intent(MainActivity.this,NoteActivity.class));

                    return true;
                }
                if(item.getItemId() == R.id.backup_restore_id){
                    startActivity(new Intent(MainActivity.this,BackupRestoreActivity.class));
                    return true;
                }
                if (item.getItemId() == R.id.developer_info_id){
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    View view = inflater.inflate(R.layout.about_alert_dailog, null);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setView(view);
                    alertDialog.create();
                    alertDialog.show();

                    return true;
                }
                return false;
            }
        });


    }
    public void searchViweTask(){
        binding.searchView.setOnClickListener(view -> {
          binding.searchView.setIconified(false);

        });
        binding.searchView.setQueryHint("Search");
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });
    }
    public void filterList(String text){
       List<Contact> filteredList = new ArrayList<>();
       for (Contact contact : contactList){
           if(contact.getName().toLowerCase().contains(text.toLowerCase())
                   || contact.getVillage().toLowerCase().contains(text.toLowerCase())
                   || contact.getPost_office().toLowerCase().contains(text.toLowerCase())
                   || contact.getFathers_name().toLowerCase().contains(text.toLowerCase())
                   || contact.getMother_husband_wife_name().toLowerCase().contains(text.toLowerCase())
                   || contact.getThana().toLowerCase().contains(text.toLowerCase())
                   || contact.getDristict().toLowerCase().contains(text.toLowerCase())
                   || contact.getCase_number().toLowerCase().contains(text.toLowerCase())
                   || contact.getBp_number().toLowerCase().contains(text.toLowerCase())
                   || contact.getMobile_number().toLowerCase().contains(text.toLowerCase())
                   || contact.getCurrent_workplace().toLowerCase().contains(text.toLowerCase())
                   || contact.getOld_workplace().toLowerCase().contains(text.toLowerCase())
                   || contact.getOld_workplace_2().toLowerCase().contains(text.toLowerCase())

           )
           {
               filteredList.add(contact);
           }
       }
       if(!filteredList.isEmpty()){
           adapter.filterList(filteredList);
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        contactViewModel.callContactsListFromDB();
    }
    @Override
    public void onBackPressed() {
        // Create a new dialog instance
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Are you sure you want to exit the app?");

        // Set up the positive button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Exit the activity
                finish();
            }
        });

        // Set up the negative button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}