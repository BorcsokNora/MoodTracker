package com.example.moodtracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.moodtracker.MoodDatabase.MoodDatabase;
import com.example.moodtracker.MoodDatabase.MoodEntry;

import java.util.List;

public class MoodHistoryViewModel extends AndroidViewModel {

    // Constant for logging
        private static final String TAG = MoodHistoryViewModel.class.getSimpleName();

        private LiveData<List<MoodEntry>> moodEntries;

        public MoodHistoryViewModel(Application application) {
            super(application);
            MoodDatabase database = MoodDatabase.getDatabase(this.getApplication());
            Log.d(TAG, "Actively retrieving the mood entries from the DataBase");
            moodEntries = database.moodDao().getMoodEntries();
        }

        public LiveData<List<MoodEntry>> getTasks() {
            return moodEntries;
        }
    }


