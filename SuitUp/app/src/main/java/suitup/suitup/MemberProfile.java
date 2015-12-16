package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.InputStream;
import java.util.ArrayList;

import models.*;
import models.Post;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MemberProfile extends Activity {

    ArrayList<String> Posts = new ArrayList<String>();
    ArrayList<Uri> Images = new ArrayList<Uri>();
    private final int SELECT_PHOTO = 1;
    boolean imageUploaded = false;
    Uri imageToUpload;
    ArrayAdapter adapter2;
    TextView myFriend;
    Button b;
    String myId= "";

    ListView postsList;
    ArrayList<String> postOwnersIDs = new ArrayList<String>();
    ArrayList<User> postOwners = new ArrayList<models.User>();
    List<Post> myPosts;

    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";
    String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        myFriend = (TextView) findViewById(R.id.textView4);
        myFriend.setTextColor(Color.parseColor("#40E0D0"));
        b = (Button) findViewById(R.id.button);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapter.create(ourAPI.class);
        friendId = settings.getString("user_id", "");
        api.getFriend(friendId, new retrofit.Callback<models.User>() {

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

                String twitterId = settings.getString("twitter_id", "");
                api.isFriend(twitterId, friendId, new Callback<models.User>() {
                    @Override
                    public void success(models.User user, Response response) {
                        if(user!=null) {
                            myFriend.setText("Friends");
                            b.setText("Remove Friend");
                        }
                        else {
                            myFriend.setText("");
                            b.setText("Add Friend");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {


                    }
                });

                api.getMyPostsByID(friendId, new Callback<List<models.Post>>() {
                    public void success(List<Post> posts, Response response) {
                        for (int i = 0; i < posts.size(); i++) {
                            String ownerID = String.valueOf(posts.get(i).getOwner_id());
                            postOwnersIDs.add(ownerID);
                        }
                        myPosts = posts;
                        for (int i = 0; i < postOwnersIDs.size(); i++) {
                            api.getFriend(postOwnersIDs.get(i), new retrofit.Callback<models.User>() {
                                public void success(models.User user, Response response) {
                                    postOwners.add(user);
                                    if (postOwners.size() == myPosts.size()) {
                                        ArrayAdapter<models.Post> adapter3 = new PostsAdapter(getApplicationContext(), myPosts, postOwners);
                                        postsList = (ListView) findViewById(R.id.list);
                                        postsList.setAdapter(adapter3);
                                        postsList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                models.Post post = (models.Post) parent.getItemAtPosition(position);
                                                editor.putString("post_id", String.valueOf(post.getId())).commit();
                                                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }

                                public void failure(RetrofitError error) {
                                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
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

    public void Tweet(View view) {
        String post = ((EditText)findViewById(R.id.tweet)).getText().toString();
        TweetComposer.Builder builder;
        if(imageUploaded) {
            builder = new TweetComposer.Builder(this)
                    .text(post) .image(imageToUpload);
        }
        else {
            builder = new TweetComposer.Builder(this)
                    .text(post);
        }
        builder.show();
        adapter2.notifyDataSetChanged();
    }

    public void Post(View view) {
        String post = ((EditText)findViewById(R.id.tweet)).getText().toString();
        if(imageUploaded) {
            Images.add(imageToUpload);
        }
        else {
            Images.add(Uri.EMPTY);
        }
        Posts.add(post);
        adapter2.notifyDataSetChanged();
    }

//    public void viewFriends(View view){
//        Intent friendList = new Intent(view.getContext(), FriendsListActivity.class);
//        startActivityForResult(friendList, 0);
//    }

    public void uploadImage(View view){
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

    public void viewMsgs(View view){
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapter.create(ourAPI.class);
        myId = settings.getString("my_id","");
        api.findThread(myId, friendId, new Callback<MessageThread>() {
            @Override
            public void success(MessageThread messageThread, Response response) {
                if(messageThread!=null) {
                    editor.putString("thread_id", messageThread.getId().toString());
                    Intent msg = new Intent(getApplicationContext(), MessageActivity.class);
                    startActivity(msg);
                }
                else {
                    api.AddThread(myId, friendId, new Callback<MessageThread>() {
                        @Override
                        public void success(MessageThread messageThread, Response response) {
                            editor.putString("thread_id", messageThread.getId().toString()).commit();
                            Intent msg = new Intent(getApplicationContext(), MessageActivity.class);
                            startActivity(msg);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                String msg = error.toString();
            }
        });

    }

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

    public void removeOrAddFriend(View view){

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        String twitterId = settings.getString("twitter_id", "");

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);

        if(b.getText()=="Remove Friend") {
            api.removeFriend(twitterId, friendId, new Callback<models.User>() {
                @Override
                public void success(models.User user, Response response) {
                    String name = user.getFname();
                    b.setText("Add Friend");
                    myFriend.setText("");
                }

                @Override
                public void failure(RetrofitError error) {
                    String msg = error.toString();
                }
            });

        }
        else {
            api.addFriend(twitterId, friendId, new Callback<models.User>() {
                @Override
                public void success(models.User user, Response response) {
                    b.setText("Remove Friend");
                    myFriend.setText("Friends");
                }

                @Override
                public void failure(RetrofitError error) {
                    String msg = error.toString();
                }
            });

        }
    }
}

