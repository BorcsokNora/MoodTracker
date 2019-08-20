package com.example.moodtracker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moodtracker.MoodDatabase.DateConverter;
import com.example.moodtracker.MoodDatabase.MoodDatabase;
import com.example.moodtracker.MoodDatabase.MoodEntry;
import com.example.moodtracker.databinding.ActivityMoodRegisterBinding;


public class EditMoodActivity extends AppCompatActivity {

    public static final String TAG = EditMoodActivity.class.getName();

    public boolean inUpdateMode = false;
    private final String SAVED_INSTANCE_STATE_KEY_UPDATE_MODE = "inUpdateMode";

    // The ID of the mood entry.
    // Should be assigned a valid value on the opening this activity.
    // If it remains -1 that means there is no valid MoodEntry to be loaded
    int mMoodEntryId = -1;

    // This variable stores the ID of the selected mood
    int mSelectedMood = 0;

    // This variable holds the notes added by the user to the actual day's mood
    String mMoodNotes = "";

    Long mMoodDateTime;

    // This boolean shows if there are any notes added by the user that needs to be saved
    boolean mNotesAdded = false;

    // This is the MoodEntry to be showed/modified
    MoodEntry mMoodEntry;

    // The instance of the data binding class
    // A binding class is autogenerated for each layout file.
    ActivityMoodRegisterBinding mBinding;

    // This listener is notified when a mood icon is clicked
    View.OnClickListener mMoodIconListener;

    // This listener is notified when the Modify/Update button is clicked
    View.OnClickListener mModifyUpdateButtonListener;

    // This listener is notified when the Cancel/Back to Mood List button is clicked
    View.OnClickListener mCancelBackToListListener;

    // This variable holds the single instance of our database
    private MoodDatabase mMoodDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoodEntryId = getIntent().getExtras().getInt(getResources().getString(R.string.editMoodActivityIntentExtraMoodIdKey), -1);

        // check if the mood entry ID is valid
        if (mMoodEntryId == -1) {
            Log.w(TAG, "onCreate: MoodEntry ID is not valid! ");
        }

        Log.d(TAG, "onCreate: mMoodEntryId = " + mMoodEntryId);

        // Initialize/get access to the data binding class
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_mood_register);

        // adapt texts and views of the layout to the edit mood activity
        mBinding.saveButton.setText(R.string.buttonModifyMood);
        mBinding.dateOfEntry.setVisibility(View.VISIBLE);
        mBinding.editTextNotes.setVisibility(View.GONE);
        mBinding.editModeNotesTextView.setVisibility(View.VISIBLE);
        mBinding.moodHistoryButton.setText(R.string.buttonBackToMoodHistory);

        // Create (or get) the instance of the database
        // We need the application context to see if the db was already created by another activity
        // todo:  Database instance should be provided by ViewModel!!! - lsd: LiveData & ViewModel in FÜZETBE
        mMoodDatabase = MoodDatabase.getDatabase(getApplicationContext());


        // todo: refactor code to prevent crash when mood ID is invalid

        Log.d(TAG, "onCreate: get mood from DAO with id " + mMoodEntryId);

        // get the mood from the database
        mMoodEntry = mMoodDatabase.moodDao().getMoodWithId(mMoodEntryId);
        Log.d(TAG, "onCreate: mMoodEntry = " + mMoodEntry.toString(this));

        // Retrieve the data only if the MoodEntry is not null
        if (mMoodEntry != null) {
            // assign the correct value to mSelectedMood
            mSelectedMood = mMoodEntry.getMoodId();
            Log.d(TAG, "onCreate: retrieved mood from MoodEntry. mSelectedMood = " + mSelectedMood);

            // Display on the UI the mood belonging to the MoodEntry
            MoodUtilities.showSelectedMood(mSelectedMood, mBinding);

            // Extract the saved text from the database
            //  assign the correct text to mMoodNotes and show it on the UI
            mMoodNotes = mMoodEntry.getNotes();
            mBinding.editModeNotesTextView.setText(mMoodNotes);

            mMoodDateTime = mMoodEntry.getTimeOfMood();
            mBinding.dateOfEntry.setText(DateConverter.timeStampToDateString(mMoodDateTime));

            Log.d(TAG, "onCreate: mMoodNotes = " + mMoodNotes);
        } else {
            Log.d(TAG, "onCreate: MoodEntry is null! ");
        }


        // This listener is responsible to invoke the modify/update function when the user clicks on the show mood history button
        // The function of the button changes based on the mode (read only or update) we are in.
        mModifyUpdateButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: mModifyUpdateButtonListener. inUpdateMode = " + inUpdateMode);
                if (inUpdateMode) {
                    // This means we are in Update mode, so when the user clicks on the button we invoke the update mood entry function
                    updateMood();
                } else {

                    switchToUpdateMode();
                }
            }
        }

        ;

        // This listener is responsible to invoke the calcel/back to list function when the user clicks on the button.
        // The function of this button is always the same, but the label of the button changes based on the input mode we are in.
        mCancelBackToListListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoodHistory();
            }
        };

        // set the listeners on the buttons to invoke the functions when the user clicks on them.
        mBinding.saveButton.setOnClickListener(mModifyUpdateButtonListener);

        mBinding.moodHistoryButton.setOnClickListener(mCancelBackToListListener);

    }

    // Save the update/modify mode state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_INSTANCE_STATE_KEY_UPDATE_MODE, inUpdateMode);
    }

    // Restore the modify/update mode state
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inUpdateMode = savedInstanceState.getBoolean(SAVED_INSTANCE_STATE_KEY_UPDATE_MODE);
    }

    /**
     * When the user presses the update button, this method extracts the data from the UI and updates the database.
     */
    public void updateMood() {
        mMoodNotes = mBinding.editTextNotes.getText().toString();

        //Todo: refactor code to save the update time separated
        Long timeStampLong = System.currentTimeMillis();

        MoodEntry updatedEntry = new MoodEntry(mSelectedMood, timeStampLong, mMoodNotes);
        updatedEntry.setEntryId(mMoodEntryId);
        int updatedRows = mMoodDatabase.moodDao().updateMoodEntry(updatedEntry);
        if (updatedRows > 0) {
            Log.d(TAG, "updateMood: database is updated successfully");
            Toast.makeText(getApplicationContext(), "Updated.", Toast.LENGTH_SHORT).show();
            showMoodHistory();
        } else {
            Log.d(TAG, "updateMood: failed to update the data");
        }
    }

    public void deleteMood(MoodEntry entry) {
        int deletedRows = mMoodDatabase.moodDao().delete(entry);
        if (deletedRows > 0) {
            Log.d(TAG, "deleteMood: entry is deleted successfully");
        } else {
            Log.d(TAG, "deleteMood: failed to delete the entry");
        }
    }

    /**
     * This method opens up a new activity to show the list of saved moods
     **/
    public void showMoodHistory() {
        Intent i = new Intent(getApplicationContext(), MoodHistoryActivity.class);
        startActivity(i);
    }


    // This method adjusts the layout so that the user can modify the MoodEntry
    public void switchToUpdateMode() {

        // Modify the layout to switch from showing the specific mood entry to Modify entry mode
        mBinding.dateOfEntry.setVisibility(View.INVISIBLE);
        mBinding.editModeNotesTextView.setVisibility(View.GONE);
        mBinding.editTextNotes.setVisibility(View.VISIBLE);
        mBinding.saveButton.setText(getResources().getString(R.string.buttonUpdateMood));
        mBinding.moodHistoryButton.setText(R.string.buttonCancelModification);
        mBinding.deleteButton.setVisibility(View.VISIBLE);

        // DISPLAY THE MOOD ENTRY ON THE UI
        mBinding.editTextNotes.setText(mMoodNotes);

        // SET LISTENERS ON VIEWS

        // Set a new listener on the Delete button to delete the current entry from the database when the button is clicked.
        mBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMood(mMoodEntry);
                showMoodHistory();
                Toast.makeText(getApplicationContext(), "Deleted.", Toast.LENGTH_SHORT).show();
            }
        });

        // todo: add this listener (and the same one in MoodRegisterActivity) to utilities!

        // When a mood is clicked the UI shows an indicator around the selected mood icon
        mMoodIconListener = new View.OnClickListener() {
            @Override
            public void onClick(View moodIcon) {

                // Check if any mood was selected previously and clean up before saving the new mood state
                if (mSelectedMood != 0) {
                    MoodUtilities.cleanUpSelectedMood(mSelectedMood, mBinding);
                    mSelectedMood = 0;
                }

                // Register which mood was selected by the user
                mSelectedMood = MoodUtilities.registerSelectedMood(moodIcon);

                // and put a sign on the selected mood in the UI
                MoodUtilities.showSelectedMood(mSelectedMood, mBinding);
            }
        };

        // set the listener on the mood icons so that it registers if an icon is clicked
        mBinding.iconBad.setOnClickListener(mMoodIconListener);
        mBinding.iconGood.setOnClickListener(mMoodIconListener);
        mBinding.iconNeutral.setOnClickListener(mMoodIconListener);
        mBinding.iconVeryBad.setOnClickListener(mMoodIconListener);
        mBinding.iconVeryGood.setOnClickListener(mMoodIconListener);

        // set a listener on the EditText field to know if there are any notes to be saved
        mBinding.editTextNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotesAdded = true; // this boolean indicates that there might be some notes that we have to save.
            }
        });

        // indicate that we switched from "read only" mode to update mode.
        inUpdateMode = true;
    }
}
