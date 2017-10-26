package edu.mhu.cs.brandonwatkins.deardiary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddNewEntryActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    int mDay;
    int mMonth;
    int mYear;
    String entryText;
    private long entryDate;
    private TextView lblDateSelected;
    private EditText txtJournalEntry;
    Journal newJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        newJournal = IOManager.loadJournal(this);

        final Calendar c = Calendar.getInstance();

        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        lblDateSelected = (TextView) findViewById(R.id.dateSelected);
        lblDateSelected.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

    }

    /**
     * Date picker that gets the date selected by the user and stores the data
     * @param v
     */
    public void selectDate(View v) {

        datePickerDialog = new DatePickerDialog(AddNewEntryActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        lblDateSelected.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        mDay    = dayOfMonth;
                        mMonth  = monthOfYear;
                        mYear   = year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /**
     * Back button used to go back to the previous screen. Has an AlertDialog
     * to confirm users choice
     * @param v
     */
    public void backBtn(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.txtBackDialog));
        builder.setCancelable(true);


        //Add Buttons
        builder.setPositiveButton(
                R.string.txtYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }
        );

        builder.setNegativeButton(
                R.string.txtCancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }
        );

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    /**
     * Save button that is used to save the Journal Entry and also save
     * it to the shared preferences
     * @param v
     */
    public void saveBtn(View v) {
        txtJournalEntry = (EditText) findViewById(R.id.journalEntry);
        entryText       = txtJournalEntry.getText().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);

        entryDate = calendar.getTimeInMillis();

        JournalEntry je = new JournalEntry(entryText, entryDate);
        newJournal.addEntry(je);

        IOManager.saveJournal(this, newJournal);

        Toast.makeText(AddNewEntryActivity.this, "Your Diary entry was saved",
                Toast.LENGTH_LONG).show();
        finish();


    }
}

