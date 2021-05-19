package com.codingbjs.voicenotice.voicedata;

import android.content.Context;

import com.codingbjs.voicenotice.util.PreferenceManager;

public class VoiceSettings {
    public static final String SPEECH_PITCH = "speechPitch";
    public static final String SPEECH_RATE = "speechRate";

    private static VoiceSettings voiceSettings;

    private float speechPitch = 1.0F;
    private float speechRate = 1.0F;

    private final Context context;

    public static VoiceSettings getInstance(Context context) {
        if(voiceSettings == null) {
            voiceSettings = new VoiceSettings(context);
        }
        return voiceSettings;
    }

    public VoiceSettings(Context context) {
        this.context = context.getApplicationContext();
        if(PreferenceManager.getFloat(context, SPEECH_PITCH) > 0) {
            speechPitch = PreferenceManager.getFloat(context, SPEECH_PITCH);
        }
        if(PreferenceManager.getFloat(context, SPEECH_RATE) > 0) {
            speechRate = PreferenceManager.getFloat(context, SPEECH_RATE);
        }
    }

    private void setPreferenceFloat(String key, float value){
        PreferenceManager.setFloat(context, key, value);
    }

    public float getSpeechPitch() {
        return speechPitch;
    }

    public void setSpeechPitch(float speechPitch) {
        this.speechPitch = speechPitch;
        setPreferenceFloat(SPEECH_PITCH, speechPitch);
    }

    public float getSpeechRate() {
        return speechRate;
    }

    public void setSpeechRate(float speechRate) {
        this.speechRate = speechRate;
        setPreferenceFloat(SPEECH_RATE, speechRate);
    }
}
