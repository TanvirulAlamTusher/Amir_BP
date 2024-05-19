package com.example.amirbp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amirbp.Adapter.ContactListAdapter;
import com.example.amirbp.Adapter.NoteAdapter;
import com.example.amirbp.Model.Note;
import com.example.amirbp.R;
import com.example.amirbp.ViewModel.ContactViewModel;
import com.example.amirbp.ViewModel.NoteViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityNoteBinding;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    ActivityNoteBinding binding;
    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        animation = AnimationUtils.loadAnimation(this, R.anim.click_animation);


        topbarTask();
        allButtonTask();


        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(NoteViewModel.class, new NoteViewModel(getApplication())).build();


        noteViewModel = new ViewModelProvider(NoteActivity.this, viewModelFactory).get(NoteViewModel.class);
        observedata();
        noteViewModel.callNoteListFromDB();

    }

    private void allButtonTask() {
        binding.noteAddBtn.setOnClickListener(view -> {
            startActivity(new Intent(NoteActivity.this, AddNoteActivity.class));
        });
    }

    private void observedata() {
        noteViewModel.getContactsList().observe(NoteActivity.this,notes -> {
            if (notes != null) {
                loadNoteList(notes);
            }
        });
        noteViewModel.getIs_inserted().observe(NoteActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Notes add successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadNoteList(List<Note> notes) {

        binding.recyclerviewId.setLayoutManager( new GridLayoutManager(NoteActivity.this,2));
        adapter = new NoteAdapter(NoteActivity.this, notes,animation);
        binding.recyclerviewId.setAdapter(adapter);
    }

    private void topbarTask() {
        binding.materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.callNoteListFromDB();

    }
}