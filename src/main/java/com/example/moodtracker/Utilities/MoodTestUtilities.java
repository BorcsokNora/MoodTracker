package com.example.moodtracker.Utilities;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.moodtracker.MoodDatabase.MoodDatabase;
import com.example.moodtracker.MoodDatabase.MoodEntry;

import java.util.List;

/**
 * This class contains methods for testing and debugging purposes
 * This class will be deleted from the final version of the app.
 */

//todo: delete class from final version after testing

public class MoodTestUtilities {



    /* This method is used for debugging purposes!
     *  This method returns a String containing all the entries in the mood database in readable format.
     *
     *
    public static  String getFullMoodEntryList(MoodDatabase mdb, Context context) {
        String fullMoodEntryList = "";

        StringBuilder sb = new StringBuilder();
        LiveData<List<MoodEntry>> moodEntryList = mdb.moodDao().getMoodEntries();
        for (int i = 0; i < moodEntryList.getValue().size(); i++) {
            MoodEntry moodEntry = moodEntryList.getValue().get(i);
            String moodEntryString = moodEntry.toString(context);
            sb.append(moodEntryString);
            sb.append("\n");
        }
        fullMoodEntryList = sb.toString();
        return fullMoodEntryList;
    }
*/

}
