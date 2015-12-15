package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MembersListActivity extends AppCompatActivity{

    ArrayAdapter adapter1;
    ListView usersList;
    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        api.getAllUsers(new Callback<List<models.User>>() {
            @Override
            public void success(List<User> users, Response response) {
                for(int i =0;i<users.size();i++)
                {
                    if(users.get(i).getTwitter_id().equals(settings.getString("twitter_id","-1"))) {
                        users.remove(i);
                        break;
                    }
                }
                adapter1 = new FriendsAdapter(MembersListActivity.this, users);
                usersList = (ListView) findViewById(R.id.usersList);
                usersList.setAdapter(adapter1);
                usersList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        models.User user = (models.User) parent.getItemAtPosition(position);
                        editor.putString("user_id", String.valueOf(user.getId())).commit();
                        Intent intent = new Intent(getApplicationContext(), MemberProfile.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("nooooo", "nooo");
            }
        });


    }

}
