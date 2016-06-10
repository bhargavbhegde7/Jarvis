package com.sound.bytes.voicecommand;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private boolean AUTO_SEND = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button speakButton;
        speakButton = (Button) findViewById(R.id.micButton);
        speakButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        CheckBox autoSend = (CheckBox) findViewById(R.id.autoSend);
        autoSend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) AUTO_SEND = true;
                    else AUTO_SEND = false;
                }
            }
        );

        Button sendButton;
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText resultText;
                resultText = (EditText) findViewById(R.id.resultString);
                String command = resultText.getText().toString();
                sendCommand(command);
            }
        });

        Button connectButton;
        connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText serverIpField = (EditText) findViewById(R.id.serverIp);
                String serverIp = serverIpField.getText().toString();

                EditText serverPortField = (EditText) findViewById(R.id.serverPort);
                int serverPort = Integer.parseInt(serverPortField.getText().toString());

                ClientThread.setServerIp(serverIp);
                ClientThread.setSERVERPORT(serverPort);

                new Thread(new ClientThread()).start();
                Toast.makeText(getApplicationContext(),"Looks like Connected",Toast.LENGTH_SHORT);
            }
        });

        Button disconnectButton;
        sendButton = (Button) findViewById(R.id.disconnectButton);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //end connection
                ClientThread.endConnection();
            }
        });
    }

    public void sendCommand(String command)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(ClientThread.getSocket().getOutputStream())),
                    true);
            out.println(command);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* for speech input */
    /* Showing google speech input dialog */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "speak the words");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /* Receiving speech input */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    EditText resultText = (EditText)findViewById(R.id.resultString);
                    String command = result.get(0);
                    resultText.setText(command);
                    if(AUTO_SEND) sendCommand(command);
                }
                break;
            }
        }
    }
    /* for speech input */
}
