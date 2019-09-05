package com.example.moodtracker.Utilities;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.moodtracker.MoodRegisterActivity;

// This listener is notified when the user presses the Done button on the keyboard after entering a note.
// Note: to use this feature we need to specify two attributes of the EditText view:
// android:inputType="text" or similar (as it doesn't work with the default input type - even if it's text)
// android:imeOptions to specify the action ("actionDone")

public class EditTextSoftKeyListener implements TextView.OnEditorActionListener {

    Activity mActivity;
    public EditTextSoftKeyListener(Activity activity) {
    mActivity = activity;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            UiUtilities.hideKeyboard(mActivity);
            handled = true;
        }
        return handled;
    }
}
