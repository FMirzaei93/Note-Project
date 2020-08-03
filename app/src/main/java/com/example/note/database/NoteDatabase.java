package com.example.note.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    //We haven't made an instance yet, in the getInstance method, we will make it.
    //We create this variable because we have to turn this class to a singleton class. because we must have just one instance of this
    //class in the whole application and we're gonna use it throughout the application.
    //So that's why we declare it as static cause we can then access it over static variables.

    public abstract NoteDao noteDao();
    //output and name of the method.
    //this method is not going to have body, so that's why we define it as an abstract method,
    //By writing this abstract method Room will realize what to do and will take care of it.

    public static synchronized NoteDatabase getInstance(Context context){
        //synchronized means that only one thread at a time can access this method.

        if (instance==null){
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }



    //Here we want to add some values to our database first when it's created.
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        NoteDao noteDao;

        public PopulateDbAsyncTask(NoteDatabase db){

            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","Desc1",1));
            noteDao.insert(new Note("Title2","Desc2",2));
            noteDao.insert(new Note("Title3","Desc3",3));

            return null;
        }
    }


}
