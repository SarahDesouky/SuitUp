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

    private String[] content = new String[] {
            "aiwjewe is now friends with oiwrhiwer",
            "rworpw commented on werjwer's photo",
            "owaeuqe liked ejrwoer's post",
            "eowprkw posted on iwoerwe's wall",
            "aiwjewe is now friends with oiwrhiwer",
            "rworpw commented on werjwer's photo",
            "owaeuqe liked ejrwoer's post",
            "eowprkw posted on iwoerwe's wall"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, content);
        setListAdapter(adapter);
    }

}
