package suitup.suitup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {
    Button save;
    EditText fname, lname, email;
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        fname = (EditText) findViewById(R.id.fNameText);
        fname.setText(settings.getString("fname", "First Name"));
        lname = (EditText) findViewById(R.id.lNameText);
        lname.setText(settings.getString("lname", "Last Name"));
        email = (EditText) findViewById(R.id.emailText);
        email.setText(settings.getString("email", "Email"));
    }

    public void saveChanges(View v){
        editor.putString("fname",fname.getText().toString()).putString("lname", lname.getText().toString()).putString("email", email.getText().toString()).commit();
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        startActivity(intent);
    }

    public void LogOutClicked(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
