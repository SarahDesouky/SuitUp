package suitup.suitup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import models.*;
import models.Post;

public class PostsAdapter extends ArrayAdapter<Post> {

    List<Post> posts;
    List<String> postOwners;

    PostsAdapter(Context context, List<Post> posts) {
        super(context, R.layout.custom_row_posts, posts);
        this.posts = posts;
        this.postOwners = postOwners;
    }

    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row, parent, false);
        ImageView postImage = (ImageView)CustomView.findViewById(R.id.imageWall);
        TextView postText = (TextView)CustomView.findViewById(R.id.textWall);
//        TextView postOwner = (TextView)CustomView.findViewById(R.id.poster);
        Post p = posts.get(position);
        String text = p.getText();
        postText.setText(text);
//        String owner = postOwners.get(posts.indexOf(p));
//        postOwner.setText("2araaaaaf");
        String image = p.getImage_url();
        if (image != null){
            try {
                new DownloadImageTask(postImage).execute(image);
            } catch(Exception e) {
                e.printStackTrace();
            }
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
