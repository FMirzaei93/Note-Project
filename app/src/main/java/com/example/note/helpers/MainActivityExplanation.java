package com.example.note.helpers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;
import com.example.note.database.Note;
import com.example.note.database.NoteViewModel;

import java.util.List;

public class MainActivityExplanation extends AppCompatActivity{

    NoteViewModel noteViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        /*Setting it as true doesn't mean that the RecyclerView size is fixed, just means it won't change because of change in the adapter content.
        For example the RecyclerView size can change because of a size change on its parent.
        If the RecyclerView knows in advance that its size doesn't depend on the adapter content,
        then it will skip checking if its size should change every time an item is added or removed from the adapter.
         */
        //final NoteAdapter noteAdapter = new NoteAdapter();
        //recyclerView.setAdapter(noteAdapter);


        //we don't create an instance of NoteViewModel every time by calling noteViewModel = new NoteViewModel(), cause we're
        // not going to create a new instance in every new activity. instead we have to ask Android system for the ViewModel. because it
        //provides for us with an already existing instance.
        //This way we're going to get the existing instance:
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        //We pass "this". This way our ViewModel knows which activity or fragment  it has to be scoped to.
        //So when we pass our MainActivity as "this", Android system will destroy this instance of ViewModel as this activity has finished.
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //onChanged callBack method will be triggered every time our data in LiveData object changes.
                //This method should be triggered as soon as we start observing. so onChanged will be called as soon as we start
                //the app and go through onCreate.
                //it will be called whenever our activity is in the foreground and if we rotate the device or our activity is destroyed,
                //this will not hold a reference to our activity anymore.
                //these all happen automatically under the hood so we don't need to worry about it.(we don't need to stop updating data
                // when our activity is finished it the appropriate lifecycle.)

                //Update RecyclerView
                //noteAdapter.setNotes(notes);

            }
        });
        //LiveData is lifeCycle aware and it will only update our activity if it's on the foreground. when the activity is destroyed,
        //it will automatically clean up the reference to the activity. this way we avoid memory leaks or crashes.

    }
}