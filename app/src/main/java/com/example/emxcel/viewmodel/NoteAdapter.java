package com.example.emxcel.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emxcel.viewmodel.database.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prerak on 25/9/18.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<Note> notes = new ArrayList<>();
    private ViewModel viewModel;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Note note = notes.get(position);
        holder.tvPriority.setText(String.valueOf(note.getPriority()));
        holder.tvTitle.setText(note.getTitle());
        holder.tvDescription.setText(note.getDescription());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvPriority, tvDescription;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvPriority = itemView.findViewById(R.id.tvPriority);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
