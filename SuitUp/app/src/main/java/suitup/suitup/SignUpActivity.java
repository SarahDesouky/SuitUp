package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs";
    String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.USER_NAME_INTENT);
        avatar = intent.getStringExtra(MainActivity.USER_AVATAR_INTENT);
        EditText username = (EditText)findViewById(R.id.username);
        username.setText(name);
    }

    public void SignUp(View view){
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        DatePicker dob = (DatePicker)findViewById(R.id.dob);
        Date date_of_birth = new Date(dob.getYear(),dob.getMonth(),dob.getDayOfMonth());
        StaticData.CurrentUser.username = username;
        StaticData.CurrentUser.email = email;
        StaticData.CurrentUser.dob = date_of_birth;
        StaticData.CurrentUser.avatar = avatar;

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        int numberOfIDs = settings.getInt("ID_ARRAY_SIZE",0);
        editor.putString("username_" + (numberOfIDs), username).commit();
        editor.putString("email_"+(numberOfIDs), email).commit();
        editor.putString("dob_"+(numberOfIDs), date_of_birth.toString()).commit();
        editor.putString("avatar_"+(numberOfIDs), avatar).commit();
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        startActivity(intent);

    }

}
