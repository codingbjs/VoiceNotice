package com.codingbjs.voicenotice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.codingbjs.voicenotice.databinding.ActivityMainBinding;
import com.codingbjs.voicenotice.util.TTSManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    TTSManager ttsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        ttsManager = new TTSManager(getApplicationContext());

        mainBinding.voicePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsManager.speak(mainBinding.voiceEditText.getText().toString());
            }
        });
    }


    @Override
    protected void onDestroy() {
        ttsManager.shutdown();
        super.onDestroy();
    }
}