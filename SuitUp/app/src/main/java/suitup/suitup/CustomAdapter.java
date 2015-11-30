package suitup.suitup;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, String[] kalam) {
        super(context,R.layout.custom_row, kalam);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater wallInflater =LayoutInflater.from(getContext());
        View customView= wallInflater.inflate(R.layout.custom_row, parent, false);
        String singleItem = getItem(position);
        TextView textWall = (TextView)customView.findViewById(R.id.textWall);
        ImageView imageWall = (ImageView)customView.findViewById(R.id.imageWall);
        textWall.setText(singleItem);
        imageWall.setImageResource(R.drawable.mohee);
        return customView;
    }
}
