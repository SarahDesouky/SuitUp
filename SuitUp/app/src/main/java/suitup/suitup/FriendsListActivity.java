package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FriendsListActivity extends AppCompatActivity {

    CustomListAdapter adapter;
    ArrayList<String> content = new ArrayList<>();
    ArrayList<Integer>icons = new ArrayList<>();
    public static final String PREFS_NAME = "MyPrefs";
    ListView friendsList;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        String twitterId = settings.getString("twitter_id", "");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);
        api.getFriends(twitterId, new Callback<List<models.User>>() {
            @Override
            public void success(List<models.User> friends, Response response) {
                ArrayAdapter<models.User> adapter = new FriendsAdapter(getApplicationContext(), friends);
                friendsList = (ListView) findViewById(R.id.friendlist);
                friendsList.setAdapter(adapter);
                friendsList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.v("Module Item Trigger", "Module item was triggered");
                        models.User friend = (models.User) parent.getItemAtPosition(position);
                        editor.putString("friend_id", String.valueOf(friend.getId())).commit();
                        Intent intent = new Intent(getApplicationContext(), FriendProfile.class);
                        startActivity(intent);
                    }
                });
            }

            public void failure(RetrofitError error) {
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                String e = error.toString();

            }
        });



    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        Intent intent = new Intent(getApplicationContext(), FriendProfile.class);
//        intent.putExtra("friendName", content.get(position));
//        startActivity(intent);
    }

}
