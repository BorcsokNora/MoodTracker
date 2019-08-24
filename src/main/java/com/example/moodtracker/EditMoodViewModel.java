package com.example.moodtracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.moodtracker.MoodDatabase.MoodDatabase;
import com.example.moodtracker.MoodDatabase.MoodEntry;

import java.util.List;

public class EditMoodViewModel extends ViewModel {

    // Constant for logging
    private static final String TAG = com.example.moodtracker.EditMoodViewModel.class.getSimpleName();

    // This variable is the entry to be edited by the user
    // It's wrapped in LiveData so that it can be refreshed automatically if the data changes
    private LiveData<MoodEntry> moodEntry;


    // PUBLIC CONSTRUCTOR
    // We defined this constructor in the EditMoodViewModelFactory in order to take in different parameters than the default ViewModel constructor.
    public EditMoodViewModel(MoodDatabase db, int moodEntryId) {
        moodEntry = db.moodDao().getMoodWithId(moodEntryId);
    }

    // Getter method to return the MoodEntry with the specific ID
    public LiveData<MoodEntry> getMoodEntry() {
        return moodEntry;
    }

}




