package suitup.suitup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import models.Message;
import models.User;

public class ConversationsAdapter extends ArrayAdapter {

    ArrayList names;
    ArrayList<Message> messages;
    ArrayList read;



    ConversationsAdapter(Context context,ArrayList n, ArrayList r, ArrayList<Message>messages) {
        super(context, R.layout.custom_row_posts, n);
        this.names = n;
        this.messages = messages;
        this.read = r;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row_posts, parent, false);
        TextView nameView = (TextView)CustomView.findViewById(R.id.poster);
        TextView messageView = (TextView)CustomView.findViewById(R.id.text);

        String name = names.get(position).toString();
        nameView.setText(name);
        String messaget = messages.get(position).getText().toString();
        messageView.setText(messaget);

        if(read.get(position)=="false")
            messageView.setTextColor(Color.GREEN);
        else
            messageView.setTextColor(Color.BLACK);

//        Toast.makeText(this.getContext()
//                , "Reached here", Toast.LENGTH_LONG).show();


        return CustomView;
    }

}
