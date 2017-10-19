package edu.mhu.cs.brandonwatkins.deardiary;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

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

        //Checks if Journal Entry for that date already exists. If it does, it then appends the text.
        for (int i = 0; i < entries.size(); i++) {
            JournalEntry oldJournalEntry = entries.get(i);

            Log.d("Dear Diary", "New Entry Date: " + IOManager.getDateString(e.getEntryDate()));
            Log.d("Dear Diary", "Old Entry Date: " + IOManager.getDateString(oldJournalEntry.getEntryDate()));

            if (IOManager.getDateString(e.getEntryDate()).equals(IOManager.getDateString(oldJournalEntry.getEntryDate()))){
                String oldText = oldJournalEntry.getEntryText();
                String newText = e.getEntryText();
                String combinedText = oldText + "\n" + newText;

                oldJournalEntry.setEntryText(combinedText);
                return;
            }
        }

        // Do insertion sort and puts entry is the correct position for its date
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
            return;
        }
    }


    public void checkDate(long entryDate1, long entryDate2){
        Calendar c = Calendar.getInstance();
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

    public String[] myJournalEntries() {
        String[] mje = new String[entries.size() + 1];
        /*Test to see if String[] gets loaded in
        String[] mje = new String[6];
        mje[0] = "Select a date";
        mje[1] = "Test1";
        mje[2] = "Test2";
        mje[3] = "Test3";
        mje[4] = "Test4";
        mje[5] = "Test1";
        */
        mje[0] = "Select a date...";
        for (int j = 0; j < entries.size(); j++) {
            JournalEntry currentEntry = entries.get(j);
            mje[j + 1] = IOManager.getDateString(currentEntry.getEntryDate());

            if (mje[j] == null) Log.d("Dear Diary", "THIS IS NULL: " + mje[j]);

            Log.d("Dear Diary", "Array of Strings: " + mje[j]);
        }

        return mje;
    }

    public JournalEntry getEntryAtIndex(int i){
        if (entries.get(i) != null) {
            JournalEntry e = entries.get(i);
            return e;
        }
        return null;

    }
}
