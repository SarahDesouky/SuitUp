package suitup.suitup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.Post;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Timeline extends AppCompatActivity {
    private ArrayAdapter adapter2;
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;
    private ArrayList<String> content = new ArrayList<>();
    private Button myProfile;
    RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
    ourAPI api = adapter.create(ourAPI.class);
    ListView timeline;
    ArrayList<String> usersNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        setContentView(R.layout.activity_timeline);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, content);
//            ArrayList<Integer>icons = new ArrayList<>();
            final ArrayList<Post> posts = new ArrayList<>();
             usersNames = new ArrayList<>();
        api.getFriends(settings.getString("twitter_id", ""), new Callback<List<models.User>>() {
            @Override
            public void success(List<User> users, Response response) {
//                users =(ArrayList) users1;
                for (int i = 0; i < users.size(); i++) {
                    usersNames.add(users.get(i).getFname() + " "+users.get(i).getLname());
                    api.getMyPosts(users.get(i).getId() + "", new Callback<List<Post>>() {
                        @Override
                        public void success(List<Post> postsList, Response response) {
                            posts.addAll(postsList);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        adapter2 = new PostsAdapter(Timeline.this, posts,usersNames);
        timeline = (ListView)findViewById(R.id.timelineList);
        timeline.setAdapter(adapter2);
    }




//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        Object o = this.getListAdapter().getItem(position);
//        String post = o.toString();
//        Log.d(post, post);
//        editor.putString("post", post);
//        editor.commit();
//        PostActivity pa = new PostActivity();
//        Intent intent = new Intent(getApplicationContext(), pa.getClass());
//        startActivity(intent);
//    }
    public  void myProfileClicked(View v){
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        startActivity(intent);

    }
    public  void AllMembers(View v){
        Intent intent = new Intent(getApplicationContext(), MembersListActivity.class);
        startActivity(intent);
    }
}
