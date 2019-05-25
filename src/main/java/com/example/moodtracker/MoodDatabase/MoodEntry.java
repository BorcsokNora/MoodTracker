package com.example.moodtracker.MoodDatabase;

import java.util.Date;

public class MoodEntry {


    private int entryId;
    private int moodId;
    private Date dateOfMood;


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
