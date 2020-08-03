package com.example.note.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;
import com.example.note.database.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //if we want to use the "setOnItemClickListener" method in MainActivity, we don't need to have this construction.
    public NoteAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {


        Note currentNote = notes.get(position);
        holder.txt_title.setText(currentNote.getTitle());
        holder.txt_desc.setText(currentNote.getDescription());
        holder.txt_priority.setText(String.valueOf(currentNote.getPriority()));


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }



    class NoteHolder extends RecyclerView.ViewHolder{

        private TextView txt_title,txt_desc,txt_priority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            //itemView is the whole cardView

            txt_title= itemView.findViewById(R.id.txt_title);
            txt_desc= itemView.findViewById(R.id.txt_desc);
            txt_priority= itemView.findViewById(R.id.txt_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (onItemClickListener!=null && position!= RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(notes.get(position));

                }
            });

        }

    }

    //creating an interface for sending the information(note) to the activity.
    public interface OnItemClickListener {
        void onItemClick(Note note);
        //This method (Event) works as an callback, it means when it's called here, it also will be called in somewhere else that we
        //have implemented it's owner(interface).
    }


   /* public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }*/



    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAtPosition(int position){
        return notes.get(position);
    }



}
