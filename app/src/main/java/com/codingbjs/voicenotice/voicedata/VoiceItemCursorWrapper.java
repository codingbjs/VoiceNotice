package com.codingbjs.voicenotice.voicedata;

import android.database.Cursor;
import android.database.CursorWrapper;

import static com.codingbjs.voicenotice.voicedata.VoiceItemDBSchema.*;

public class VoiceItemCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public VoiceItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public VoiceItem getVoiceItem() {
        long date = getLong(getColumnIndex(VoiceItemTable.Cols.DATE));
        String title = getString(getColumnIndex(VoiceItemTable.Cols.TITLE));
        String voice = getString(getColumnIndex(VoiceItemTable.Cols.VOICE));
        return new VoiceItem(date, title, voice);
    }
}
