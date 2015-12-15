package suitup.suitup;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PostActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;
    static EditText editText;
    private ArrayAdapter adapter2;
    ArrayList<models.User> authors;

    String twitterId = "";
    String postId = "";
    String ownerId = "";
    ArrayAdapter commentsAdapter;
    int i = 0;

    public PostActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        twitterId = settings.getString("twitter_id", "");
        postId = settings.getString("post_id", "");
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapter.create(ourAPI.class);
        api.getUser(twitterId, new Callback<models.User>() {
            @Override
            public void success(User user, Response response) {
                ownerId = String.valueOf(user.getId());
                api.getAllComments(postId, new Callback<List<models.Comment>>() {
                    @Override
                    public void success(final List<models.Comment> comments, Response response) {
                        authors = new ArrayList<models.User>();

                        for (i = 0; i < comments.size(); i++) {
                            api.getFriend(String.valueOf(comments.get(i).getOwner_id()), new Callback<User>() {
                                @Override
                                public void success(User user, Response response) {
                                    authors.add(user);
                                    if(authors.size()==comments.size()){
                                      //  Toast.makeText(getApplicationContext(), "FULLL", Toast.LENGTH_LONG).show();
                                        commentsAdapter = new CommentsAdapter(getApplicationContext(), authors, comments);
                                        ListView commentsList = (ListView)findViewById(R.id.listcomments);
                                        commentsList.setAdapter(commentsAdapter);
                                    }
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    String msg = error.toString();
                                }
                            });
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }


            @Override
            public void failure(RetrofitError error) {
                String msg = error.toString();
            }
        });



    }
    public void postButtonClicked(View v){
        editText = (EditText) findViewById(R.id.editText);
        String text=editText.getText().toString();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapter.create(ourAPI.class);

        api.AddComment(twitterId, ownerId, text , postId, new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                api.getAllComments(postId, new Callback<List<models.Comment>>() {
                    @Override
                    public void success(final List<models.Comment> comments, Response response) {
                        authors = new ArrayList<models.User>();

                        for (i = 0; i < comments.size(); i++) {
                            api.getFriend(String.valueOf(comments.get(i).getOwner_id()), new Callback<User>() {
                                @Override
                                public void success(User user, Response response) {
                                    authors.add(user);
                                    if (authors.size() == comments.size()) {
                                        //  Toast.makeText(getApplicationContext(), "FULLL", Toast.LENGTH_LONG).show();
                                        commentsAdapter = new CommentsAdapter(getApplicationContext(), authors, comments);
                                        ListView commentsList = (ListView) findViewById(R.id.listcomments);
                                        commentsList.setAdapter(commentsAdapter);
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    String msg = error.toString();
                                }
                            });
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
