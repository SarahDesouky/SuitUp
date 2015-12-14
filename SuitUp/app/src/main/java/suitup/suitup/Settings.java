package suitup.suitup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Settings extends Activity {

    EditText fname, lname, email, country;
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);
        String twitterId = settings.getString("twitter_id", "");
        api.getUser(twitterId, new Callback<models.User>() {

            public void success(User user, Response response) {
                fname = (EditText) findViewById(R.id.fNameText);
                fname.setText(user.getFname());
                lname = (EditText) findViewById(R.id.lNameText);
                lname.setText(user.getLname());
                email = (EditText) findViewById(R.id.emailText);
                email.setText(user.getEmail());
                country = (EditText) findViewById(R.id.countryText);
                country.setText(user.getCountry());
            }

            public void failure(RetrofitError error) {

            }
        });



    }

    public void saveChanges(View v){
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);
        String twitterId = settings.getString("twitter_id", "");
        api.updateUser(twitterId, fname.getText().toString(), lname.getText().toString(),
                email.getText().toString(), country.getText().toString(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {

                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void LogOutClicked(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        editor.putString("twitter_id","").commit();
        startActivity(intent);
    }
}
