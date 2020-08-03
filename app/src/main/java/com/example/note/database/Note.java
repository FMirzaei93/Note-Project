package com.example.note.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {

    //we can make all these fields public instead of private, so we don't need get methods. but for encapsulation we it's better to
    //use getter methods.

    @PrimaryKey(autoGenerate = true )
    private int id;

   //normally if we don't assign any especial name to the fields(the columns of our table), room will assign their names as the name.

    private String title;
    private String description;

    //@ColumnInfo(name = "priority_column")
    private int priority;



    //we don't give the id as an input of the constructor because as it's auto generated, we don't need to pass it every time for making an
    //object. but if it doesn't have id, room later can not make this object and return it to us. then we need to have a setter object for it.
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public int getId() {
        return id;
    }






}
