package suitup.suitup;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendsListActivity extends ListActivity {

    CustomListAdapter adapter;
    ArrayList<String> content = new ArrayList<>();
    ArrayList<Integer>icons = new ArrayList<>();
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        content.add(StaticData.f1.getUsername());
        content.add(StaticData.f2.getUsername());
        content.add(StaticData.f3.getUsername());
        content.add(StaticData.f4.getUsername());
        for(int i =0;i<content.size();i++)
            icons.add(R.drawable.profile);
        adapter = new CustomListAdapter(FriendsListActivity.this, content, icons);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("friendName", content.get(position));
        startActivity(intent);
    }

}
