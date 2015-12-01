package suitup.suitup;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MembersListActivity extends ListActivity {

    CustomListAdapter adapter;
    ArrayList<String> content = new ArrayList<>();
    ArrayList<Integer>icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        for(int i=0; i<StaticData.members.length; i++){
            content.add(StaticData.members[i].getUsername());
        }
        for(int i =0;i<content.size();i++)
            icons.add(R.drawable.mohee);
        adapter = new CustomListAdapter(MembersListActivity.this, content, icons);
        setListAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getApplicationContext(), MemberProfile.class);
        intent.putExtra("friendName", content.get(position));
        startActivity(intent);
    }

}
