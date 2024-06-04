package com.example.amirbp.Activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.amirbp.Model.Note;
import com.example.amirbp.R;
import com.example.amirbp.ViewModel.NoteViewModel;
import com.example.amirbp.ViewModel.ViewModelFactory;
import com.example.amirbp.databinding.ActivityNoteDetailsBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteDetailsActivity extends AppCompatActivity {
   ActivityNoteDetailsBinding binding;
    private NoteViewModel noteViewModel;

   private int noteId;
    private ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        // Initialize executorService
        executorService = Executors.newSingleThreadExecutor();

        topbarTask();


        ViewModelFactory viewModelFactory = new ViewModelFactory.Builder()
                .add(NoteViewModel.class, new NoteViewModel(getApplication())).build();


        noteViewModel = new ViewModelProvider(NoteDetailsActivity.this, viewModelFactory).get(NoteViewModel.class);
        observedata();
        noteViewModel.getNoteById(noteId);

        setupSearchView();

    }
    private void observedata() {
        noteViewModel.getNoteInfoById().observe(NoteDetailsActivity.this,notes -> {
            if (notes != null) {
              binding.materialToolbar.setTitle(notes.getTitle());
              binding.noteTextTvId.setText(notes.getNotes());
            }
        });


    }
    private void setupSearchView() {
        binding.searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                highlightText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                highlightText(newText);
                return false;
            }
        });
    }

    private void highlightText(String searchText) {
        String originalText = binding.noteTextTvId.getText().toString();
        if (searchText.isEmpty()) {
            binding.noteTextTvId.setText(originalText);
            return;
        }

        SpannableString spannableString = new SpannableString(originalText);
        String lowerOriginalText = originalText.toLowerCase();
        String lowerSearchText = searchText.toLowerCase();

        int startIndex = lowerOriginalText.indexOf(lowerSearchText);
        while (startIndex >= 0) {
            int endIndex = startIndex + searchText.length();
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = lowerOriginalText.indexOf(lowerSearchText, endIndex);
        }

        binding.noteTextTvId.setText(spannableString);
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
            if (item.getItemId() == R.id.download_pdf) {
                noteViewModel.getNoteInfoById().observe(this, note -> {
                    if (note != null) {
                        generatePDF(note);
                    }
                });
                return true;
            }
            return false;
        });
    }
//    private void generatePDF(Note note) {
//        PdfDocument pdfDocument = new PdfDocument();
//        Paint paint = new Paint();
//        Paint titlePaint = new Paint();
//
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
//        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//
//        Canvas canvas = page.getCanvas();
//        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//        titlePaint.setTextSize(24);
//        titlePaint.setColor(ContextCompat.getColor(this, R.color.black));
//
//        int x = 10, y = 25;
//
//        canvas.drawText(note.getTitle(), x, y, titlePaint);
//        y += 30;
//        paint.setTextSize(12);
//
//        canvas.drawText( note.getNotes(), x, y, paint);
//        y += 20;
//        paint.setTextSize(8);
//
//
//
//        pdfDocument.finishPage(page);
//
//        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        File file = new File(directoryPath, "Note.pdf");
//
//        try {
//            pdfDocument.writeTo(new FileOutputStream(file));
//            Toast.makeText(this, "PDF saved in Downloads folder", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error while saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        pdfDocument.close();
//    }
private void generatePDF(Note note) {
    executorService.execute(() -> {
        PdfDocument pdfDocument = new PdfDocument();
        Paint titlePaint = new Paint();
        Paint textPaint = new Paint();

        try {
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(24);
            titlePaint.setColor(ContextCompat.getColor(NoteDetailsActivity.this, R.color.black));

            textPaint.setTextSize(14);
            textPaint.setColor(ContextCompat.getColor(NoteDetailsActivity.this, R.color.black));

            int pageWidth = 595;
            int pageHeight = 842;
            int margin = 10;
            int y = 25;

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            canvas.drawText(note.getTitle(), margin, y, titlePaint);
            y += 30;

            String text = note.getNotes();
            int textHeight = (int) (textPaint.descent() - textPaint.ascent());
            int lineHeight = textHeight + 10; // 10 is the line spacing

            String[] lines = text.split("\n");
            for (String line : lines) {
                if (y + lineHeight > pageHeight - margin) {
                    pdfDocument.finishPage(page);
                    pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pdfDocument.getPages().size() + 1).create();
                    page = pdfDocument.startPage(pageInfo);
                    canvas = page.getCanvas();
                    y = margin;
                }
                canvas.drawText(line, margin, y, textPaint);
                y += lineHeight;
            }

            pdfDocument.finishPage(page);

            String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(directoryPath, "Note.pdf");

            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                pdfDocument.writeTo(outputStream);
                runOnUiThread(() -> Toast.makeText(NoteDetailsActivity.this, "PDF saved in Downloads folder", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                Log.e("PDF Generation", "Error while saving PDF", e);
                runOnUiThread(() -> Toast.makeText(NoteDetailsActivity.this, "Error while saving PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                pdfDocument.close();
            }
        } catch (Exception e) {
            Log.e("PDF Generation", "An error occurred", e);
            runOnUiThread(() -> Toast.makeText(NoteDetailsActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    });
}


    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.getNoteById(noteId);
    }
}