package suitup.suitup;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

import java.util.ArrayList;

import models.*;

public class UserListAdapter extends ArrayAdapter {
    private static ArrayList<String> names;
    private static ArrayList<Uri> images;
    private String [] comments = new String [100];
    private static int numberOfComments = 0;
    private ourAPI ourAPI;
//    private List<User>users;

    UserListAdapter(Context context, ArrayList<models.User> users) {
        super(context, R.layout.custom_row_posts, users);
        names = new ArrayList<>();
        images = new ArrayList<>();
        Log.d(users.size()+"",users.size()+"");
        for (int i = 0; i < users.size(); i++) {

            names.add(users.get(i).getFname() + " "+ users.get(i).getLname());
            images.add(Uri.parse(users.get(i).getAvatar_url()));
        }
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View CustomView = inflater.inflate(R.layout.custom_row_posts, parent, false);

//        TextView poster = (TextView)CustomView.findViewById(R.id.poster);
//        poster.setText(StaticData.CurrentUser.fname + " " + StaticData.CurrentUser.lname + "\n");

        String post = "";
        Uri image = null;

        try {
            post = names.get(position);
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

        return CustomView;
    }


}
