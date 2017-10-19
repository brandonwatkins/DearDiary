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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewEditActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    int mDay;
    int mMonth;
    int mYear;
    String entryText;
    private TextView lblDateSelected;
    private Spinner dateSpinner;
    private EditText txtJournalEntry;
    Journal j;
    private long selectedJournalID;
    JournalEntry je;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        lblDateSelected     = (TextView) findViewById(R.id.dateSelected);
        dateSpinner         = (Spinner) findViewById(R.id.spinner);
        txtJournalEntry     = (EditText) findViewById(R.id.viewEditText);

        j = IOManager.loadJournal(this);

        //Spinner with dates loaded in
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,
                        j.myJournalEntries());

        dateSpinner.setAdapter(spinnerArrayAdapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    txtJournalEntry.setText(j.getEntryAtIndex(i - 1).getEntryText());
                    selectedJournalID = j.getEntryAtIndex(i - 1).getId();
                    je = j.getEntryAtIndex(i - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

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

    public void saveBtn(View v) {

        txtJournalEntry = (EditText) findViewById(R.id.viewEditText);
        entryText       = txtJournalEntry.getText().toString();

        j.updateEntry(selectedJournalID, entryText);

        IOManager.saveJournal(this, j);

        Toast.makeText(ViewEditActivity.this, "Your Diary entry was saved",
                Toast.LENGTH_LONG).show();
    }
}
