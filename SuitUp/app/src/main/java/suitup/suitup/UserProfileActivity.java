package suitup.suitup;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity{


    ListView lstview;
    ArrayList<String> Posts = new ArrayList<String>();
    ArrayList<Uri> Images = new ArrayList<Uri>();
    private final int SELECT_PHOTO = 1;
    boolean imageUploaded = false;
    Uri imageToUpload;
    ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        lstview = (ListView)findViewById(R.id.list);
        adapter = new CustomPostsAdapterTest(this,Posts,Images);
        lstview.setAdapter(adapter);
//        String[] content = {"Mazlooom", "Magroo7", "Geraaaa7", "Ader we te3melha we tensa awam", "7abebty matet"};
        String image = StaticData.CurrentUser.avatar;
        String fname = StaticData.CurrentUser.fname;
        String lname = StaticData.CurrentUser.lname;
        try {

            new DownloadImageTask((ImageView) findViewById(R.id.avatar))
                    .execute(image);
        }catch(Exception e) {
            e.printStackTrace();
        }
        TextView name = (TextView)findViewById(R.id.username);
        name.setText(fname + " " + lname);
        TextView country = (TextView)findViewById(R.id.country);
        country.setText(StaticData.CurrentUser.country);
//        ArrayAdapter wallAdapter = new CustomAdapter(this, content);
//        ListView wallListView = (ListView) findViewById(R.id.wallListView);
//        wallListView.setAdapter(wallAdapter);
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
//        TextView posts = (TextView)findViewById(R.id.posts);
//        posts.append("\n" + post);
        //lstview.invalidateViews();
        adapter.notifyDataSetChanged();
    }
    public void Post(View view) {
        String post = ((EditText)findViewById(R.id.tweet)).getText().toString();
//        TextView posts = (TextView)findViewById(R.id.posts);
//        posts.append("\n" + post);
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
        Intent friendList = new Intent(view.getContext(), FriendList.class);
        startActivityForResult(friendList, 0);}

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
//                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        imageView.setImageBitmap(selectedImage);


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



