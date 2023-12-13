package com.example.j_ai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SpeechToTextActivity extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView textedSpeech;
    private TextToSpeech tts;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        Dexter.withContext(this)
                .withPermission(android.Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        System.exit(0);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        initTextToSpeech();
        result();
        findById();
        textToSpeech();

    }

    private void textToSpeech() {
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

    private void initTextToSpeech() {
        tts = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (tts.getEngines().size() == 0) {
                            Toast.makeText(getApplicationContext(), "TTS engine not found", Toast.LENGTH_SHORT).show();
                        } else {
                            String greetings = wishMeAGoodDay();
                            speak(greetings+"I'm your J AI Assistant. How can I help you?");
                        }
                    }
                });
    }

    private String wishMeAGoodDay() {
        String greetings = "";
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if (time >= 6 && time <12 ){
            greetings = "Good morning";
        }
        else if (time >= 12 && time <16){
            greetings = "good afternoon";
        }
        else if (time >= 16 && time<22){
            greetings = "good evening";
        }
        else {
            greetings = "good night blyad";
        }
        return greetings;
    }

    private void speak(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void findById() {
        textedSpeech = (TextView) findViewById(R.id.texted_speech);
    }


    private void result() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    assert result != null;
                    Toast.makeText(getApplicationContext(), result.get(0), Toast.LENGTH_SHORT).show();
                    textedSpeech.setText(result.get(0));
                    response(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void response(String msg) {
        String msgs = msg.toLowerCase(Locale.ROOT);
        Date date = new Date();
        LocalDate localDate;
        DateClass dateClass = new DateClass();
        dateClass.setDate();
        if (msgs.contains("hi") || msgs.contains("hello")) {
            speak("Hello I'm your J AI Assistant. How can I help you?");
        }
        if (msgs.contains("time")) {
            String time = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
            speak(time);
        }
        if (msgs.contains("date")) {
            SimpleDateFormat outGoingDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            speak("to day is "+dateClass.setDate(outGoingDateFormat));
        }
        if (msgs.contains("day")) {
            speak(dateClass.day + "of" + dateClass.month);
        }
        if (msgs.contains("month")) {
            speak(dateClass.month);
        }
        if (msgs.contains("year")) {

            speak(dateClass.year);
        }
        if (msgs.contains("thank")) {
            speak("I'm pleased to help");
        }
        if (msgs.contains("google")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        }
        if (msgs.contains("youtube")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
            startActivity(intent);
        }
        if (msgs.contains("search")|| msgs.contains("lock up")|| msgs.contains("lock for")){
            String searchMsg;
            if (msgs.contains("for")&& msgs.indexOf("h") == 5){
                searchMsg = msgs.replace("search","").replace("for","");
            }else searchMsg = msgs.replace("search","");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+searchMsg));
            startActivity(intent);
        }
        if (msgs.contains("remember")){
            speak("ok i'll Remember that");
            writeToFile(msgs.replace("j remember that",""));
        }
        if (msgs.contains("know")){
            try {
                String data =   readFromFile();
                speak("yes you sad that"+ data);
            }catch (Exception e){
                Log.e("Exception", "File not found: " + e.toString());
            }

        }

    }

    private String readFromFile() throws FileNotFoundException {
        String ret = "";
        try {
            InputStream inputStream = openFileInput("data.txt");
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }catch (Exception e){
            Log.e("Exception", "File not found: " + e.toString());
        }
        return ret;
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    public String getDate() {
        SimpleDateFormat outGoingDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        Calendar calendar = Calendar.getInstance();
        return outGoingDateFormat.format(calendar.getTime());
    }

    public void startRecording(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizer.startListening(intent);
    }

    public void startTalking(View view) {
        String text = textedSpeech.getText().toString();
        int speech = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }
}