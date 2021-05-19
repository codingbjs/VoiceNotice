package com.codingbjs.voicenotice.voicedata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.codingbjs.voicenotice.voicedata.VoiceItemDBSchema.*;

public class VoiceDataBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "voice_data.db";


    public VoiceDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VoiceItemTable.CREATE_VOICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                db.execSQL(VoiceItemTable.DELETE_LOG_TABLE);
                onCreate(db);
                break;
        }
    }
}
