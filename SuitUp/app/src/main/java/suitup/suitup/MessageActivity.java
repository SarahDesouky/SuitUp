package suitup.suitup;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sendmsg = (Button) findViewById(R.id.sendmsg);
        inputmsg = (EditText) findViewById(R.id.inputmsg);
        displaymsg = (TextView) findViewById(R.id.displaymsg);
        friendName = (TextView) findViewById(R.id.textView3);
        friendName.append(" " + AllMessagesActivity.msg.getOwner());
        displaymsg.append(AllMessagesActivity.msg.getOwner() + ": " + AllMessagesActivity.msg.getText());
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
    }
}
