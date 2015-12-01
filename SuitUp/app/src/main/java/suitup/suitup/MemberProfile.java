package suitup.suitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.InputStream;
import java.util.ArrayList;

public class MemberProfile extends Activity {

    ListView lstview;
    ArrayList<String> Posts = new ArrayList<String>();
    ArrayList<Uri> Images = new ArrayList<Uri>();
    private final int SELECT_PHOTO = 1;
    boolean imageUploaded = false;
    Uri imageToUpload;
    ArrayAdapter adapter;
    String value;
    TextView myFriend;
    Button b;


    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        myFriend = (TextView) findViewById(R.id.textView4);
        myFriend.setTextColor(Color.parseColor("#40E0D0"));
        b = (Button) findViewById(R.id.button);
        lstview = (ListView)findViewById(R.id.list);
        adapter = new CustomPostsAdapterTest(this,Posts,Images);
        lstview.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("friendName");
        }
        String fname = value;
        String lname = "";

        TextView name = (TextView)findViewById(R.id.username);
        name.setText(fname + " " + lname);
        TextView country = (TextView) findViewById(R.id.country);
        country.setText(StaticData.CurrentUser.country);
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
        adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
    }

    public void viewFriends(View view){
        Intent friendList = new Intent(view.getContext(), FriendsListActivity.class);
        startActivityForResult(friendList, 0);
    }

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

    public void viewSettings(View view){
        Intent settings = new Intent(view.getContext(), Settings.class);
        startActivityForResult(settings, 0);
    }

    public void viewMsgs(View view){
        String friendName = ((TextView)findViewById(R.id.username)).getText().toString();
        Message newMessage = new Message("", StaticData.CurrentUser.fname,friendName );
        Intent msg = new Intent(view.getContext(), MessageActivity.class);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString("friendname", friendName).commit();
        startActivityForResult(msg, 0);
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
        if(b.getText()=="Remove Friend") {
            b.setText("Add Friend");
            myFriend.setText("");
        }
        else {
            b.setText("Remove Friend");
            myFriend.setText("My Friend");
        }
    }
}

