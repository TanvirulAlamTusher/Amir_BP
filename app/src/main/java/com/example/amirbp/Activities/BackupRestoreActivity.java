package com.example.amirbp.Activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.amirbp.DB.AppDb;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.Model.Note;
import com.example.amirbp.R;
import com.example.amirbp.Utils.BackupUtils;
import com.example.amirbp.databinding.ActivityBackupRestoreBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BackupRestoreActivity extends AppCompatActivity {
   ActivityBackupRestoreBinding binding;

    private AppDb appDataBase;
    BackupUtils backupUtils;
    private ActivityResultLauncher<String> filePickerLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBackupRestoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the ActivityResultLauncher
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        restoreBackup(uri);
                    }
                });

        appDataBase = AppDb.getInstance(BackupRestoreActivity.this);
        backupUtils = BackupUtils.getInstance(BackupRestoreActivity.this);

        topbar();
        allButtonTask();

    }

    private void allButtonTask() {
        binding.backupBattonId.setOnClickListener(v -> {
            List<Contact> contacts =  appDataBase.contactDao().getAllContact();
            List<Note> notes =  appDataBase.noteDao().getAllNote();


            backupUtils.saveAllBackup(contacts, notes, new BackupUtils.BackupCallback() {
                @Override
                public void onSuccess(boolean b) {
                    if(b == true){
                        Snackbar.make(findViewById(android.R.id.content), "Backup Successfull", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.parseColor("#388E3C"))
                                .show();

                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.parseColor("#FF0000"))
                                .show();

                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(BackupRestoreActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });
        binding.restoreFileButtonId.setOnClickListener(v -> {
            // Launch the file picker to let the user choose the backup file
            filePickerLauncher.launch("application/json");

        });
    }

    private void restoreBackup(Uri backupUri) {
        BackupUtils backupUtils = BackupUtils.getInstance(BackupRestoreActivity.this);
        backupUtils.restoreBackup(backupUri, new BackupUtils.RestoreCallback() {
            @Override
            public void onSuccess() {
                Snackbar.make(findViewById(android.R.id.content), "Restore Successful", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#388E3C"))
                        .show();
            }

            @Override
            public void onNoDataToRestore() {
                Snackbar.make(findViewById(android.R.id.content), "No Data to Restore", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#FFA000"))
                        .show();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Restore Error: " + e.getMessage(), Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.parseColor("#FF0000"))
                        .show();
            }
        });

    }

    private void topbar() {
        binding.materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}