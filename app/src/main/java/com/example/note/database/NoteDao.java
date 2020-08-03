package com.example.note.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();
    //when we don't mention *, it means all columns

    @Query("SELECT * FROM note_table ORDER BY priority Asc")
    LiveData<List<Note>> getAllNotes();

}


/*Difference From Class
Can not instantiate an interface and can't contain instance fields.
An interface has no constructors.
All of the methods in an interface are abstract.
All the fields that can appear in an interface must be declared both static and final.
(As we can not instantiate an interface, so the all variables must be static)
An interface can extend multiple interfaces.
*/



/*f you have used viewmodel along with LiveData class to store data then during orientation change,
 new instance of activity will be created but the data wont be downloaded again. Viewmodel will provide the most recent available data.
 LiveData is lifeCycle aware and it will only update our activity if it's on the foreground. when the activity is destroyed,
 it will automatically clean up the reference to the activity. this way we avoid memory leaks or crashes.
 onChange method will be called whenever our activity is in the foreground and if we rotate the device or our activity is destroyed,
 this will not hold a reference to our activity anymore.
 these all happen automatically under the hood so we don't need to worry about it.(we don't need to stop updating data
 when our activity is finished in the appropriate lifecycle.)


  Don’t think that viewmodel will hold data forever or for every case.
  If you close or activity is destoryed the viewmodel will also be destroyed or cleared.
  Because as i said in previous article that it has to address leakage also so its coupled with activity or
  fragment once if they are destroyed viewholder will also be cleared.

  ViewModel keeps the information as liveData but it's "instance" will be removed whenever the activity is destroyed.
  So when we rotate the phone(so the activity is destroyed), new instance of ViewModel is created, but there's no need for the
  data to be downloaded again cause ViewModel holds them as LiveData.*/