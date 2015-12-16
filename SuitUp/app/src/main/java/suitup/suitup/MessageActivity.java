package suitup.suitup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.Message;
import models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MessageActivity extends AppCompatActivity {

    static String messages = "";
    Button sendmsg;
    EditText inputmsg;
    TextView displaymsg;
    TextView friendName;
    String threadId = "";
    String receiverId = "";
    ArrayList receiversnames = new ArrayList<>();
    ArrayList sendersnames = new ArrayList();

    public static SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefs";
    String frname;
    ArrayAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        threadId = settings.getString("thread_id", "");
        RestAdapter adapterre = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapterre.create(ourAPI.class);
        api.getAllMessagesInThread(threadId, new Callback<List<models.Message>>() {
            @Override
            public void success(final List<Message> messages, Response response) {

                for(int i =0;i<messages.size();i++) {

                    String receiverId = String.valueOf(messages.get(i).getUser_id());
                    final String ownerId = String.valueOf(messages.get(i).getOwner_id());
                    api.getFriend(receiverId, new Callback<User>() {
                        @Override
                        public void success(User user, Response response) {

                            String receiverName = user.getFname() + " " + user.getLname();
                            receiversnames.add(receiverName);
                            api.getFriend(ownerId, new Callback<User>() {
                                @Override
                                public void success(User user, Response response) {
                                    sendersnames.add(user.getFname() + " " + user.getLname());
                                    if (sendersnames.size() == messages.size()) {
                                        messagesAdapter = new MessagesAdapter(getApplicationContext(), sendersnames, messages);
                                        ListView listView = (ListView) findViewById(R.id.listView);
                                        listView.setAdapter(messagesAdapter);
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });

                }






            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
//        frname = settings.getString("friendname", null);
//
//        sendmsg = (Button) findViewById(R.id.sendmsg);
//        inputmsg = (EditText) findViewById(R.id.inputmsg);
//        displaymsg = (TextView) findViewById(R.id.displaymsg);
//        friendName = (TextView) findViewById(R.id.textView3);
//
//        if(frname == null) {
//            friendName.append(" " + AllMessagesActivity.msg.getOwner());
//            displaymsg.append(AllMessagesActivity.msg.getOwner() + ": " + AllMessagesActivity.msg.getText() + "\n");
//        }
//        else {
//            friendName.append(" " + frname);
//            editor.putString("friendname", null).commit();
//
//        }
//        sendmsg.setOnClickListener(new View.OnClickListener() {
//            public void onClick (View v) {
//                sendMessage();
//            }
//
//        });
    }

    public void sendMessage(View view) {
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        inputmsg = (EditText) findViewById(R.id.inputmsg);
        inputmsg = (EditText) findViewById(R.id.inputmsg);
        final String message = inputmsg.getText().toString();
        final String myId = settings.getString("my_id", "");
        threadId = settings.getString("thread_id","");
        RestAdapter adapterre = new RestAdapter.Builder().setEndpoint(getResources().getString(R.string.API_BASE_URL)).build();
        final ourAPI api = adapterre.create(ourAPI.class);
        api.getThread(threadId, new Callback<MessageThread>() {
            @Override
            public void success(MessageThread messageThread, Response response) {

                if(String.valueOf(messageThread.getReceiver_id())==myId){
                    receiverId = String.valueOf(messageThread.getUser_id());
                }
                else {
                    receiverId = String.valueOf(messageThread.getReceiver_id());
                }
                api.AddMessage(myId, receiverId, message, threadId, new Callback<Message>() {
                    @Override
                    public void success(Message message, Response response) {
                        api.getAllMessagesInThread(threadId, new Callback<List<models.Message>>() {
                            @Override
                            public void success(final List<Message> messages, Response response) {

                                for(int i =0;i<messages.size();i++) {

                                    String receiverId = String.valueOf(messages.get(i).getUser_id());
                                    final String ownerId = String.valueOf(messages.get(i).getOwner_id());
                                    api.getFriend(receiverId, new Callback<User>() {
                                        @Override
                                        public void success(User user, Response response) {

                                            String receiverName = user.getFname() + " " + user.getLname();
                                            receiversnames.add(receiverName);
                                            api.getFriend(ownerId, new Callback<User>() {
                                                @Override
                                                public void success(User user, Response response) {
                                                    sendersnames.add(user.getFname() + " " + user.getLname());
                                                    if (sendersnames.size() == messages.size()) {
                                                        messagesAdapter = new MessagesAdapter(getApplicationContext(), sendersnames, messages);
                                                        ListView listView = (ListView) findViewById(R.id.listView);
                                                        listView.setAdapter(messagesAdapter);
                                                    }
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                        }
                                    });

                                }






                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }


            @Override
            public void failure(RetrofitError error) {

            }
        });


//        messages = "You: " + message + "\n";
//        displaymsg.append(messages);
        //inputmsg.getText().clear();
//        inputmsg.setText("");
//        AllMessagesActivity.msgs.add(new Message(StaticData.CurrentUser.fname, frname, displaymsg.getText().toString()));
    }
}
