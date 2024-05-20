package com.example.amirbp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amirbp.Activities.ContactDetailsActivity;
import com.example.amirbp.Activities.EditNoteActivity;
import com.example.amirbp.Activities.NoteDetailsActivity;
import com.example.amirbp.Model.Contact;
import com.example.amirbp.Model.Note;
import com.example.amirbp.R;
import com.example.amirbp.databinding.NoteListLayoutBinding;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final Context context;
    private final List<Note> noteList;

    private final Animation animation;
    private AlertDialog alertDialog = null;

    public NoteAdapter(Context context, List<Note> noteList, Animation animation) {
        this.context = context;
        this.noteList = noteList;
        this.animation = animation;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.note_list_layout, parent, false);
        return new NoteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
          holder.binding.titleTvId.setText(noteList.get(position).getTitle());
          holder.binding.layoutId.setOnClickListener(view -> {
              holder.binding.layoutId.startAnimation(animation);

              int noteId = noteList.get(position).getId();
              context.startActivity(new Intent(context, NoteDetailsActivity.class).putExtra("noteId", noteId));

          });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NoteListLayoutBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NoteListLayoutBinding.bind(itemView);

        }
    }
}
