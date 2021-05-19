package com.codingbjs.voicenotice.voicedata;

import android.content.ContentValues;

import com.codingbjs.voicenotice.util.ArgParamManager;

import java.util.Date;

public class VoiceItem {
    private long date;
    private String title;
    private String voice;
    private int position;

    public VoiceItem(long date, String title, String voice) {
        this.date = date;
        this.title = title;
        this.voice = voice;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateText () {
        return ArgParamManager.DATE_TIME.format(new Date(date));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ContentValues getContentValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(VoiceItemDBSchema.VoiceItemTable.Cols.DATE, date);
        contentValues.put(VoiceItemDBSchema.VoiceItemTable.Cols.TITLE, title);
        contentValues.put(VoiceItemDBSchema.VoiceItemTable.Cols.VOICE, voice);
        return contentValues;
    }
}
