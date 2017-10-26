package edu.mhu.cs.brandonwatkins.deardiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);
    }

    /**
     * Button that takes you to the ViewEditActivity
     * @param v
     */
    public void viewPreviousBtn(View v) {
        Intent i = new Intent(this, ViewEditActivity.class);
        startActivity(i);
    }

    /**
     * Button that takes you to the AddNewEntryActivity
     * @param v
     */
    public void newEntryBtn(View v) {
        Intent i = new Intent(this, AddNewEntryActivity.class);
        startActivity(i);
    }

}
