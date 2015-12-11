package suitup.suitup;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserActivityTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activity_test);

        String twitterId = "266629394";

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);
        api.getUser(twitterId, new Callback<User>() {

            public void success(models.User user, Response response) {
                String fname = user.getFname();
                TextView name = (TextView)findViewById(R.id.fname);
                name.setText(fname);
            }

            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Make sure you are online", Toast.LENGTH_LONG).show();
            }
        });

    }
}