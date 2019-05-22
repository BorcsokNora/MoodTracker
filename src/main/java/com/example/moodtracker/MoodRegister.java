package com.example.moodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MoodRegister extends AppCompatActivity {

    // The mood icons to be selected by the user
    ImageView ivVeryBadMood;
    ImageView ivBadMood;
    ImageView ivVeryGoodMood;
    ImageView ivGoodMood;
    ImageView ivNeutralMood;

    ImageView selectorVeryBad;
    ImageView selectorBad;
    ImageView selectorNeutral;
    ImageView selectorGood;
    ImageView selectorVeryGood;

    // This variable stores the ID of the selected mood
    int mSelectedMood = 0;

    // This listener is notified when a mood icon is clicked
    View.OnClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_register);

        // The mood icons are identified here
        ivBadMood = findViewById(R.id.icon_bad);
        ivVeryBadMood = findViewById(R.id.icon_very_bad);
        ivGoodMood = findViewById(R.id.icon_good);
        ivVeryGoodMood = findViewById(R.id.icon_very_good);
        ivNeutralMood = findViewById(R.id.icon_neutral);

        // The mood selector views are identified here
        selectorBad = findViewById(R.id.selector_bad);
        selectorGood = findViewById(R.id.selector_good);
        selectorNeutral = findViewById(R.id.selector_neutral);
        selectorVeryBad = findViewById(R.id.selector_very_bad);
        selectorVeryGood = findViewById(R.id.selector_very_good);


        // This onClickListener is notified when a mood is clicked
        // Then it saves the selected mood's ID.
        // And modifies the layout to indicate to the user which mood is selected.
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View moodIcon) {
                // Check if any mood was selected previously and clean up before saving the new mood state
                if (mSelectedMood != 0) {
                    cleanUpSelectedMood();
                    mSelectedMood = 0;
                }
                int moodViewId = moodIcon.getId();
                switch (moodViewId) {
                    case R.id.icon_bad:
                        mSelectedMood = MoodUtilities.BAD_MOOD_ID;
                        selectorBad.setVisibility(View.VISIBLE);
                        break;
                    case R.id.icon_very_bad:
                        mSelectedMood = MoodUtilities.VERY_BAD_MOOD_ID;
                        selectorVeryBad.setVisibility(View.VISIBLE);
                        break;
                    case R.id.icon_good:
                        mSelectedMood = MoodUtilities.GOOD_MOOD_ID;
                        selectorGood.setVisibility(View.VISIBLE);
                        break;
                    case R.id.icon_very_good:
                        mSelectedMood = MoodUtilities.VERY_GOOD_MOOD_ID;
                        selectorVeryGood.setVisibility(View.VISIBLE);
                        break;
                    case R.id.icon_neutral:
                        mSelectedMood = MoodUtilities.NEUTRAL_MOOD_ID;
                        selectorNeutral.setVisibility(View.VISIBLE);
                        break;
                }

                //Todo: put mSelectedMood to savedInstanceState OR save in shared preferences here? (or both?)

                Toast.makeText(MoodRegister.this, "Selected mood ID: " + mSelectedMood, Toast.LENGTH_SHORT).show();

                return;
            }
        };

        ivVeryBadMood.setOnClickListener(mListener);
        ivBadMood.setOnClickListener(mListener);
        ivNeutralMood.setOnClickListener(mListener);
        ivGoodMood.setOnClickListener(mListener);
        ivVeryGoodMood.setOnClickListener(mListener);
    }

    // Clean up the previously selected mood state
    // by making the selection-indicator view invisible
    private void cleanUpSelectedMood() {
        switch (mSelectedMood) {
            case MoodUtilities.BAD_MOOD_ID:
                selectorBad.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.GOOD_MOOD_ID:
                selectorGood.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.NEUTRAL_MOOD_ID:
                selectorNeutral.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.VERY_BAD_MOOD_ID:
                selectorVeryBad.setVisibility(View.INVISIBLE);
                break;
            case MoodUtilities.VERY_GOOD_MOOD_ID:
                selectorVeryGood.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
