package suitup.suitup;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Timeline extends ListActivity {
    private CustomListAdapter adapter2;

    private ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, content);
        setListAdapter(adapter);
        ArrayList<Integer>icons = new ArrayList<>();
        content.add("aiwjewe is now friends with oiwrhiwer");
        content.add("rworpw commented on werjwer's photo");
        content.add("owaeuqe liked ejrwoer's post");
        content.add("eowprkw posted on iwoerwe's wall");
        content.add("aiwjewe is now friends with oiwrhiwer");
        content.add("rworpw commented on werjwer's photo");
        content.add("owaeuqe liked ejrwoer's post");
        content.add("eowprkw posted on iwoerwe's wall");
        for(int i =0;i<content.size();i++)
            icons.add(R.drawable.donna);
        adapter2 = new CustomListAdapter(Timeline.this, content, icons);
        setListAdapter(adapter2);
    }

}
