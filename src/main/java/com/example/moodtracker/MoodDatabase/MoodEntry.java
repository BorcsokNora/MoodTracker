package com.example.moodtracker.MoodDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


/*
 * The mood database has only one table.
 * This class serves as the schema of the database
 *
 * MoodEntry: one entry contains all the data of one day's mood
 * The annotations (@...) are creating the database table structure
 * @Entity: defines the table name and the database entity - which is a MoodEntry object in this case.
 * @ColumnInfo: it sets the name of the column.
 * (If the field has the correct name for the column, this annotation is unnecessary).
 * @Ignore: Room will skip to add these items to the database
 */
@Entity(tableName = "mood")
public class MoodEntry {

    //todo: refactor code to use date as a primary key!!

    // The ID of the database entry
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int entryId;

    @ColumnInfo(name = "mood_id")
    // The ID of the mood that defines which mood was saved for that day
    private int moodId;

    @ColumnInfo(name = "date")
    // The date of the saved mood
    private Date dateOfMood;


    // Use the Ignore annotation so Room knows that it has to use the other constructor instead - autogenerating the ID
    // todo: utánanézni az @ignore annotation-nek:
    // how does it insert the entry using only a MoodEntry without id??
    // (Does it extract the fields from the MoodEntry object and inserts to the other constructor or what??)
    @Ignore
    public MoodEntry(int moodId, Date dateOfMood) {
        this.moodId = moodId;
        this.dateOfMood = dateOfMood;
    }

    public MoodEntry(int entryId, int moodId, Date dateOfMood) {
        this.entryId = entryId;
        this.moodId = moodId;
        this.dateOfMood = dateOfMood;
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

    public Date getDateOfMood() {
        return dateOfMood;
    }

    public void setDateOfMood(Date dateOfMood) {
        this.dateOfMood = dateOfMood;
    }
}
