package com.example.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note.database.Note;
import java.util.ArrayList;
import java.util.List;

//if it extend ListAdapter(!!which is not ListView.Adapter), we can use diffUtil method for comparing 2 different lists.

//it sends the list directly to the super class of ListAdapter class. So we don't need the lisrt anymore.

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {


    private OnItemClickListener onItemClickListener;

    public NoteAdapter() {
        super(diffCallback);
        //it implies to its the parent constructor.
    }


    private static final DiffUtil.ItemCallback<Note> diffCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();

            //it means if oldItem.getId() == newItem.getId(), return true. else return false.
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();

            //Wrong approach: return oldItem.equal(newItem);
            //Cause it's never true. because oldItem and newItem are different java objects and they are not the same objects .
            //LiveData always returns a new list, which means the newItem will never be the oldItem, even if the both have the same id.
        }
    };


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {


        Note currentNote = getItem(position);
        holder.txt_title.setText(currentNote.getTitle());
        holder.txt_desc.setText(currentNote.getDescription());
        holder.txt_priority.setText(String.valueOf(currentNote.getPriority()));


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
                    onItemClickListener.onItemClick(getItem(position));

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


    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }


    public Note getNoteAtPosition(int position){
        return getItem(position);
    }



}
