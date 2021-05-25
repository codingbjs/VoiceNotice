package com.codingbjs.voicenotice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.codingbjs.voicenotice.activity.CarMoveActivity;
import com.codingbjs.voicenotice.databinding.ActivityMainBinding;
import com.codingbjs.voicenotice.util.ArgParamManager;
import com.codingbjs.voicenotice.util.TTSManager;
import com.codingbjs.voicenotice.voicedata.VoiceItem;
import com.codingbjs.voicenotice.voicedata.VoiceItemActivity;
import com.codingbjs.voicenotice.voicedata.VoiceItemList;
import com.codingbjs.voicenotice.voicedata.VoiceItemListActivity;
import com.codingbjs.voicenotice.voicedata.VoiceSettings;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    private VoiceItemList voiceItemList;
    private VoiceSettings voiceSettings;
    private TTSManager ttsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        voiceItemList = VoiceItemList.getInstance(getApplicationContext());
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

        mainBinding.voiceStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsManager.speakStop();
            }
        });

        mainBinding.saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        VoiceItemActivity.class,
                        ArgParamManager.NEW_VOICE_ITEM,
                        mainBinding.voiceEditText.getText().toString()));
            }
        });

        mainBinding.loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        VoiceItemListActivity.class,
                        ArgParamManager.NEW_VOICE_ITEM,
                        mainBinding.voiceEditText.getText().toString()));
            }
        });

        mainBinding.carMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ArgParamManager.getAllFlagsIntent(getApplicationContext(),
                        CarMoveActivity.class,null, null));
            }
        });

        if(voiceItemList.getVoiceItemList().size() == 0) {
            addVoiceItem();
        }

    }

    private void addVoiceItem() {
        ArrayList<VoiceItem> voiceItems = new ArrayList<>();
        long date = System.currentTimeMillis();
        voiceItems.add(new VoiceItem(date + 1000, getString(R.string.parking_info_title), getString(R.string.parking_info_voice)));
        voiceItems.add(new VoiceItem(date + 2000, getString(R.string.issue_info_title), getString(R.string.issue_info_voice)));

        for (VoiceItem voiceItem : voiceItems){
            voiceItemList.addVoiceItem(voiceItem);
        }
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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null){
            String param1 = intent.getStringExtra(ArgParamManager.ARG_PARAM1);
            String param2 = intent.getStringExtra(ArgParamManager.ARG_PARAM2);
            if (param1 != null) resultParamCheck(param1, param2);
        }
    }

    private void resultParamCheck(String param1, String param2) {

        switch (param1) {
            case ArgParamManager.GET_VOICE_ITEM:
                if(param2 != null) {
                    mainBinding.voiceEditText.setText(param2);
                }
                break;
            default:
                break;
        }
    }
}