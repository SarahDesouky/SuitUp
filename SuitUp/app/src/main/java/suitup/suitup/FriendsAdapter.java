package suitup.suitup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.*;
import models.User;

/**
 * Created by sarah on 12/14/2015.
 */
public class FriendsAdapter extends ArrayAdapter<User> {

    List<User> friends;


    FriendsAdapter(Context context, List<User> friends) {
        super(context, R.layout.custom_row_posts, friends);
        this.friends = friends;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row, parent, false);
        ImageView avatarView = (ImageView)CustomView.findViewById(R.id.imageWall);
        TextView nameView = (TextView)CustomView.findViewById(R.id.textWall);

        User friend = friends.get(position);
        String name = friend.getFname() + " " + friend.getLname();
        nameView.setText(name);
        String avatar = friend.getAvatar_url();
//        Toast.makeText(this.getContext()
//                , "Reached here", Toast.LENGTH_LONG).show();

        try {
            new DownloadImageTask(avatarView)
                    .execute(avatar);
        }catch(Exception e) {
            e.printStackTrace();
        }


        return CustomView;
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
