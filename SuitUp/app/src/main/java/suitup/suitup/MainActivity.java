 package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "HIBihEkBH7TEt3rPXVLfj7VQh";
    private static final String TWITTER_SECRET = "0IA5sVAA76V4GDb0jylO5pw8MnA8lUdw2z4yEbkKUTQ5FiyPnV";
    private TwitterLoginButton loginButton;
    private Button logoutButton;
    ImageView avatar;
    public static final String PREFS_NAME = "MyPrefs";
    public static final String USER_NAME_INTENT = "user";
    public static final String USER_AVATAR_INTENT = "avatar";

    String twitterImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
        setContentView(R.layout.activity_main);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString("twitter_id", "").commit();
        //editor.clear().commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        String idstored = settings.getString("twitter_id", "");
        boolean AlreadyIn = (idstored == "") ? false : true;
        if (AlreadyIn) {
            Intent intent = new Intent(getApplicationContext(), Timeline.class);
            startActivity(intent);
        } else {
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    final TwitterSession session = result.data;
                    Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
                        @Override
                        public void success(Result<User> userResult) {
                            User user = userResult.data;
                            twitterImage = user.profileImageUrlHttps.replace("_normal", "");
                            String twitter_id = String.valueOf(session.getUserId());
                            String twitterId = String.valueOf(session.getUserId());
                            editor.putString("avatar_url", twitterImage).commit();
                            editor.putString("twitter_id", String.valueOf(session.getUserId())).commit();
                            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
                            ourAPI api = adapter.create(ourAPI.class);
                            api.getUser(twitterId, new retrofit.Callback<models.User>() {
                                public void success(models.User user, Response response) {
                                    if(user!=null) {
                                        Intent intent = new Intent(getApplicationContext(), Timeline.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                        intent.putExtra(USER_NAME_INTENT, session.getUserName());
                                        intent.putExtra(USER_AVATAR_INTENT, twitterImage);
                                        startActivity(intent);
                                    }
                                }

                                public void failure(RetrofitError error) {
                                    Log.d("ERROORR ", error.toString(), error);

                                }
                            });
                        }
                        public void failure(TwitterException e) {

                        }
                    });

                    loginButton.setVisibility(View.GONE);
                }
                public void failure(TwitterException exception) {
                    Log.d("TwitterKit", "Login with Twitter failure", exception);
                }
            });

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
