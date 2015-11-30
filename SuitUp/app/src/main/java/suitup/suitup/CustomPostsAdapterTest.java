package suitup.suitup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by sarah on 11/30/2015.
 */
public class CustomPostsAdapterTest extends ArrayAdapter {
    ArrayList<String> posts;
    ArrayList<Uri> images;
    String [] comments = new String [100];
    static int numberOfComments = 0;

    CustomPostsAdapterTest(Context context, ArrayList<String> p, ArrayList<Uri> i) {
        super(context,R.layout.custom_row_posts, p );
        posts = p;
        images = i;
        for(int j=0;j <100;j++) {
            comments[j] ="";
        }
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row_posts, parent, false);

        String post = "";
        Uri image = null;
        try {
            post = posts.get(position);
            TextView text = (TextView)CustomView.findViewById(R.id.text);
            text.setText(post);
        }catch(Exception e){
            String s = e.toString();
        };

        try {
            image = images.get(position);
            ImageView imagep = (ImageView) CustomView.findViewById(R.id.image);

            if(!image.equals(Uri.EMPTY)) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), image);
                    imagep.setImageBitmap(bitmap);
                    imagep.setMaxHeight(100);
                    imagep.setMaxWidth(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            String s = e.toString();
        };

        Button commentButton = (Button)CustomView.findViewById(R.id.PostComment);
        ListView lst = (ListView)CustomView.findViewById(R.id.comments);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.copyOfRange(comments,0,numberOfComments));
        lst.setAdapter(adapter);
        EditText commentEditText = (EditText)CustomView.findViewById(R.id.comment);
        try {
            commentButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                String comment = (String)((EditText)CustomView.findViewById(R.id.comment)).getText().toString();
                comments[numberOfComments] = comment;
                numberOfComments++;
                adapter.notifyDataSetChanged();
                }
            });
        }catch(Exception e) {
            String s = e.toString();
        }

        return CustomView;
    }


}
