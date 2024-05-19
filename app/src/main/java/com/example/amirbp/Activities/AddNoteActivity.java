package com.example.amirbp.Activities;

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
import com.example.amirbp.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {
  ActivityAddNoteBinding binding;
  private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topbarTask();
        allButtonTask();


        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(NoteViewModel.class, new NoteViewModel(getApplication())).build();
        noteViewModel = new ViewModelProvider(AddNoteActivity.this, viewModelFactory).get(NoteViewModel.class);
        observedata();
        noteViewModel.callNoteListFromDB();

    }

    private void allButtonTask() {
        binding.saveBtnId.setOnClickListener(v -> {
            String title = binding.noteTitleId.getText().toString();
            String notes = binding.noteId.getText().toString();
            if (title.isEmpty()){
                binding.noteTitleId.requestFocus();
                binding.noteTitleId.setError("Title Required");
                return;
            }
            Note note = new Note(title,notes);
            noteViewModel.insertNote(note);
            finish();

        });
    }

    private void observedata() {
        noteViewModel.getIs_inserted().observe(AddNoteActivity.this,aBoolean -> {
            if (aBoolean != null) {
                if (aBoolean) {
                    Toast.makeText(this, "Note add successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void topbarTask() {
        binding.materialToolbar.setNavigationOnClickListener(v -> finish());
    }
}