package com.example.moodtracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.MoodDatabase.DateConverter;
import com.example.moodtracker.MoodDatabase.MoodEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MoodHistoryAdapter extends RecyclerView.Adapter<MoodHistoryAdapter.MoodViewHolder> {


    //todo: override onCreateViewHolder and onBindViewHolder!!
    // use TaskApp as sampler

    Context mContext;

    // Class variables for the List that holds task data and the Context
    private List<MoodEntry> mMoodEntries;

    // Constant for date format
    private static final String DATE_FORMAT = "yyyy.MMM.dd hh:mm:ss";

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    /**
     * Constructor for the MoodHistoryAdapter that initializes the Context.
     *
     * @param context the current Context
     */
    public MoodHistoryAdapter(Context context, List<MoodEntry> moodEntryList) {
        mContext = context;
        mMoodEntries = moodEntryList;
        // todo: by adding a new parameter to the constructor here we can also pass in an ItemClickListener
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public MoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_element_mood, parent, false);

        return new MoodViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(MoodViewHolder holder, int position) {
        // Determine the values of the wanted data
        MoodEntry moodEntry = mMoodEntries.get(position);



        // Get the formatted date of the mood
        // todo: try the difference between the two methods to get the date:
        // String dateString = moodEntry.getTimeOfMood().toString();

        String dateString = dateFormat.format(moodEntry.getTimeOfMood());

        // Check if there is any notes saved to the mood
        String notes = moodEntry.getNotes();
        if(notes == null){
            notes = "";
        }

        //Set values
        holder.moodDateView.setText(dateString);
        holder.moodIconView.setImageResource(MoodUtilities.getMoodIconResourceId(moodEntry.getMoodId()));
        holder.moodNotesView.setText(moodEntry.getNotes());
    }

    /**
     * Returns the number of items to display.
     * mMoodEntries is the list of saved moods that were passed in to the adapter as a parameter
     */
    @Override
    public int getItemCount() {
        if (mMoodEntries == null) {
            return 0;
        }
        return mMoodEntries.size();
    }

/*
CODE SNIPPET FOR PARSING DB DATA

    // This method makes a list of the database entries in string format.
    // For the purpose of testing.
    // todo: check if this method is needed - if not, delete it!
    public String parseMoodList(List<MoodEntry> moodList) {

        int listItems = moodList.size();
        if (listItems > 0) {
            StringBuilder moodDetails = new StringBuilder();

            for (int i = 0; i < listItems; i++) {
                MoodEntry moodEntry = moodList.get(i);
                // get the Id of the database entry
                int moodEntryId = moodEntry.getEntryId();
                // get the mood of the entry in String format
                String moodString = MoodUtilities.getMoodString(moodEntry.getMoodId(), this);

                //get the timestamp of the entry and convert it to readable string format
                String timeOfEntry = DateConverter.timeStampToDateString(moodEntry.getTimeOfMood());

                // create a string of the database entry
                String moodEntryString = "\n" + "ID: " + moodEntryId + ". Saved at this time: " + timeOfEntry + ". " + moodString + " mood.";
                moodDetails.append(moodEntryString);
            }
            // return the string containing the list of mood entry IDs and the corresponding moods.
            return moodDetails.toString();
        }
        // if there is no item it the list return null
        return null;
    }

*/


    // Inner class for creating ViewHolders
    class MoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView moodDateView;
        TextView moodNotesView;
        ImageView moodIconView;


        /**
         * Constructor for the MoodViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public MoodViewHolder(View itemView) {
            super(itemView);

            moodDateView = itemView.findViewById(R.id.listItemMoodDate);
            moodIconView = itemView.findViewById(R.id.listItemMoodIcon);
            moodNotesView = itemView.findViewById(R.id.listItemNotes);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            // todo: add only toast message for now.

        }
    }



}
