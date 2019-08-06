package com.example.moodtracker.MoodDatabase;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        //todo: delete log - only for testing
        Log.d("DateConverter", "toDate method called");
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toLong(Date date) {
        //todo: delete log - only for testing
        Log.d("DateConverter", "toLong method called");
        return date == null ? null : date.getTime();
    }

    public static String timeStampToDateString(Long timeInMilliseconds) {
        Date dateObject = new Date(timeInMilliseconds);

        // ** The date/time format can be changed here **
        // This format shows the date and time in the following format:
        // 2001.07.04 at 12:08:56
        // Further examples and date format rules:
        // https://developer.android.com/reference/java/text/SimpleDateFormat.html?utm_source=udacity&utm_medium=course&utm_campaign=android_basics
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");


        String dateToDisplay = dateFormatter.format(dateObject);

        return dateToDisplay;
    }
}
