package com.example.amirbp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.Model.Note;
import com.example.amirbp.R;
import com.example.amirbp.ViewModel.NoteViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityEditNoteBinding;

public class EditNoteActivity extends AppCompatActivity {
     ActivityEditNoteBinding binding;
     private NoteViewModel noteViewModel;

     int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);


        topbarTask();
        allButtonTask();

        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(NoteViewModel.class, new NoteViewModel(getApplication())).build();


        noteViewModel = new ViewModelProvider(EditNoteActivity.this, viewModelFactory).get(NoteViewModel.class);
        observedata();
        noteViewModel.getNoteById(noteId);

    }

    private void allButtonTask() {
        binding.updateBtnId.setOnClickListener(view -> {
            String title = binding.noteTitleId.getText().toString();
            String notes = binding.noteEdittextId.getText().toString();
           if(title.isEmpty()) {
               binding.noteTitleId.requestFocus();
               binding.noteTitleId.setError("Title Required!");
               return;
           }
            Note note = new Note();
           note.id = noteId;
           note.title = title;
           note.notes = notes;
           noteViewModel.updateNote(note);
           finish();

        });
        binding.cancleBtnId.setOnClickListener(view -> {
            finish();
        });
    }

    private void observedata() {
        noteViewModel.getContactInfoById().observe(EditNoteActivity.this,notes -> {
            if (notes != null) {
               binding.noteTitleId.setText(notes.getTitle());
               binding.noteEdittextId.setText(notes.getNotes());
            }
        });
        noteViewModel.getIs_updated().observe(EditNoteActivity.this, aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void topbarTask() {
        binding.materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}