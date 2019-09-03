package com.example.moodtracker;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

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

    private MoodHistoryAdapter.ItemClickListener itemClickListener;


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
        final LiveData<List<MoodEntry>> moodEntryList = mDb.moodDao().getMoodEntries();

        // connect an observer to the LiveData object
        // onChanged is executed on a background thread by default.
        moodEntryList.observe(this, new Observer<List<MoodEntry>>() {
            @Override
            public void onChanged(@Nullable List<MoodEntry> entries) {
                // here comes the code what should be done with the new data
                // this is done on the main thread!
                mAdapter.setMoodEntries(entries);
            }
        });


        // arguments of observer method: owner (the object that has a lifecycle - here this activity) and the observer


        // This listener is responsible to handle the item clicks by opening the clicked item in a separate activity.
        itemClickListener = new MoodHistoryAdapter.ItemClickListener() {
            @Override
            public void onItemClickListener(int itemId) {
                Log.d(TAG, "onItemClickListener: item with itemId " + itemId + " is opened.");
                openMood(itemId);
            }
        };

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new MoodHistoryAdapter(this, itemClickListener);

        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        // Log the whole database for debugging purposes
        // todo: delete this code after testing
        // String fullMoodEntryList = MoodTestUtilities.getFullMoodEntryList(mDb, this);
        // Log.d(TAG, "onCreate: fullMoodEntryList = " + fullMoodEntryList);

        //todo: setup ViewModel here to observe database updates through the ViewModel instead of the activity
        // for this we have to create a custom viewmodel class
        // look taskApp for further details


        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete or modify an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // We don't use the move function of the ItemTouchHelper
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int adapterPosition = viewHolder.getAdapterPosition();

                switch (swipeDir) {
                    // In case of swiping left, the MoodEntry will be deleted.
                    case ItemTouchHelper.LEFT:
                        // Identify the entry to be deleted
                        MoodEntry entry = moodEntryList.getValue().get(adapterPosition);
                        // Delete the entry of the selected position.
                        deleteMood(entry, mDb, TAG);
                        Toast.makeText(MoodHistoryActivity.this, "ID " + entry.getEntryId() + " entry deleted.", Toast.LENGTH_SHORT).show();
                        break;

                    // In case of swiping right, the MoodEntry will be opened to modify.
                    case ItemTouchHelper.RIGHT:
                        // open modify entry mode, passing in the ID of the selected entry.
                        openMood(moodEntryList.getValue().get(adapterPosition).getEntryId());
                        break;
                }
            }
        }).attachToRecyclerView(mRecyclerView);

        setupViewModel();
    }

    /**
     * This method opens up a new activity to show the specific mood
     **/
    public void openMood(int moodEntryId) {
        Intent openMoodIntent = new Intent(getApplicationContext(), EditMoodActivity.class);
        openMoodIntent.putExtra(Constants.INTENT_EXTRA_MOOD_ID_KEY, moodEntryId);
        startActivity(openMoodIntent);

    }

    /**
     * This method is responsible to update the list when the data changes.
     * It creates a ViewModel that will be responsible to update the list of mood entries from LiveData
     */
    private void setupViewModel() {
        MoodHistoryViewModel viewModel = ViewModelProviders.of(this).get(MoodHistoryViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<MoodEntry>>() {
            @Override
            public void onChanged(@Nullable List<MoodEntry> moodEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setMoodEntries(moodEntries);
            }
        });
    }

    /**
     * Helper method to delete a MoodEntry from the database.
     * As a possibly costly database operation this operation runs off the main thread.
     *
     * @param entry:   the MoodEntry to be deleted from the database
     * @param mDb:     the database instance (this should be a singleton)
     * @param LOG_TAG: a TAG string for log entries.
     */
    public void deleteMood(final MoodEntry entry, final MoodDatabase mDb, final String LOG_TAG) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Run the delete database operation on a background thread
                int deletedRows = mDb.moodDao().delete(entry);

                // Create log messages for the different cases and execute logging on the main thread
                final String logMessage;
                if (deletedRows > 0) {
                    logMessage = "deleteMood: entry is deleted successfully";
                } else {
                    // Create log message running on the main thread
                    logMessage = "deleteMood: failed to delete the entry";
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, logMessage);
                    }
                });
            }

        });
    }

}
