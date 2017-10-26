package edu.mhu.cs.brandonwatkins.deardiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final static String DIARY_PREFS    = "edu.mhu.cs.bwatkins.DIARY_PREFS";
    private final static String APP_PASS    = "edu.mhu.cs.bwatkins.APP_PASS";

    private String storedPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadPassword();

        if (storedPassword == null){
            showNewPasswordField();
        }
    }

    /**
     * Loads password from Shared Preferences
     */
    public void loadPassword() {
        SharedPreferences prefs = this.getSharedPreferences(DIARY_PREFS, 0);

        storedPassword = prefs.getString(APP_PASS, null);

        Log.d("Dear Diary", "Loading Password " + storedPassword);
    }

    /**
     * Saves password to Shared Preferences
     */
    private void savePassword() {
        Log.d("Dear Diary", "Saving password" + storedPassword);
        SharedPreferences prefs = this.getSharedPreferences(DIARY_PREFS, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(APP_PASS, storedPassword);
        editor.commit();
    }

    /**
     * Shows the password field for a new password entry. Also shows the save password
     * button when needed
     */
    public void showNewPasswordField(){
        TextView lblNewPassword     = (TextView) findViewById(R.id.textViewNewPassword);
        EditText txtNewPassword     = (EditText) findViewById(R.id.newPassword);
        TextView helpNewPassword    = (TextView) findViewById(R.id.textViewReEnterPassword);
        EditText txtPassword        = (EditText) findViewById(R.id.password);
        ImageButton saveBtn         = (ImageButton) findViewById(R.id.saveBtn);

        lblNewPassword.setVisibility(View.VISIBLE);
        txtNewPassword.setVisibility(View.VISIBLE);
        helpNewPassword.setVisibility(View.VISIBLE);
        txtPassword.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);

    }

    /**
     * Verifies if the password entered matches the password stored in the
     * Shared Preferences.
     * @return true or false if the password does or doesn't match
     */
    public boolean verifyPassword(){
        EditText txtPassword        = (EditText) findViewById(R.id.password);

        String pwd = txtPassword.getText().toString();

        loadPassword();

        if(pwd.equals(storedPassword)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Button that takes the user to the AddViewActivity if the password
     * entered is correct.
     * @param v
     */
    public void unlockBtn(View v) {
        Intent i = new Intent(this, AddViewActivity.class);

        EditText txtPassword = (EditText) findViewById(R.id.password);

        if (txtPassword.getText().toString().length() == 0) {
            txtPassword.setError("You must enter a Password");
            return;
        }

        if (verifyPassword() == true) {
            startActivity(i);
        } else {
            txtPassword.setError("Incorrect Password");
        }

    }

    /**
     * Button used to save the password entered by the user
     * @param view
     */
    public void savePasswordBtn(View view) {
        Intent i = new Intent(this, AddViewActivity.class);

        EditText txtPassword        = (EditText) findViewById(R.id.password);
        EditText txtNewPassword     = (EditText) findViewById(R.id.newPassword);

        String pwd      = txtPassword.getText().toString();
        String newPwd   = txtNewPassword.getText().toString();

        Log.d("Dear Diary", "Password " + pwd);
        Log.d("Dear Diary", "New Password " + newPwd);

        if (pwd.equals(newPwd)) {
            storedPassword = newPwd;
            savePassword();
            startActivity(i);
        } else {
            txtNewPassword.setError("Your Passwords do not match");
        }

    }

}
