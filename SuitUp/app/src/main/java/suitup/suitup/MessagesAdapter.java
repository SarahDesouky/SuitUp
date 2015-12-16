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
import models.Message;
import models.User;

public class MessagesAdapter extends ArrayAdapter {

    ArrayList senders;
    List<models.Message> messages;


    MessagesAdapter(Context context, ArrayList s, List<models.Message> m) {
        super(context, R.layout.custom_row_posts, s);
        this.senders = s;
        this.messages = m;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row_posts, parent, false);
        TextView nameView = (TextView) CustomView.findViewById(R.id.poster);
        TextView messaget = (TextView) CustomView.findViewById(R.id.text);

        String sender = senders.get(position).toString();
        Message msg = messages.get(position);
        nameView.setText(sender);
        // String avatar = author.getAvatar_url();
        String mText = msg.getText();
        messaget.setText(mText);


        return CustomView;
    }

}

