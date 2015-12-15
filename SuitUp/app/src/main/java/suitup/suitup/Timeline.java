package suitup.suitup;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Timeline extends ListActivity {
    private CustomListAdapter adapter2;
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;
    private ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        setContentView(R.layout.activity_timeline);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, content);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Object o = this.getListAdapter().getItem(position);
        String post = o.toString();
        Log.d(post, post);
        editor.putString("post", post);
        editor.commit();
        PostActivity pa = new PostActivity();
        Intent intent = new Intent(getApplicationContext(), pa.getClass());
        startActivity(intent);
    }
    public  void myProfileClicked(View v){
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        startActivity(intent);

    }
    public  void AllMembers(View v){
        Intent intent = new Intent(getApplicationContext(), MembersListActivity.class);
        startActivity(intent);
    }
}
