package suitup.suitup;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PostActivity extends ListActivity {
    public static final String PREFS_NAME = "MyPrefs";
    public static SharedPreferences.Editor editor;
    static TextView post;
    static Button postButton;
    static EditText editText;
    static ArrayList<String> comments;
    static ArrayList<Integer> icons;
    private ArrayAdapter adapter2;

    public PostActivity(){
        comments = new ArrayList<>();
        icons = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        post = (TextView) findViewById(R.id.MainPost);
        post.setText(settings.getString("post", "ana ga3ana"));
        editText = (EditText) findViewById(R.id.editText);
        postButton = (Button) findViewById(R.id.postButton);

//        for(int i =0;i<comments.size();i++)
//            icons.add(R.drawable.donna);
        adapter2 = new ArrayAdapter<String>(PostActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, comments);
//        adapter2 = new CustomListAdapter(PostActivity.this, comments, icons);
        setListAdapter(adapter2);

    }
    public  void postButtonClicked(View v){
        comments.add(editText.getText().toString());
        adapter2.notifyDataSetChanged();

    }

}
