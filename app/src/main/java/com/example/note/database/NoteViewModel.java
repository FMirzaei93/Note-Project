package com.example.note.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//This class doesn't have to do anything with the process of collecting data. (for example working with AsyncTask classes) and just
//have the data ready to use.
//ViewModel's only responsibility is to manage the data for the UI.
//The purpose of the ViewModel is to acquire and keep the information that is necessary for an Activity or a Fragment.
// The Activity or the Fragment should be able to observe changes that are in the ViewModel.
// ViewModels usually expose this information via LiveData or Android Data Binding.
//ViewModel will not be destroyed if its owner is destroyed for a configuration change (e.g. rotation or changing the font of the device)and
//The new instance of the owner(Activity or fragment) will just re-connected to the existing ViewModel.
public class NoteViewModel extends AndroidViewModel {
    NoteRepository repository;
    LiveData<List<Note>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
