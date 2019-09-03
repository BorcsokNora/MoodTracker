package com.example.moodtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moodtracker.databinding.ActivityMoodRegisterBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MoodUtilities {

    // Name tag for log entries
    public static final String LOG_TAG = MoodUtilities.class.getSimpleName();

    // IDs indicating the moods
    public static final int VERY_BAD_MOOD_ID = 1;
    public static final int BAD_MOOD_ID = 2;
    public static final int NEUTRAL_MOOD_ID = 3;
    public static final int GOOD_MOOD_ID = 4;
    public static final int VERY_GOOD_MOOD_ID = 5;

    // Create a definition for these mood constants so that the type safety is guaranteed in compile time already.
    @IntDef({VERY_BAD_MOOD_ID, BAD_MOOD_ID, NEUTRAL_MOOD_ID, GOOD_MOOD_ID, VERY_GOOD_MOOD_ID})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mood { }


    public static String getMoodString(@Mood int moodId, Context context) {
        Resources res = context.getResources();

        switch (moodId) {
            case VERY_BAD_MOOD_ID:
                return res.getString(R.string.veryBadMood);
            case BAD_MOOD_ID:
                return res.getString(R.string.badMood);
            case NEUTRAL_MOOD_ID:
                return res.getString(R.string.neutralMood);
            case GOOD_MOOD_ID:
                return res.getString(R.string.goodMood);
            case VERY_GOOD_MOOD_ID:
                return res.getString(R.string.veryGoodMood);
        }
        return null;
    }

    /**
     * This method cleans up the previously selected mood state on the UI
     * by making the selection-indicator view invisible.
     *
     * @param selectedMoodID: integer, the ID of the selected mood
     * @param binding:        the instance of the data binding class.
     *                        This method is used in the MoodRegisterActivity where the data binding class is ActivityMoodRegisterBinding,
     *                        that's why we are expecting an instance of this custom binding class.
     */
    public static void cleanUpSelectedMood(@Mood int selectedMoodID, ActivityMoodRegisterBinding binding) {
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
                selectedMoodId = BAD_MOOD_ID;
                break;
            case R.id.icon_very_bad:
                selectedMoodId = VERY_BAD_MOOD_ID;
                break;
            case R.id.icon_good:
                selectedMoodId = GOOD_MOOD_ID;
                break;
            case R.id.icon_very_good:
                selectedMoodId = VERY_GOOD_MOOD_ID;
                break;
            case R.id.icon_neutral:
                selectedMoodId = NEUTRAL_MOOD_ID;
                break;
        }
        return selectedMoodId;
    }

    /**
     * This method indicates on the UI the selected Mood
     * by showing a selector shape around the selected mood icon.
     * If no mood was selected, no selector shape will be shown.
     *
     * @param selectedMoodId: integer, one of the predefined ID of the selected mood
     * @param binding:        the instance of the data binding class.
     *                        This method is used in the MoodRegisterActivity where the data binding class is ActivityMoodRegisterBinding,
     *                        that's why we are expecting an instance of this custom binding class.
     */
    public static void showSelectedMood(@Mood int selectedMoodId, ActivityMoodRegisterBinding binding) {
        switch (selectedMoodId) {
            case BAD_MOOD_ID:
                binding.selectorBad.setVisibility(View.VISIBLE);
                break;
            case VERY_BAD_MOOD_ID:
                binding.selectorVeryBad.setVisibility(View.VISIBLE);
                break;
            case GOOD_MOOD_ID:
                binding.selectorGood.setVisibility(View.VISIBLE);
                break;
            case VERY_GOOD_MOOD_ID:
                binding.selectorVeryGood.setVisibility(View.VISIBLE);
                break;
            case NEUTRAL_MOOD_ID:
                binding.selectorNeutral.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * This method saves the selected mood in SharedPreferences,
     * using the default SharedPreferences.
     * And informs the user that the mood is saved or that a mood has to be selected.
     *
     * @param context:        is the context of the invoking activity (which is MoodRegisterActivity)
     * @param selectedMoodId: integer, one of the predefined ID of the selected mood
     * @param sp:             instance of SharedPreferences
     */
    public static void saveMoodToSharedPreferences(Context context, @Mood int selectedMoodId, String moodNotes, SharedPreferences sp) {

        // If no icon was selected, prompt the user in a toast message to select a mood first.
        if (selectedMoodId == 0) {
            Toast.makeText(context, context.getString(R.string.noMoodSelectedMessage), Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the selected mood and notes in SharedPreferences;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(Constants.SELECTED_MOOD_KEY, selectedMoodId).
               putString(Constants.MOOD_NOTES_KEY, moodNotes)
                //save the changes in the preferences
                // .apply() performs the update off the main thread
                .apply();

        // Show a toast message informing the user that the selected mood is saved
        Toast.makeText(context, context.getString(R.string.moodSavedMessage), Toast.LENGTH_SHORT).show();

    }

    /**
     * This method extracts the int value of the saved mood from the shared preferences
     *
     * @param context: activity context
     * @param tag:     tag String for log entries
     * @param sp:      instance of SharedPreferences
     * @return savedMoodPref: the int value of the saved mood
     * If there is no value saved in the shared preferences it returns 0.
     */
    public static int getSavedMoodFromSharedPreferences(Context context, String tag, SharedPreferences sp) {
        int savedMoodPref = 0;
        try {
            savedMoodPref = sp.getInt(Constants.SELECTED_MOOD_KEY, 0);
        } catch (Exception e) {
            Log.d(tag, "getSavedMoodFromSharedPreferences: extracting savedMood from preference failed. " + e);
        }
        return savedMoodPref;
    }


    public static String getSavedNotes(Context context, String tag, SharedPreferences sp){
        String savedNotes = "";
        try{
            savedNotes = sp.getString(Constants.MOOD_NOTES_KEY, "");
        } catch (Exception e){
            Log.d(tag, "getSavedNotes: extracting savedNotes from preference failed. " + e);
        }
        return savedNotes;
    }

    /**
     * Helper method to provide the icon resource id according to the mood id.
     *
     * @param moodId is a predefined constant int defining each mood. They are defined in MoodUtilities.
     * @return the resource id of the corresponding mood icon
     */
    public static int getMoodIconResourceId(int moodId) {

        switch (moodId) {
            case VERY_BAD_MOOD_ID:
                return R.drawable.ic_very_bad_mood;
            case BAD_MOOD_ID:
                return R.drawable.ic_bad_mood;
            case NEUTRAL_MOOD_ID:
                return R.drawable.ic_neutral_mood;
            case GOOD_MOOD_ID:
                return R.drawable.ic_good_mood;
            case VERY_GOOD_MOOD_ID:
                return R.drawable.ic_very_good_mood;
            default:
                Log.e(LOG_TAG, "Unknown Mood: " + moodId);
                return R.drawable.ic_not_found_48dp;
        }
    }



}
