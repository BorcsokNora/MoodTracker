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

    // This variable stores the ID of the selected mood
    int mSelectedMood;

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

        // This onClickListener is notified when a mood is clicked
        // Then it saves the selected mood's ID.
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View moodIcon) {
                int moodViewId = moodIcon.getId();
                switch (moodViewId) {
                    case R.id.icon_bad:
                        mSelectedMood = MoodUtilities.BAD_MOOD_ID;
                        break;
                    case R.id.icon_very_bad:
                        mSelectedMood = MoodUtilities.VERY_BAD_MOOD_ID;
                        break;
                    case R.id.icon_good:
                        mSelectedMood = MoodUtilities.GOOD_MOOD_ID;
                        break;
                    case R.id.icon_very_good:
                        mSelectedMood = MoodUtilities.VERY_GOOD_MOOD_ID;
                        break;
                    case R.id.icon_neutral:
                        mSelectedMood = MoodUtilities.NEUTRAL_MOOD_ID;
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

}
