package com.example.j_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    Button btnClick;
    EditText textEnter;
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        btnClick = (Button) findViewById(R.id.text_to_speech_btn);
        textEnter = (EditText) findViewById(R.id.edit_text);

        textToSpeech = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            int language = textToSpeech.setLanguage(Locale.ENGLISH);
                        }
                    }
                });
    }

    public void startTalking(View view) {
        String text = textEnter.getText().toString();
        int speech =  textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}