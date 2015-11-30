package suitup.suitup;

import android.app.ListActivity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import java.io.InputStream;
import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity{

//
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String[] content = {"Mazlooom", "Magroo7", "Geraaaa7", "Ader we te3melha we tensa awam", "7abebty matet"};

        ArrayAdapter wallAdapter = new CustomAdapter(this, content);
        ListView wallListView = (ListView) findViewById(R.id.wallListView);
        wallListView.setAdapter(wallAdapter);
    }

//        wallListView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String content =String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(UserProfileActivity.this,content,Toast.LENGTH_LONG).show();
//                    }}
//
//                    );}



    //ListView wallListView =(ListView)findViewById(R.id.);

//        setListAdapter(adapter);
//        ArrayList<Integer>icons = new ArrayList<>();
//
//
//        for(int i =0;i<content.size();i++)
//            icons.add(R.drawable.mohee);
//        adapter3 = new CustomListAdapter(UserProfileActivity.this, content, icons);
//        setListAdapter(adapter3);
//    }



    public void viewFriends(View view){
        Intent friendList = new Intent(view.getContext(), FriendList.class);
        startActivityForResult(friendList, 0);

    }
    public void viewSettings(View view){
        Intent settings = new Intent(view.getContext(), Settings.class);
        startActivityForResult(settings, 0);
    }
    public void viewMsgs(View view){
        Intent msg = new Intent(view.getContext(), AllMessagesActivity.class);
        startActivityForResult(msg, 0);}


}


