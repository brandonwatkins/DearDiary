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
    public final static String DIARY_PREFS  = "edu.mhu.cs.dairy.prefs";
    public final static String JOURNAL      = "edu.mhu.cs.dairy.jounral";

    static public String getDateString(long epoch) {
        Log.d("Epoch Time", ":" + epoch);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(epoch));
    }

    static public Journal loadJournal(Activity a) {
        SharedPreferences prefs = a.getSharedPreferences(DIARY_PREFS, 0);
        String journalString = prefs.getString(JOURNAL, null);
        if (journalString == null) return new Journal();

        Journal j = Journal.create(journalString);
        return j;
    }

    static public void saveJournal(Activity a, Journal j) {
        SharedPreferences prefs = a.getSharedPreferences(DIARY_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(JOURNAL, j.serialize());

        editor.commit();
    }

}

