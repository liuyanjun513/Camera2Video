/**
 * This code makes extensive use of Speech to Text tutorials from the following links:
 *  http://blog.csdn.net/wangkuifeng0118/article/details/7251813 by wangkuifeng0118
 *  http://www.androidhive.info/2014/07/android-speech-to-text-tutorial/
 *
 *  The latter tutorial is provided in English, while the first is written in Chinese,
 *  which was used and modified for English comprehension below.
 *
 */



package com.example.android.camera2video;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecognizerIntentActivity extends Activity {

    private Button btnReconizer;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private EditText instruction;
    private TextView sentence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer_intent);

        btnReconizer=(Button) this.findViewById(R.id.btnRecognizer);
        btnReconizer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sentence =(TextView)findViewById(R.id.sentence);
                sentence.setText("");

                try{
                    //use Intent to activate speech window
                    Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    //free form and web search model
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //indicate it is working
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Recoginizing...");
                    //start activity
                    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Can't find any voice equipments", 1).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        instruction =(EditText)findViewById(R.id.instruction);
        sentence =(TextView)findViewById(R.id.sentence);
        sentence.setText("");
        String sInstruction=instruction.getText().toString();
        if(sInstruction.equals("")){
            sInstruction="hello bear";
        }
        boolean flag=false;
        //get data from google voice
        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK){
            //obtain phrases and sentences you just said
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String resultString="";
            for(int i=0;i<results.size();i++){
                resultString+=results.get(i)+"//";
                if(results.get(i).contains(sInstruction)){ //equals(sInstruction)
                    flag=true;
                }
            }
            //Toast.makeText(this, resultString , 1).show();
            sentence.setText(resultString);
            if(flag==true){
                Intent intent=new Intent(this,CameraActivity.class);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
