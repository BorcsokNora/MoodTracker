package com.example.moodtracker;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.moodtracker.MoodDatabase.MoodDatabase;
import com.example.moodtracker.MoodDatabase.MoodEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MoodHistoryActivity extends AppCompatActivity {

    // Constant for logging
    private static final String TAG = MoodHistoryActivity.class.getSimpleName();

    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private MoodHistoryAdapter mAdapter;

    private MoodDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mood_history);

        // Set the RecyclerView to its corresponding view
        mRecyclerView = findViewById(R.id.recyclerViewMoods);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create (or get) the instance of the database
        // We need the application context to see if the db was already created by another activity
        // todo:  Database instance should be provided by ViewModel!!! - lsd: LiveData & ViewModel in FÜZETBE
        mDb = MoodDatabase.getDatabase(getApplicationContext());

        // Get a list of all the entries of the database
        List<MoodEntry> moodEntryList = mDb.moodDao().getMoodEntries();


        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new MoodHistoryAdapter(this, moodEntryList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mDb = MoodDatabase.getDatabase(getApplicationContext());

        //todo: setup ViewModel here to observe database updates through the ViewModel instead of the activity
        // for this we have to create a custom viewmodel class
        // look taskApp for further details
    }


}
