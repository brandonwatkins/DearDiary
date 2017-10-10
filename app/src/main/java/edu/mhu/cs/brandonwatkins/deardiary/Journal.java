package edu.mhu.cs.brandonwatkins.deardiary;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by brandonwatkins on 26/09/17.
 */

public class Journal {

    private ArrayList<JournalEntry> entries;

    public Journal(){
        entries = new ArrayList<JournalEntry>();
    }

    /**
     * Adds a new JournalEntry to the ArrayList
     * @param e the JournalEntry
     */
    public void addEntry(JournalEntry e){
        // Do insertion sort
        if (e != null) {
            for (int i = 0; i < entries.size(); i++) {
                JournalEntry thisEntry = entries.get(i);
                if (e.getEntryDate() < thisEntry.getEntryDate()){
                    entries.add(i, e);
                    /*for (int j = 0; j < entries.size(); j++) {
                        Log.d("Dear Diary", "Entry 1: " + entries.get(j));
                    }*/
                    return;
                }
            }
            entries.add(e);
           /* for (int j = 0; j < entries.size(); j++) {
                Log.d("Dear Diary", "Entry 2: " + entries.get(j));
            }*/
            return;
        }
    }

    /**
     * Updates the JournalEntry that has been edited
     * @param id the id of the JournalEntry
     * @param text the new text that was been entered
     */
    public void updateEntry(long id, String text){
        JournalEntry e = getEntry(id);
        if (e != null) e.setEntryText(text);
    }

    /**
     * Deletes a the desired JournalEntry
     * @param id the id of the JournalEntry
     */
    public void deleteEntry(long id){
        JournalEntry e = getEntry(id);
        if (e != null) entries.remove(e);
    }

    /**
     * Returns the JournalEntry that corresponds to id
     * @param id the id of the JournalEntry
     * @return the JournalEntry in this Journal, or null if not found
     */
    public JournalEntry getEntry (long id){
        for (JournalEntry e : entries){
            if (e.getId() == id){
                return e;
            }
        }
        return null;

    }

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    static public Journal create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the
        // journal entry
        Gson gson = new Gson();

        return gson.fromJson(serializedData, Journal.class);
    }

    /*public String[] myJournalEntries() {
        for (int j = 0; j < entries.size(); j++) {

        }
    }*/

    public ArrayList<JournalEntry> getEntries() {
        return entries;
    }

}
