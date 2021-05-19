package com.codingbjs.voicenotice.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSManager {

    private static final float SPEECH_PITCH_LEVEL = 1.0F;
    private static final float SPEECH_RATE_LEVEL = 1.0F;


    private final Context context;

    private TextToSpeech textToSpeech;

    public TTSManager(Context context) {
        this.context = context;
        init(context, SPEECH_PITCH_LEVEL, SPEECH_RATE_LEVEL);
    }

    public TTSManager(Context context, float pitchLevel, float rateLevel) {
        this.context = context;
        init(context, pitchLevel, rateLevel);
    }


    private void init(Context context,  float pitchLevel, float rateLevel) {
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.KOREA);
                textToSpeech.setPitch(pitchLevel);
                textToSpeech.setSpeechRate(rateLevel);
            }
        });
    }

    public void setSpeechPitchLevel(float level) {
        textToSpeech.setPitch(level);
    }

    public void setSpeechRateLevel(float level) {
        textToSpeech.setSpeechRate(level);
    }

    public void speak(String ttsString) {
        textToSpeech.speak(ttsString, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void shutdown() {
        textToSpeech.shutdown();
    }

}
