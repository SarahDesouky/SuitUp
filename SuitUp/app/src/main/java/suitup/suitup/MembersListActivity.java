package suitup.suitup;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MembersListActivity extends ListActivity {

    UserListAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
//        for(int i=0; i<StaticData.members.length; i++){
//            content.add(StaticData.members[i].getUsername());
//        }
//        for(int i =0;i<content.size();i++)
//            icons.add(R.drawable.mohee);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        ourAPI api = adapter.create(ourAPI.class);

        api.getAllUsers(new Callback<List<models.User>>() {
            @Override
            public void success(List<User> users, Response response) {
                Log.d("suzzess", "suzzess");
                ArrayList<User> u = new ArrayList<>();
                for(int i =0;i<users.size();i++){
                    u.add(users.get(i));
                    Log.d(users.get(i).getFname(), users.get(i).getFname());
                }
                adapter1 = new UserListAdapter(MembersListActivity.this, u);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("nooooo","nooo");
            }
        });
        setListAdapter(adapter1);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getApplicationContext(), MemberProfile.class);
//        intent.putExtra("friendName", content.get(position));
        startActivity(intent);
    }

}
