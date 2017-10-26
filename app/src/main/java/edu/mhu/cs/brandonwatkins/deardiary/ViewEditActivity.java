package edu.mhu.cs.brandonwatkins.deardiary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewEditActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    int mDay;
    int mMonth;
    int mYear;
    int selectedYear;
    int selectedMonth;
    String selectedDate;
    String entryText;
    private ImageButton deleteBtn;
    private ImageButton saveBtn;
    private TextView lblDateSelected;
    private Spinner dateSpinner;
    private EditText txtJournalEntry;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    Journal j;
    private long selectedJournalID;
    JournalEntry je;

    String[] months = new String[] {"Month", "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        lblDateSelected     = (TextView) findViewById(R.id.dateSelected);
        dateSpinner         = (Spinner) findViewById(R.id.spinner);
        txtJournalEntry     = (EditText) findViewById(R.id.viewEditText);
        monthSpinner        = (Spinner) findViewById(R.id.monthSpinner);
        yearSpinner         = (Spinner) findViewById(R.id.yearSpinner);
        deleteBtn           = (ImageButton) findViewById(R.id.deleteBtn);
        saveBtn             = (ImageButton) findViewById(R.id.saveBtn);

        txtJournalEntry.setEnabled(false);
        dateSpinner.setEnabled(false);
        monthSpinner.setEnabled(false);
        deleteBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        j = IOManager.loadJournal(this);

        //Year Spinner
        loadYearSpinner();

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                    dateSpinner.setEnabled(false);
                    txtJournalEntry.setEnabled(false);
                    txtJournalEntry.setText("");
                    monthSpinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Month Spinner
        ArrayAdapter<String> monthsStringArrayAdapter= new ArrayAdapter<>
                (this,android.R.layout.simple_spinner_item, months);

        monthSpinner.setAdapter(monthsStringArrayAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedMonth = i + 2;
                    /*
                    String month = monthSpinner.getSelectedItem().toString();
                    char monthNumber = month.charAt(0);
                    selectedMonth = Integer.parseInt(String.valueOf(monthNumber));
                    **/
                    dateSpinner.setEnabled(true);
                    txtJournalEntry.setEnabled(false);
                    txtJournalEntry.setText("");

                    loadJournalEntrySpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Spinner with dates loaded in


        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selectedDate = dateSpinner.getSelectedItem().toString();
                    selectedJournalID = j.findJournal(selectedDate).getId();
                    txtJournalEntry.setText(j.getEntryById(selectedJournalID).getEntryText());
                    je = j.findJournal(selectedDate);

                    deleteBtn.setEnabled(true);
                    saveBtn.setEnabled(true);

                    txtJournalEntry.setEnabled(true);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    /**
     *
     * @param v
     */
    /*
    public void selectDate(View v) {

        datePickerDialog = new DatePickerDialog(ViewEditActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        lblDateSelected.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        mDay = dayOfMonth;
                        mMonth = monthOfYear;
                        mYear = year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    */
    public void deleteBtn(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.txtDeleteDialog));
        builder.setCancelable(true);

        //Add Buttons
        builder.setPositiveButton(
                R.string.txtYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        j.deleteEntry(selectedJournalID);
                        txtJournalEntry.setText("");

                        //If last Journal Entry is deleted return to previous screen ISSUE #4
                        if(j.getEntries().size() == 0){
                            finish();
                        }
                        loadJournalEntrySpinner();
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

        IOManager.saveJournal(this, j);
    }

    public void saveBtn(View v) {

        txtJournalEntry = (EditText) findViewById(R.id.viewEditText);
        entryText       = txtJournalEntry.getText().toString();

        j.updateEntry(selectedJournalID, entryText);

        IOManager.saveJournal(this, j);

        Toast.makeText(ViewEditActivity.this, "Your Diary entry was saved",
                Toast.LENGTH_LONG).show();
    }

    public void loadJournalEntrySpinner() {
        //Spinner with dates loaded in
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        j.myJournalEntries(selectedYear, selectedMonth));

        dateSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void loadYearSpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        j.yearEntries());

        yearSpinner.setAdapter(spinnerArrayAdapter);
    }


}

