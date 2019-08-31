package com.example.moodtracker.MoodDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;


/*
 * This static class provides a single database.
 * In this class we connect all the classes (eg. tables, typeConverters) and interfaces (eg. DAO) that belong together to create the database.
 */

/* Due to this @Database annotation room automatically provides an implementation of this class
 *
 * @param entities: the tables of the database. Here we could specify further tables if necessary.
 * @param version: version number of the database. The first version of the database is named version 1.
 */
@Database(entities = {MoodEntry.class}, version = 2, exportSchema = false)

/*
 * Via the TypeConverter we can provide methods to convert complex data types to ones that are acceptable by SQLite.
 * The simple data types that SQLite can accept: null, integer, real, text, blob
 * If we build the project without converting complex data types, it won't compile.
 * Multiple TypeConverter classes can be added here if necessary.
 */
// This DateConverter is responsible for the conversion between Date and Long (Timestamp) data types.
@TypeConverters(DateConverter.class) //here we can add all the TypeConverter classes we need

public abstract class MoodDatabase extends RoomDatabase {

    // Tag with the class name used for log entries
    private static final String LOG_TAG = MoodDatabase.class.getSimpleName();

    // The name of the database - this will identify the Mood database
    private static final String DATABASE_NAME = "MoodHistory";

    // The database is a singleton: this static database instance will be the only one database object
    private static MoodDatabase INSTANCE;


    // This migration defines the method to upgrade the database from version 1 to version 2.
    // In this upgrade a new column (Notes) was added to the database.
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // This will tell the underlying SQLite database to change the table
            // by adding a new "notes" column containing TEXT (corresponding to String) data type
            database.execSQL("ALTER TABLE mood "
                    + " ADD COLUMN notes TEXT");
        }
    };



    /**
     * This method returns the single instance of the Mood database
     */
    public static MoodDatabase getDatabase(Context context) {

        // Create a database instant if it doesn't exist yet
        if (INSTANCE == null) {

            // Java blocks the MoodDatabase class for the time while it executes the code below
            // this ensures that there will be only one class instance created.
            synchronized (MoodDatabase.class) {
                Log.d(LOG_TAG, "Creating the database instance");

                // Create the database object and build the database
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),    // Context: The context for the database. This is usually the Application context.
                        MoodDatabase.class,                 // Class: The abstract class which is annotated with Database and extends RoomDatabase.
                        MoodDatabase.DATABASE_NAME)         // String: The name of the database file.
                        .addMigrations(MIGRATION_1_2)     // The migration scheme in case of version upgrade. Further schemes can be added here.
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");

        // return the newly created or already existing database instance
        return INSTANCE;
    }

    // Connect the MoodDao to the Mood database by creating an abstract method.
    // This method will allow to access the DAO interface through the database instance.
    public abstract MoodDao moodDao();

}

