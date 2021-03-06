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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        for(int i=0; i<StaticData.friends.length; i++){
            content.add(StaticData.friends[i].getUsername());
        }
        for(int i =0;i<content.size();i++)
            icons.add(R.drawable.profile);
        adapter = new CustomListAdapter(FriendsListActivity.this, content, icons);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getApplicationContext(), FriendProfile.class);
        intent.putExtra("friendName", content.get(position));
        startActivity(intent);
    }

}
