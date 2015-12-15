package suitup.suitup;

/**
 * Created by daliamaarek on 11/28/15.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CustomListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> itemName;
    private ArrayList<Integer> imgId;
    private ArrayList<String> tempItemname;
    private ArrayList<Integer> tempImgid;
    Button addRoom;

    public ArrayList<String> getItemName() {
        return itemName;
    }

    public void setItemName(ArrayList<String> itemName) {
        this.itemName = itemName;
    }

    public ArrayList<Integer> getImgId() {
        return imgId;
    }

    public void setImgId(ArrayList<Integer> imgId) {
        this.imgId = imgId;
    }

    public CustomListAdapter(Activity context, ArrayList<String> itemName, ArrayList<Integer> imgId) {
        super(context, R.layout.mylist, itemName);
        this.context = context;
        this.itemName = itemName;
        this.imgId = imgId;
        tempImgid = new ArrayList<Integer>();
        tempItemname = new ArrayList<String>();
        tempImgid.addAll(imgId);
        tempItemname.addAll(itemName);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView content = (TextView) rowView.findViewById(R.id.text);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);

        content.setText(itemName.get(position));
        image.setImageResource(R.drawable.donna);
        return rowView;
    }
}
