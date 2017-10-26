package edu.mhu.cs.brandonwatkins.deardiary;

import android.app.Activity;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.Date;

/**
 * Created by brandonwatkins on 26/09/17.
 */

public class IOManager {

    public final static String DATE_FORMAT  = "dd/MM/yy";
    public final static String YEAR_FORMAT  = "yyyy";
    public final static String MONTH_FORMAT  = "MM";
    public final static String DIARY_PREFS  = "edu.mhu.cs.dairy.prefs";
    public final static String JOURNAL      = "edu.mhu.cs.dairy.jounral";

    /**
     * Converts the epoch time into a full date string
     * @param epoch time when the Journal Entry was created
     * @return the date string "dd/MM/yy"
     */
    static public String getDateString(long epoch) {
        Log.d("Epoch Time", ":" + epoch);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(epoch));
    }

    /**
     * Converts the epoch time into a year string
     * @param epoch time when the Journal Entry was created
     * @return the date string "yyyy"
     */
    static public String getYearString(long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_FORMAT);
        return sdf.format(new Date(epoch));
    }

    /**
     * Converts the epoch time into a month string
     * @param epoch time when the Journal Entry was created
     * @return the date string "MM"
     */
    static public String getMonthString(long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat(MONTH_FORMAT);
        return sdf.format(new Date(epoch));
    }

    /**
     * Loads the Journal from shared preferences
     * @param a the Activity
     * @return the Journal
     */
    static public Journal loadJournal(Activity a) {
        SharedPreferences prefs = a.getSharedPreferences(DIARY_PREFS, 0);
        String journalString = prefs.getString(JOURNAL, null);
        if (journalString == null) return new Journal();

        Journal j = Journal.create(journalString);
        return j;
    }

    /**
     * Saves the Journal to shared preferences
     * @param a the Activity
     * @param j the Journal
     */
    static public void saveJournal(Activity a, Journal j) {
        SharedPreferences prefs = a.getSharedPreferences(DIARY_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(JOURNAL, j.serialize());

        editor.commit();
    }

}

