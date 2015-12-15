package suitup.suitup;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.util.SortedList.Callback;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.*;
import models.Post;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserProfileActivity extends AppCompatActivity{

    ListView postsList;
    ArrayList<User> postOwners = new ArrayList<models.User>();
    private final int SELECT_PHOTO = 1;
    boolean imageUploaded = false;
    Uri imageToUpload;
    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";





    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapter.create(ourAPI.class);
        final String twitterId = settings.getString("twitter_id", "");
        api.getUser(twitterId, new retrofit.Callback<models.User>() {

            public void success(models.User user, Response response) {
                String fname = user.getFname();
                String lname = user.getLname();
                String country = user.getCountry();
                String gender = user.getGender();
                String avatar = user.getAvatar_url();
                String email = user.getEmail();
                String dateofbirth = user.getDate_of_birth();
                ((TextView) findViewById(R.id.username)).setText(fname + " " + lname);
                ((TextView) findViewById(R.id.country)).setText(country);
                ((TextView) findViewById(R.id.dateofbirth)).setText("Birthday: " + dateofbirth);
                ((TextView) findViewById(R.id.email)).setText(email);
                ((TextView) findViewById(R.id.gender)).setText(gender);
                try {
                    new DownloadImageTask((ImageView) findViewById(R.id.avatar))
                            .execute(avatar);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                api.getMyPosts(twitterId, new Callback<List<models.Post>>() {
                    public void success(List<Post> posts, Response response) {
                        for (int i=0; i<posts.size(); i++){
                            String ownerID = String.valueOf(posts.get(i).getOwner_id());
                            api.getFriend(ownerID, new retrofit.Callback<models.User>() {
                                public void success(models.User user, Response response) {
                                    postOwners.add(user);
                                    Log.d("owners",user.getEmail());
                                }
                                public void failure(RetrofitError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                    Log.d("owners","No");
                                }
                            });
                        }
                        ArrayAdapter<models.Post> adapter = new PostsAdapter(getApplicationContext(), posts, postOwners);
                        postsList = (ListView) findViewById(R.id.list);
                        postsList.setAdapter(adapter);
                    }

                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }

            public void failure(RetrofitError error) {
            }
        });

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

//    public void Tweet(View view) {
//        String post = ((EditText)findViewById(R.id.tweet)).getText().toString();
//        TweetComposer.Builder builder;
//        if(imageUploaded) {
//            builder = new TweetComposer.Builder(this)
//                    .text(post) .image(imageToUpload);
//        }
//        else {
//            builder = new TweetComposer.Builder(this)
//                    .text(post);
//        }
//        builder.show();
//        adapter2.notifyDataSetChanged();
//    }
//    public void Post(View view) {
//        String post = ((EditText)findViewById(R.id.tweet)).getText().toString();
//        if(imageUploaded) {
//            Images.add(imageToUpload);
//        }
//        else {
//            Images.add(Uri.EMPTY);
//        }
//        Posts.add(post);
//        adapter2.notifyDataSetChanged();
//
//    }

    public void viewFriends(View view){
        Intent friendList = new Intent(view.getContext(), FriendsListActivity.class);
        startActivityForResult(friendList, 0);
    }

    public void uploadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){

                    final Uri imageUri = imageReturnedIntent.getData();
                    imageToUpload = imageUri;
                    imageUploaded = true;
                    ImageView viewimage = (ImageView)findViewById(R.id.imagetest);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                        viewimage.setImageBitmap(bitmap);
                        viewimage.setVisibility(View.VISIBLE);
                        Button cancel = (Button)findViewById(R.id.cancel);
                        cancel.setVisibility(View.VISIBLE);
                        Button upload = (Button)findViewById(R.id.upload_btn);
                        upload.setVisibility(View.GONE);

                    }
                    catch(Exception e) {
                        String s = e.toString();
                    }
                }
        }
    }

    public void viewSettings(View view){
        Intent settings = new Intent(view.getContext(), Settings.class);
        startActivityForResult(settings, 0);
    }

    public void viewMsgs(View view){
        Intent msg = new Intent(view.getContext(), AllMessagesActivity.class);
        startActivityForResult(msg, 0);}

    public void viewTimeline(View view){
        Intent msg = new Intent(view.getContext(), Timeline.class);
        startActivityForResult(msg, 0);}

    public void removeImage(View view) {
        ImageView viewimage = (ImageView)findViewById(R.id.imagetest);
        viewimage.setVisibility(View.GONE);
        viewimage.setImageBitmap(null);
        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        imageUploaded = false;
        imageToUpload = null;
        Button upload = (Button)findViewById(R.id.upload_btn);
        upload.setVisibility(View.VISIBLE);

    }
}



