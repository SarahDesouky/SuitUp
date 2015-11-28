package suitup.suitup;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;

import java.util.ArrayList;

public class AllMessagesActivity extends Activity {
    public static ArrayList<Message> msgs = new ArrayList<>();
    public static Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);

        ListView listView = (ListView) findViewById(R.id.listView);

        msgs.clear();
        msgs.add(new Message("Message 1", "Dina", "Alaa"));
        msgs.add(new Message("Message 2", "Aya", "Alaa"));
        msgs.add(new Message("Message 3", "Sondos", "Alaa"));
        msgs.add(new Message("Message 4", "Doha", "Alaa"));
        msgs.add(new Message("Message 5", "Lina", "Alaa"));

        ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, msgs);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = ((TextView)view).getText().toString();
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
//                String e = "" + id;
//                Toast.makeText(getBaseContext(), e, Toast.LENGTH_LONG).show();
                msg = msgs.get((int)id);
                Intent intent = new Intent(AllMessagesActivity.this, MessageActivity.class);
                AllMessagesActivity.this.startActivity(intent);
            }
        });

    }


}
