package com.example.moodtracker.Utilities;

import android.view.View;

import com.example.moodtracker.databinding.ActivityMoodRegisterBinding;

public class MoodIconListener implements View.OnClickListener {

    int mSelectedMood;
    ActivityMoodRegisterBinding mBinding;

    public MoodIconListener(int selectedMood, ActivityMoodRegisterBinding binding) {
        selectedMood = mSelectedMood;
        binding = mBinding;
    }

    // This listener is notified when a mood icon is clicked
    // When a mood is clicked the UI shows an indicator around the selected mood icon
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
    }


