package com.example.moodtracker;

import android.view.View;

import com.example.moodtracker.databinding.ActivityMoodRegisterBinding;

public class MoodUtilities {

    // IDs indicating the moods
    public static final int VERY_BAD_MOOD_ID = 1;
    public static final int BAD_MOOD_ID = 2;
    public static final int NEUTRAL_MOOD_ID = 3;
    public static final int GOOD_MOOD_ID = 4;
    public static final int VERY_GOOD_MOOD_ID = 5;




    /**
     * This method cleans up the previously selected mood state
     * by making the selection-indicator view invisible.
     * @param selectedMoodID: integer, the ID of the selected mood
     * @param binding: the instance of the data binding class.
     *               This method is used in the MoodRegisterActivity where the data binding class is ActivityMoodRegisterBinding,
     *               that's why we are expecting an instance of this custom binding class.
     */
    public static void cleanUpSelectedMood(int selectedMoodID, ActivityMoodRegisterBinding binding) {
        switch (selectedMoodID) {
            case MoodUtilities.BAD_MOOD_ID:
                binding.selectorBad.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.GOOD_MOOD_ID:
                binding.selectorGood.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.NEUTRAL_MOOD_ID:
                binding.selectorNeutral.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.VERY_BAD_MOOD_ID:
                binding.selectorVeryBad.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.VERY_GOOD_MOOD_ID:
                binding.selectorVeryGood.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * This method registers the selected mood when a mood icon is clicked
     * and saves the mood ID belonging to that icon
     * Note: This method DOES NOT save the selected mood permanently, to the database!
     *
     * @param moodIcon: The mood icon that is clicked by the user
     */
    public static int registerSelectedMood(View moodIcon) {
        int moodViewId = moodIcon.getId();
        int selectedMoodId = 0;

        switch (moodViewId) {
            case R.id.icon_bad:
                selectedMoodId = MoodUtilities.BAD_MOOD_ID;
                break;
            case R.id.icon_very_bad:
                selectedMoodId = MoodUtilities.VERY_BAD_MOOD_ID;
                break;
            case R.id.icon_good:
                selectedMoodId = MoodUtilities.GOOD_MOOD_ID;
                break;
            case R.id.icon_very_good:
                selectedMoodId = MoodUtilities.VERY_GOOD_MOOD_ID;
                break;
            case R.id.icon_neutral:
                selectedMoodId = MoodUtilities.NEUTRAL_MOOD_ID;
                break;
        }
        return selectedMoodId;
    }

    /**
     * This method indicates on the UI the selected Mood
     * by showing a selector shape around the selected mood icon.
     * If no mood was selected, no selector shape will be shown.
     */

    public static void showSelectedMood(int selectedMoodId, ActivityMoodRegisterBinding binding) {
        switch (selectedMoodId) {
            case MoodUtilities.BAD_MOOD_ID:
                binding.selectorBad.setVisibility(View.VISIBLE);
                break;
            case MoodUtilities.VERY_BAD_MOOD_ID:
                binding.selectorVeryBad.setVisibility(View.VISIBLE);
                break;
            case MoodUtilities.GOOD_MOOD_ID:
                binding.selectorGood.setVisibility(View.VISIBLE);
                break;
            case MoodUtilities.VERY_GOOD_MOOD_ID:
                binding.selectorVeryGood.setVisibility(View.VISIBLE);
                break;
            case MoodUtilities.NEUTRAL_MOOD_ID:
                binding.selectorNeutral.setVisibility(View.VISIBLE);
                break;
        }
    }

}
