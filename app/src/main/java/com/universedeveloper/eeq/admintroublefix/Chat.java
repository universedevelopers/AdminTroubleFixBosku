package com.universedeveloper.eeq.admintroublefix;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.universedeveloper.eeq.admintroublefix.AdapterRecyclerView.AdapterChat;

import java.util.Random;

public class Chat extends AppCompatActivity {
    ConnectivityManager conMgr;

    private DatabaseReference root;
    private FirebaseListAdapter<AdapterChat> adapter;
    ListView listOfMessages;
    TextView txt_user;
    FloatingActionButton fab;

    EditText input ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        root = FirebaseDatabase.getInstance().getReference();

        FirebaseApp.initializeApp(Chat.this);

        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        root = FirebaseDatabase.getInstance().getReference();

        input = (EditText)findViewById(R.id.input);
        // Load chat room contents
        displayChatMessages();

        //jika di kirim pesannya
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                if (input.length() < 1) {
                    Toast.makeText(Chat.this, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new AdapterChat(input.getText().toString(),
                                    "Admin")
                            );

                    // Clear the input
                    input.setText("");
                }
            }
        });

    }
    private void displayChatMessages() {

        adapter = new FirebaseListAdapter<AdapterChat>(Chat.this, AdapterChat.class, R.layout.list_row_chat, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, AdapterChat model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.txt_pesan);
                TextView messageUser = (TextView)v.findViewById(R.id.txt_username);
                TextView messageTime = (TextView)v.findViewById(R.id.txt_waktu);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));


                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                messageUser.setTextColor(color);
            }
        };

        listOfMessages.setAdapter(adapter);
    }
}
