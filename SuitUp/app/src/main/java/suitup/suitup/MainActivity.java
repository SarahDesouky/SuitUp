package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        //editor.clear().commit();
        if(!settings.contains("ID_ARRAY_SIZE")) {
            editor.putInt("ID_ARRAY_SIZE",0);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        logoutButton = (Button) findViewById(R.id.twitter_logout_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                final TwitterSession session = result.data;
                Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        User user = userResult.data;
                        twitterImage = user.profileImageUrlHttps.replace("_normal", "");
                        boolean signedUp = AlreadySignedUp(session.getUserId());
                        if (!signedUp) {
                            AddId(session.getUserId());
                            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                            intent.putExtra(USER_NAME_INTENT, session.getUserName());
                            intent.putExtra(USER_AVATAR_INTENT, twitterImage);
                            startActivity(intent);
                        }
                        else {
                            // Toast.makeText(getApplicationContext(), StaticData.CurrentUser.username, Toast.LENGTH_LONG).show();
                            getUser(session.getUserId(),twitterImage);
                            Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
                            startActivity(intent);
                        }

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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        //avatar.setImageBitmap(null);
        //avatar.setVisibility(View.GONE);
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

    public boolean AlreadySignedUp(Long ID) {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        int numberOfIDs = settings.getInt("ID_ARRAY_SIZE",0);
        for(int i=1; i<=numberOfIDs;i++) {
            long id = settings.getLong("ID_"+i, 0);
            if(id == ID)
                return true;
        }
        return false;
    }
    public void AddId(Long ID) {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        int numberOfIDs = settings.getInt("ID_ARRAY_SIZE",0);
        settings.edit().putInt("ID_ARRAY_SIZE", (numberOfIDs+1)).commit();
        editor.putLong("ID_" + (numberOfIDs + 1), ID).commit();
        editor.putString("avatar_" + (numberOfIDs + 1), twitterImage).commit();
        StaticData.CurrentUser.avatar = twitterImage;

    }

    public suitup.suitup.User getUser(Long ID, String twitterImage) {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        suitup.suitup.User user = new suitup.suitup.User();
        final SharedPreferences.Editor editor = settings.edit();
        int numberOfIDs = settings.getInt("ID_ARRAY_SIZE",0);
        for(int i=1; i<=numberOfIDs;i++) {
            long id = settings.getLong("ID_"+i, 0);
            if(id == ID){
                String username = settings.getString("username_" + i, null);
                String email = settings.getString("email_"+i, null);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                String date = settings.getString("dob_" + i, null);
                String image =  settings.getString("avatar" + i, twitterImage);
                Date dob = new Date();
                try {
                    dob = dateFormat.parse(date);
                }catch(ParseException e){e.printStackTrace();}
                StaticData.CurrentUser.username = username;
                StaticData.CurrentUser.email = email;
                StaticData.CurrentUser.dob = dob;
                StaticData.CurrentUser.avatar = image;
            }

        }
        return StaticData.CurrentUser;
    }

    protected  void onResume(){
        super.onResume();
//        if(loginButton.getVisibility()==View.GONE){
//            Intent intent = new Intent(this, UserProfileActivity.class);
//            startActivity(intent);
//        }
    }
}
