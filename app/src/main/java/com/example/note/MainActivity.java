package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.note.database.Note;
import com.example.note.database.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.button_add_note);
        recyclerView = findViewById(R.id.recycler_view);
        noteAdapter = new NoteAdapter();
        //here "this" means NoteAdapter.OnItemClickListener.
        //if we want "this" to mean MainActivity, we have to write MainActivity.this instead.

        configuration();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIntent(MainActivity.this);
            }
        });

    }

    private void configuration(){
        setRecyclerView();
        observingList();
        recyclerViewOnItemClick();
        recyclerOnSwipeItems();
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteAdapter);
    }

    private void observingList(){

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //MainActivity(as the owner of the lifecycle) is observing the list of notes which is in form of LiveData and whenever it changes,
        // onChange method of LiveData class update the view.
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            //it means that our list(noteViewModel.getAllNotes())-that's in the form of LiveData- starts being observed by
            // "this" (MainActivity) the first time that onCreate is called.
            //And as we know, LiveData is lifeCycle aware. So it stops updating our list when it's in the background.
            @Override
            public void onChanged(List<Note> noteList) {
                //it will be called whenever the activity is in the foreground **and** also there is new data to be updated.

                //Update RecyclerView
                noteAdapter.submitList(noteList);

            }
        });
    }

    private void recyclerOnSwipeItems(){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted!",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);
    }

    private void recyclerViewOnItemClick(){
   //in classA(MainActivity), how we start listening for whatever classB(NoteAdapter) has to tell

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("ClickedNote",note);
                intent.putExtra("isEdit",true);
                startActivity(intent);
            }
        });
    }

  /*  @Override
    public void onItemClick(Note note) {

        //It says when this method is performed in NoteAdapter class(in itemView.setOnClickListener)(and it's inputs are populated),
        // what should be done here.
        //in classA(MainActivity), how we start listening for whatever classB(NoteAdapter) has to tell

        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
        intent.putExtra("ClickedNote",note);
        intent.putExtra("isEdit",true);
        startActivity(intent);
    }
*/
    private void setIntent(Context context){
        Intent intent = new Intent(context, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this,"All notes were deleted!",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}


//What is a listener? it's an interface in View class that make interactions between user and a view possible.
//there are couple of this listeners like OnClickListener.
//for using this listener, like other interfaces there are 2 ways:
// 1.the class implements the interface and override it's abstract(without body) method(s).
//2.using it as an input of setOnClickListener method:
//
//           class.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               //it's the method of View.OnClickListener() interface.
//            }
//        });