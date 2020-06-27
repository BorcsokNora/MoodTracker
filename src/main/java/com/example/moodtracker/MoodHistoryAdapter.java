package com.example.moodtracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.MoodDatabase.MoodEntry;
import com.example.moodtracker.Utilities.MoodUtilities;

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
    private static final String DATE_FORMAT = "dd. MMM. yyyy.  HH:mm";

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private ItemClickListener mItemClickListener;

    /**
     * Constructor for the MoodHistoryAdapter that initializes the Context.
     *
     * @param context           the current Context
     * @param itemClickListener is the listener applied to each item on the listView.
     *                          This listener must be defined separately and passed in as a parameter when the adapter instance is created.
     */
    public MoodHistoryAdapter(Context context,  ItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
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
        if (notes == null) {
            notes = "";
        }

        // todo: delete mood ID from the final layout
        int moodEntryId = moodEntry.getEntryId();

        //Set values
        holder.moodDateView.setText(dateString);
        holder.moodIconView.setImageResource(MoodUtilities.getMoodIconResourceId(moodEntry.getMoodId()));
        holder.moodNotesView.setText(moodEntry.getNotes());
        holder.moodIdView.setText("ID: " + String.valueOf(moodEntryId));

        //As the entry ID is used only for testing let's hide it for now
        holder.moodIdView.setVisibility(View.INVISIBLE);
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

    /**
     * When a MoodHistoryAdapter is created it will need to implement this interface.
     * This interface makes sure to define what happens when a list item is clicked
     */
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class MoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView moodDateView;
        TextView moodNotesView;
        TextView moodIdView;
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
            moodIdView = itemView.findViewById(R.id.listItemMoodId);

            itemView.setOnClickListener(this);
        }

        // When the user clicks on one item on the list the ID of the mood entry will be passed to the onItemClickListener.
        // This listener is defined separately and passed to the adaptor as a parameter when the adapter instance is created.
        @Override
        public void onClick(View view) {

            int elementId = mMoodEntries.get(getAdapterPosition()).getEntryId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

    /**
     * When the data changes, this method updates the list of moodEntries
     * and notifies the adapter to use the new values on the list.
     */
    public void setMoodEntries(List<MoodEntry> moodEntries) {
        mMoodEntries = moodEntries;
        notifyDataSetChanged();
    }

}
