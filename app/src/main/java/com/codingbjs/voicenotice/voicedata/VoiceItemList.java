package com.codingbjs.voicenotice.voicedata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codingbjs.voicenotice.voicedata.VoiceItemDBSchema.VoiceItemTable;

import java.util.ArrayList;

public class VoiceItemList {

    private static VoiceItemList voiceItemList;

    private final Context context;

    private final SQLiteDatabase sqLiteDatabase;

    public VoiceItemList(Context context) {
        this.context = context.getApplicationContext();
        sqLiteDatabase = new VoiceDataBaseHelper(context).getWritableDatabase();
    }

    public static VoiceItemList getInstance(Context context) {
        if(voiceItemList == null) {
            voiceItemList = new VoiceItemList(context);
        }
        return voiceItemList;
    }

    private VoiceItemCursorWrapper queryVoiceList (String whereClause, String[] whereArgs) {
        String sortOrder = VoiceItemTable.Cols.DATE + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                VoiceItemTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                sortOrder
        );

        return new VoiceItemCursorWrapper(cursor);
    }

    public ArrayList<VoiceItem> getVoiceItemList() {
        ArrayList<VoiceItem> voiceItems = new ArrayList<>();
        try (VoiceItemCursorWrapper cursorWrapper = queryVoiceList(null, null)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                VoiceItem voiceItem = cursorWrapper.getVoiceItem();
                voiceItems.add(voiceItem);
                cursorWrapper.moveToNext();
            }
        }
        return voiceItems;
    }

    public VoiceItem getVoiceItem (long date) {
        String whereClause = VoiceItemTable.Cols.DATE + " = ?";

        String[] whereArgs = new String[]{String.valueOf(date)};

        try (VoiceItemCursorWrapper cursorWrapper = queryVoiceList(whereClause, whereArgs)) {
            if (cursorWrapper.getCount() == 0) return null;
            cursorWrapper.moveToFirst();
            return cursorWrapper.getVoiceItem();
        }
    }

    public synchronized void addVoiceItem (VoiceItem voiceItem){
        sqLiteDatabase.insert(VoiceItemTable.NAME, null, voiceItem.getContentValue());
    }

    public synchronized void updateVoiceItem (VoiceItem voiceItem) {
        int check = sqLiteDatabase.update(VoiceItemTable.NAME, voiceItem.getContentValue(),
                VoiceItemTable.Cols.DATE + " = ?", new String[]{String.valueOf(voiceItem.getDate())});
        Log.e("updateVoiceItem", check + "");
    }

    public synchronized void deleteVoiceItem (VoiceItem voiceItem) {
        sqLiteDatabase.delete(VoiceItemTable.NAME, VoiceItemTable.Cols.DATE + " = ?",
                new String[]{String.valueOf(voiceItem.getDate())});
    }

}
