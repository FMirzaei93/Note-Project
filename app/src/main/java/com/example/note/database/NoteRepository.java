package com.example.note.database;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

   private NoteDao noteDao;
   private LiveData<List<Note>> allNotes;

   //what is this repository class? it's a simple java class for catching data from database -or server-
    //so this way our ViewModel class doesn't have anything to do with catching data. it doesn't know where these information
    // are coming from. because repository class is handling this stuff and just simply passes it to ViewModel class.

    //Constructor
    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();

    }

    //Room will automatically execute the data that the database operations have set as LiveData in the background thread (like the getAllNotes method),
    //so we don't need to take care of them in a respective asyncTask class.
    //but for the other operations we need to execute the operations in the background thread by ourselves.
    //because Room doesn't allow database operations to be executed in the main thread.

    public void insert(Note note){
        //we need to make this method to be executed in the background thread, using AsyncTask class.
       new insertNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        //we need to make this method to be executed in the background thread, usn AsyncTask class.
        new deleteNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
        //we need to make this method to be executed in the background thread, usn AsyncTask class.
        new updateNotesAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        //we need to make this method to be executed in the background thread, usn AsyncTask class.
        new deleteAllNoteAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllNotes(){
       return allNotes;
       //since this method return a LiveData, it will be automatically excecuted in the backgroun thread.
    }


    private static class insertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        //it has to be static so it doesn't have a reference to the repository itself. otherwise it causes memory leak.

        //A static inner class is a nested class which is a static member of the outer class.
        // It can be accessed without instantiating the outer class, using other static members.
        //if the inner class "Wheel" is static, we can use it like : CarParts.Wheel wheel = new CarParts.Wheel();
        //otherwise we need an instance of the outer class for calling the inner class(That cause memory leak) like:
        //CarParts carParts = new CarParts(777);
        //CarParts.Wheel wheel = carParts.new Wheel();

        //A memory leak happens when your code allocates memory for an object, but never deallocates it.
        //deallocate:return (allocated memory) to the store of available RAM.

        NoteDao noteDao;
        //because this class is static we can not access the noteDao of our repository class (or any other non static var) directly so we need to pass it as a cunstructor.

        private insertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }


    }

    private static class deleteNoteAsyncTask extends AsyncTask<Note,Void, Void>{

        NoteDao noteDao;

        private deleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class updateNotesAsyncTask extends AsyncTask<Note,Void, Void>{

        NoteDao noteDao;

        private updateNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }



    private static class deleteAllNoteAsyncTask extends AsyncTask<Void,Void, Void>{

        NoteDao noteDao;

        private deleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
