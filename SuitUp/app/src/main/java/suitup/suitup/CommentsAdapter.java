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
import models.Comment;
import models.User;

public class CommentsAdapter extends ArrayAdapter {

    ArrayList<models.User> authors;
    List<models.Comment> comments;


    CommentsAdapter(Context context, ArrayList<models.User> a, List<models.Comment> c) {
        super(context, R.layout.custom_row_posts, a);
        this.authors = a;
        this.comments = c;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row_posts, parent, false);
        ImageView avatarView = (ImageView)CustomView.findViewById(R.id.image);
        TextView nameView = (TextView)CustomView.findViewById(R.id.poster);
        TextView commentt = (TextView)CustomView.findViewById(R.id.text);

        User author = authors.get(position);
        Comment comment = comments.get(position);
        String name = author.getFname() + " " + author.getLname();
        nameView.setText(name);
       // String avatar = author.getAvatar_url();
        String commentText = comment.getText();
        commentt.setText(commentText);
//        Toast.makeText(this.getContext()
//                , "Reached here", Toast.LENGTH_LONG).show();

//        try {
//            new DownloadImageTask(avatarView)
//                    .execute(avatar);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }


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
