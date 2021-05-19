package com.codingbjs.voicenotice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.codingbjs.voicenotice.databinding.ActivityMainBinding;
import com.codingbjs.voicenotice.util.TTSManager;
import com.codingbjs.voicenotice.voicedata.VoiceSettings;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    VoiceSettings voiceSettings;
    TTSManager ttsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        voiceSettings = VoiceSettings.getInstance(getApplicationContext());
        ttsManager = new TTSManager(getApplicationContext(),
                                voiceSettings.getSpeechPitch(), voiceSettings.getSpeechRate());

        mainBinding.speechRateSeekBar.setProgress(getSeekBarValue(voiceSettings.getSpeechRate()));
        mainBinding.speechPitchSeekBar.setProgress(getSeekBarValue(voiceSettings.getSpeechPitch()));

        mainBinding.speechPitchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                voiceSettings.setSpeechPitch(progressConverter(progress));
                ttsManager.setSpeechPitchLevel(voiceSettings.getSpeechPitch());
                Log.e("speechPitchSeekBar", voiceSettings.getSpeechPitch() + "F");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mainBinding.speechRateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                voiceSettings.setSpeechRate(progressConverter(progress));
                ttsManager.setSpeechRateLevel(voiceSettings.getSpeechRate());
                Log.e("speechPitchSeekBar", voiceSettings.getSpeechRate() + "F");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

    private int getSeekBarValue (float floatValue){
        return (int) (voiceSettings.getSpeechRate() * 10 / 2) - 1;
    }

    private float progressConverter (int progressValue){
        return (float) ((progressValue + 1) * 2 / 10F);
    }

}