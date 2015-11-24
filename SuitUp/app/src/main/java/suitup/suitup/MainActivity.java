package suitup.suitup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "HIBihEkBH7TEt3rPXVLfj7VQh";
    private static final String TWITTER_SECRET = "0IA5sVAA76V4GDb0jylO5pw8MnA8lUdw2z4yEbkKUTQ5FiyPnV";
    private TwitterLoginButton loginButton;
    private Button logoutButton;
    ImageView avatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        logoutButton = (Button) findViewById(R.id.twitter_logout_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        User user = userResult.data;
                        //String twitterImage = user.profileImageUrlHttps.replace("_bigger", "");
                        String twitterImage = user.profileImageUrlHttps.replace("_normal", "");

                        try {
                            new DownloadImageTask((ImageView) findViewById(R.id.avatar))
                                    .execute(twitterImage);
                           avatar = (ImageView) findViewById(R.id.avatar);
                            avatar.setVisibility(View.VISIBLE);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        };

                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });

                loginButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void log_out(View view) {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
        logoutButton.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        avatar.setImageBitmap(null);
        avatar.setVisibility(View.GONE);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
