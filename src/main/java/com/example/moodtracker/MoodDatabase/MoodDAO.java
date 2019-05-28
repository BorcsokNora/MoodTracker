package com.example.moodtracker.MoodDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/*
This is a Data Access Object (DAO) interface.
The CRUD operations on the database are defined here.
Annotations for simple operations are provided by Room, but we can also create custom queries.
Room handles the Cursor operations in the background and returns the requested data type.

To make a database operation: call the DAO object on the database instance, and then the suitable CRUD method on the DAO.
pl. myDatabase.moodDao.insertMoodEntry(moodEntry);


pl. :id will connect the id parameter with the id integer.


        ROOM includes SQL validation at compile time-so if there is a typo in our SQL statements our project won't compile and we will receive an error message (instead having our app crushed at runtime).

*/

@Dao
public interface MoodDAO {

    // Insert a MoodEntry to the database
    @Insert
    public void insertMoodEntry(MoodEntry moodEntry);

    // Update an existing MoodEntry
    // If there is a conflict replace the old entry with the new value
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateMoodEntry(MoodEntry moodEntry);

    // Delete an existing MoodEntry
    @Delete
    public void delete(MoodEntry moodEntry);

    // Query to get a full list of mood entries, ordered by date
    @Query("SELECT * FROM mood ORDER BY date")
    public List<MoodEntry> getMoodEntries();

    // Query to get the mood associated with a particular ID
    // Prefix the id parameter in the SQL statement with a colon
    // to automatically connect the database parameter and the id variable
    @Query("SELECT * FROM mood WHERE mood_id = :id")
    public MoodEntry getMoodWithId(int id);

}



