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

    /**
     * Serialize the class into a JSON string using GSON
     * @return a JSON string
     */
    public String serialize() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    /**
     * Use GSON to instantiate this class using the JSON representation of the
     * journal entry
     * @param serializedData
     * @return the Journal from JSON/GSON
     */
    static public Journal create(String serializedData) {
        Gson gson = new Gson();

        return gson.fromJson(serializedData, Journal.class);
    }

    /**
     *
     * @param selectedYear that the user chooses in ViewEditActivity
     * @param selectedMonth that the user chooses in ViewEditActivity
     * @return
     */
    public ArrayList<String> myJournalEntries(int selectedYear, int selectedMonth) {
        ArrayList<String> mje = new ArrayList<>();
        int counter = 0;

        mje.add(0, "Select a date...");
        for (int j = 0; j < entries.size(); j++) {
            JournalEntry currentEntry = entries.get(j);

            int currentEntryYear    = Integer.parseInt(IOManager.getYearString(currentEntry.getEntryDate()));
            int currentEntryMonth   = Integer.parseInt(IOManager.getMonthString(currentEntry.getEntryDate()));

            if (currentEntryYear == selectedYear && currentEntryMonth == selectedMonth) {
                mje.add(counter + 1, IOManager.getDateString(currentEntry.getEntryDate()));
                counter++;
            }

            //if (mje[counter + 1] == null) Log.d("Dear Diary", "THIS IS NULL: " + mje[j]);

            //Log.d("Dear Diary", "Array of Strings: " + mje[j]);
        }

        if (mje.size() == 1) {
            mje.remove(0);
            mje.add(0, "NO ENTRIES FOR THIS MONTH");
        }
        return mje;
    }

    public String[] yearEntries() {

        int firstYear   = Integer.parseInt(IOManager.getYearString(entries.get(0).getEntryDate()));
        int lastYear    = Integer.parseInt(IOManager.getYearString(entries.get(entries.size() - 1).getEntryDate()));

        String[] ye = new String[lastYear - firstYear + 2];

        ye[0] = "Year";
        int k = 1;
        for (int j = firstYear; j <= lastYear; j++) {
            ye[k] = Integer.toString(j);
            k++;
        }

        return ye;

    }

/**
    public String[] getMonthEntries(int year) {
        String[] months = activity.getResources().getStringArray(R.array.monthNames);

        for (int i = 1; i < months.length; i++){

        }
        monthEntries[0] = "Select a month...";

    }
**/
    public JournalEntry getEntryAtIndex(int i){
        if (entries.get(i) != null) {
            JournalEntry e = entries.get(i);
            return e;
        }
        return null;

    }

    public JournalEntry getEntryById(long id) {
        for (int j = 0; j < entries.size(); j++) {
            JournalEntry currentEntry = entries.get(j);
            if (currentEntry.getId() == id) {
                return currentEntry;
            }
        }
        return null;
    }

    public ArrayList<JournalEntry> getEntries() {
        return entries;
    }

    public JournalEntry findJournal(String selectedDate){
        for (int j = 0; j < entries.size(); j++) {
            JournalEntry currentEntry = entries.get(j);
            String currentEntryDate = IOManager.getDateString(currentEntry.getEntryDate());
            if (currentEntryDate.equals(selectedDate)) {
                return currentEntry;
            }
        }
        return null;
    }
}
