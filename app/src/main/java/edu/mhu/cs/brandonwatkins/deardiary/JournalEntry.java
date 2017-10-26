package edu.mhu.cs.brandonwatkins.deardiary;

/**
 * Created by brandonwatkins on 26/09/17.
 */

public class JournalEntry {
    private long id; //epoch time when it was created
    private long entryDate; //date to which entry belongs
    private String entryText; //text of journal entry

    /**
     * Constructor for JournalEntry
     * @param entryText
     * @param entryDate
     */
    public JournalEntry(String entryText, long entryDate){
        id = System.currentTimeMillis();
        this.entryText = entryText;
        this.entryDate = entryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(long entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }


}


