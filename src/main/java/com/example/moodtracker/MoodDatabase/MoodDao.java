package com.example.moodtracker.MoodDatabase;

import android.arch.lifecycle.LiveData;
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

Room includes SQL validation at compile time to avoid runtime crashes.

*/

@Dao
public interface MoodDao {

    // Insert a MoodEntry to the database
    @Insert
    public void insertMoodEntry(MoodEntry moodEntry);

    // Query to get a full list of mood entries, ordered by date
    @Query("SELECT * FROM mood ORDER BY timestamp")
    public LiveData<List<MoodEntry>> getMoodEntries();

    // Query to get the mood entry associated with a particular ID
    // Prefix the id parameter in the SQL statement with a colon
    // to automatically connect the database parameter and the id variable
    @Query("SELECT * FROM mood WHERE id = :id")
    public LiveData<MoodEntry> getMoodWithId(int id);

    // Update an existing MoodEntry
    // If there is a conflict replace the old entry with the new value
    // returns the number of rows that were updated in the database.
    @Update(onConflict = OnConflictStrategy.REPLACE)
    public int updateMoodEntry(MoodEntry moodEntry);

    // Delete an existing MoodEntry
    // Returns the number of rows deleted
    @Delete
    public int delete(MoodEntry moodEntry);

    // Delete an existing MoodEntry with a specific ID
    @Query("DELETE FROM mood WHERE id = :id")
    public int deleteMoodEntryById(int id);

}



