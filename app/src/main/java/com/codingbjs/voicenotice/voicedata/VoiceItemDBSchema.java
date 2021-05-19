package com.codingbjs.voicenotice.voicedata;

import android.provider.BaseColumns;

public class VoiceItemDBSchema {

    public static final class VoiceItemTable {
        public static final String NAME = "voice_item_table";

        public  static final class Cols implements BaseColumns {
            public static final String DATE = "date";
            public static final String TITLE = "title";
            public static final String VOICE = "voice";
        }

        public static final String CREATE_VOICE_TABLE = "CREATE TABLE " + NAME + " ("
                + Cols.DATE + " INTEGER NOT NULL, "
                + Cols.TITLE + " TEXT, "
                + Cols.VOICE + " TEXT)";

        public static final String DELETE_LOG_TABLE = "DROP TABLE IF EXISTS " + NAME;
    }


}
