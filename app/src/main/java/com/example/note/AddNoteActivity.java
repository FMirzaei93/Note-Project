package com.example.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.note.database.Note;
import com.example.note.database.NoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

    EditText editTextTitle, editTextDescription;
    NumberPicker numberPickerPriority;
    NoteViewModel noteViewModel;
    Intent intent;
    Note clickedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edt_title);
        editTextDescription = findViewById(R.id.edt_description);
        numberPickerPriority = findViewById(R.id.nrp_priorty);

        configuration();

    }

    private void configuration(){

        setNumberPickerValues();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //it needs to set our previous activity that we are moving to, to android:launchMode="singleTop" in order to make it as the top activity.
        setinitialvalues();
    }

    private void setNumberPickerValues(){
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
    }

    private void setinitialvalues(){

        //set title for the ActionBar
        setTitle("Add Note");

        intent = getIntent();
        if (intent.hasExtra("isEdit")){
            clickedNote = (Note)intent.getSerializableExtra("ClickedNote");
            setTitle("Edit Note");

            editTextTitle.setText(clickedNote.getTitle());
            editTextDescription.setText(clickedNote.getDescription());
            numberPickerPriority.setValue(clickedNote.getPriority());
        }

    }

    private void saveOrEditNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please insert a title and description",Toast.LENGTH_SHORT).show();
            return;
            //means to leave this method.
        }

        Note note = new Note(title,description,priority);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        //in order to make changes to MainActivity, we need to access ViewModel Class, and then MainActivity observes the data and update the UI.

        if (intent.hasExtra("isEdit")) {
            note.setId(clickedNote.getId());
            noteViewModel.update(note);
            Toast.makeText(this, "Note was successfully edited:)", Toast.LENGTH_SHORT).show();
        }
        else {
            noteViewModel.insert(note);
            Toast.makeText(this, "Note was successfully added:)", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveOrEditNote();

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}