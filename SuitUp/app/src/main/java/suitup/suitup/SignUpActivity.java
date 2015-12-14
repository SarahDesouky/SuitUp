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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs";
    String avatar;
    final Calendar myCalendar = Calendar.getInstance();
    static String twitter_id = "";
    static String image = "";


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
                String myFormat = "dd-MM-yyyy";
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

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        twitter_id = settings.getString("twitter_id", "");
        image = settings.getString("avatar_url", "");

    }

    public void SignUp(View view) {

        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String dateofbirth = ((EditText) findViewById(R.id.dob)).getText().toString() + "";
        String fname = ((EditText)findViewById(R.id.fname)).getText().toString();
        String lname = ((EditText)findViewById(R.id.lname)).getText().toString();
        String country = ((EditText)findViewById(R.id.country)).getText().toString();
        boolean female = ((RadioButton)findViewById(R.id.female)).isChecked();
        boolean male = ((RadioButton)findViewById(R.id.male)).isChecked();
        String gender = (female)? "Female": "Male";

//        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        final SharedPreferences.Editor editor = settings.edit();

//        editor.putString("username", username).commit();
//        editor.putString("email", email).commit();
//        editor.putString("dob", dateofbirth).commit();
//        editor.putString("avatar", avatar).commit();
//        editor.putString("country", country).commit();
//        editor.putString("fname", fname).commit();
//        editor.putString("lname", lname).commit();
//        editor.putString("gender", gender ).commit();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);
        api.CreateUser(fname, lname, dateofbirth, email,
                image, gender, country, twitter_id, new Callback<models.User>() {

            public void success(User user, Response response) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
