package com.example.laravel_echo_realtime_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.mrbin99.laravelechoandroid.Echo;
import net.mrbin99.laravelechoandroid.EchoOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    // echo setup variables
    private static final String SERVER_URL = "http://192.168.0.113:6001";
    private static final String MESSAGES_CHANNEL = "messages";
    private static final String MESSAGES_EVENT = "MessageSent";

    // echo object
    private Echo echo = null;

    // ui
    private TextView MessageTV;

    // data
    private Message lastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disconnectEcho();
    }

    private void init() {

        MessageTV = findViewById(R.id.MessageTextView);
        MessageTV.setText("No messages received");

        echo = setupEcho();

        // connect echo
        echo.connect(args -> {
            // success
            listenForRealtimeMessages();
            Log.d(TAG, "call: Echo connected successfully");
        }, args -> {
            // error
            logErrors("failed to connect echo!", args);
        });
    }

    private void listenForRealtimeMessages() {

        if(echo==null) return;

        echo.channel(MESSAGES_CHANNEL).listen(MESSAGES_EVENT, args -> {

            Log.d(TAG, "call: Message received");
            for(int i=0; i< args.length; i++){
                Log.d(TAG, "listenForRealtimeMessages: arg["+i+"] = "+args[i].toString());
            }

            runOnUiThread(() -> displayMessage(args));
        });
    }

    private void displayMessage(Object... args) {

        // Todo: parse args object through a model class
        //  instead of hardcoding here

        lastMessage = new Message();
        lastMessage.setContent("No messages received");
        //String message = "No messages received";

        try {
            JSONObject messageJson = new JSONObject(args[1].toString()).getJSONObject("message");

            lastMessage = Message.parseFromJson(messageJson);

            Log.d(TAG, "displayMessage: message received = "+lastMessage.toString());
        } catch (JSONException e) {

            Log.d(TAG, "displayMessage: error parsing json = "+e.getMessage());
        }

        if(MessageTV.getText().toString().equals("No messages received")){
            MessageTV.setText(lastMessage.getContent());
        }
        else MessageTV.setText(MessageTV.getText().toString()+"\n"+lastMessage.getContent());
    }

    private Echo setupEcho() {

        EchoOptions echoOptions = new EchoOptions();

        // setup host of your Laravel Echo Server
        echoOptions.host = SERVER_URL;

        /*
         * Add headers for authorizing your users (private and presence channels).
         * This line can change matching how you have configured
         * your guards on your Laravel application
         */
        //echoOptions.headers.put("Authorization", "Bearer {token}");

        return new Echo(echoOptions);
    }

    private void disconnectEcho(){

        if(echo!=null) echo.disconnect();
    }

    private void logErrors(String message, Object... args) {
        Log.d(TAG, "logErrors: "+message);
        for (Object arg: args) Log.d(TAG, "logErrors: "+arg.toString());
    }
}