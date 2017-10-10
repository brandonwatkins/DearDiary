package edu.mhu.cs.brandonwatkins.deardiary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewEditActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    int mDay;
    int mMonth;
    int mYear;
    private TextView lblDateSelected;
    private Spinner dateSpinner;
    Journal loadedJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        lblDateSelected = (TextView) findViewById(R.id.dateSelected);
        dateSpinner = (Spinner) findViewById(R.id.spinner);


    }

        //Journal j = IOManager.loadJournal(this);

        /*Spinner with dates loaded in
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        dateSpinner.setAdapter(spinnerArrayAdapter);
    }
    */
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
                        //Code to delete entry
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

    }
}
