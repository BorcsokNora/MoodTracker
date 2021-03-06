package com.example.moodtracker;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moodtracker.MoodDatabase.MoodDatabase;

public class EditMoodViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

// Member variable for the database
    private final MoodDatabase mDb;

    // Member variable for the ID of the MoodEntry
    private final int mEntryId;

    // Initialize the member variables in the constructor with the parameters received
    public EditMoodViewModelFactory(MoodDatabase database, int entryId) {
        mDb = database;
        mEntryId = entryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new EditMoodViewModel(mDb, mEntryId);
    }
}