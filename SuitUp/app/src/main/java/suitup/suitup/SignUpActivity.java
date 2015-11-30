package suitup.suitup;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs";
    String avatar;
    final Calendar myCalendar = Calendar.getInstance();


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

        final EditText date_of_birth = (EditText)findViewById(R.id.dob);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                date_of_birth.setText(sdf.format(myCalendar.getTime()));
            }

        };
        date_of_birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void SignUp(View view) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String dateofbirth = ((EditText) findViewById(R.id.dob)).getText().toString();
        String fname = ((EditText)findViewById(R.id.fname)).getText().toString();
        String lname = ((EditText)findViewById(R.id.lname)).getText().toString();
        String country = ((EditText)findViewById(R.id.country)).getText().toString();
        boolean female = ((RadioButton)findViewById(R.id.female)).isChecked();
        boolean male = ((RadioButton)findViewById(R.id.male)).isChecked();


//        DatePicker dob = (DatePicker)findViewById(R.id.dob);
        Date dob = new Date(myCalendar.get(Calendar.DATE));
        StaticData.CurrentUser.username = username;
        StaticData.CurrentUser.email = email;
        StaticData.CurrentUser.dob = dob;
        StaticData.CurrentUser.avatar = avatar;
        StaticData.CurrentUser.fname =fname;
        StaticData.CurrentUser.lname = lname;
        StaticData.CurrentUser.country = country;
        StaticData.CurrentUser.gender = (female)?"Female":"Male";

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        int numberOfIDs = settings.getInt("ID_ARRAY_SIZE", 0);
        editor.putString("username_" + (numberOfIDs), username).commit();
        editor.putString("email_" + (numberOfIDs), email).commit();
        editor.putString("dob_" + (numberOfIDs), dateofbirth).commit();
        editor.putString("avatar_" + (numberOfIDs), avatar).commit();
//        Intent intent = new Intent(this.getBaseContext(), Timeline.class);
//        startActivity(intent);

        //Uncomment to redirect to My Mssages View
        Intent intent = new Intent(this.getBaseContext(), UserProfileActivity.class);

        startActivity(intent);
    }

}
