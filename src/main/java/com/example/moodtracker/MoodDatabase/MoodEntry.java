package com.example.moodtracker.MoodDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.example.moodtracker.Utilities.MoodUtilities;


/**
 * The mood database has only one table.
 * The MoodEntry class serves as the schema of the database
 * <p>
 * MoodEntry: one entry contains all the data of one day's mood
 * The annotations (@...) are creating the database table structure
 *
 * @Entity: defines the table name and the database entity - which is a MoodEntry object in this case.
 * @ColumnInfo: it sets the name of the column.
 * (If the field has the correct name for the column, this annotation is unnecessary).
 * @Ignore: Room will skip to add these items to the database
 */

@Entity(tableName = "mood")
public class MoodEntry {

    //todo: refactor code to use date as a primary key!!

    // The unique ID of the database entry
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int entryId;

    @ColumnInfo(name = "mood_id")
    // The ID of the mood that defines which mood was saved for that day
    private int moodId;

    @ColumnInfo(name = "timestamp")
    // The date of the saved mood
    private Long timeOfMood;

    @ColumnInfo(name = "notes")
    // The notes added by the user to the daily mood
    // Might be null or empty.
    private String notes;

    // Use the Ignore annotation so Room knows that it has to use the other constructor instead - autogenerating the ID
    // todo: utánanézni az @ignore annotation-nek:
    // how does it insert the entry using only a MoodEntry without id??
    // (Does it extract the fields from the MoodEntry object and inserts to the other constructor or what??)
    @Ignore
    public MoodEntry(int moodId, Long timeOfMood, String notes) {
        this.moodId = moodId;
        this.timeOfMood = timeOfMood;
        this.notes = notes;
    }

    public MoodEntry(int entryId, int moodId, Long timeOfMood, String notes) {
        // Room autogenerates the entryId which is the primary key of the data entry

        this.entryId = entryId;
        this.moodId = moodId;
        this.timeOfMood = timeOfMood;
        this.notes = notes;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    // Returns the exact time when the mood was saved in a Timestamp format
    public Long getTimeOfMood() {
        return timeOfMood;
    }

    public void setTimeOfMood(Long timeOfMood) {
        this.timeOfMood = timeOfMood;
    }

    // If there was no notes added (ex. in database version 1, or if the user doesn't save any notes),
    // this will return null.
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    // Returns a string containing all the data of the MoodEntry in String format.
    public String toString(Context context) {
        String moodEntryString =
                "ID: " + this.entryId +
                ". Date: " + DateConverter.timeStampToDateString(this.timeOfMood) +
                ". Mood: " + MoodUtilities.getMoodString(this.moodId, context) +
                ". Notes: " + this.notes;
        return moodEntryString;
    }
}
