package suitup.suitup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MessageActivity extends AppCompatActivity {

    static String messages = "";
    Button sendmsg;
    EditText inputmsg;
    TextView displaymsg;
    TextView friendName;

    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";
    String frname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        frname = settings.getString("friendname", null);

        sendmsg = (Button) findViewById(R.id.sendmsg);
        inputmsg = (EditText) findViewById(R.id.inputmsg);
        displaymsg = (TextView) findViewById(R.id.displaymsg);
        friendName = (TextView) findViewById(R.id.textView3);

        if(frname == null) {
            friendName.append(" " + AllMessagesActivity.msg.getOwner());
            displaymsg.append(AllMessagesActivity.msg.getOwner() + ": " + AllMessagesActivity.msg.getText() + "\n");
        }
        else {
            friendName.append(" " + frname);
            editor.putString("friendname", null).commit();

        }
        sendmsg.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                sendMessage();
            }

        });
    }

    public void sendMessage() {
        String message = inputmsg.getText().toString();
        messages = "You: " + message + "\n";
        displaymsg.append(messages);
        //inputmsg.getText().clear();
        inputmsg.setText("");
//        AllMessagesActivity.msgs.add(new Message(StaticData.CurrentUser.fname, frname, displaymsg.getText().toString()));
    }
}
