package suitup.suitup;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;

public class AllMessagesActivity extends Activity {
    public static Message[] items;
    public static String msgTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_messages);

        ListView listView = (ListView) findViewById(R.id.listView);

        items = new Message[]{
                new Message("Message 1", "Dina", "Alaa"),
                new Message("Message 2", "Aya", "Alaa"),
                new Message("Message 3", "Sondos", "Alaa"),
                new Message("Message 4", "Doha", "Alaa"),
                new Message("Message 5", "Alia", "Alaa")};

        ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = ((TextView)view).getText().toString();
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
//                String e = "" + id;
//                Toast.makeText(getBaseContext(), e, Toast.LENGTH_LONG).show();
                Message m = items[(int)id];
                msgTxt = m.getOwner() + ": " + m.getText() + "\n";
                Intent intent = new Intent(AllMessagesActivity.this, MessageActivity.class);
                AllMessagesActivity.this.startActivity(intent);
            }
        });

    }


}
