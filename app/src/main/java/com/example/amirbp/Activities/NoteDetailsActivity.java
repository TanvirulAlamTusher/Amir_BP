package com.example.amirbp.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.R;
import com.example.amirbp.ViewModel.NoteViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityNoteDetailsBinding;

public class NoteDetailsActivity extends AppCompatActivity {
   ActivityNoteDetailsBinding binding;
    private NoteViewModel noteViewModel;

   private int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        topbarTask();


        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(NoteViewModel.class, new NoteViewModel(getApplication())).build();


        noteViewModel = new ViewModelProvider(NoteDetailsActivity.this, viewModelFactory).get(NoteViewModel.class);
        observedata();
        noteViewModel.getNoteById(noteId);




    }
    private void observedata() {
        noteViewModel.getContactInfoById().observe(NoteDetailsActivity.this,notes -> {
            if (notes != null) {
              binding.materialToolbar.setTitle(notes.getTitle());
              binding.noteTextTvId.setText(notes.getNotes());
            }
        });


    }
    private void topbarTask() {
        binding.materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        binding.materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.edit_id) {
                startActivity(new Intent(NoteDetailsActivity.this, EditNoteActivity.class).putExtra("noteId",noteId));
                return true;
            }if (item.getItemId() == R.id.delete_id) {
                new AlertDialog.Builder(NoteDetailsActivity.this)
                        .setTitle(R.string.warning)
                        .setCancelable(true)
                        .setMessage("Are you sure you want to delete this notes?")
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Delete", (dialog, which) -> {
                            noteViewModel.deleteNoteById(noteId);
                            dialog.dismiss();
                            finish();
                        })
                        .show();
                return true;
            }
            return false;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.getNoteById(noteId);
    }
}