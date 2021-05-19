package com.codingbjs.voicenotice.util;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class ArgParamManager {

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    public static final String FINISH = "Finish";
    public static final String PERMISSIONS_OK = "PermissionsOk";


    public static Intent getAllFlagsIntent(Context context, Class runActivity, String param1, String param2){
        Intent allFlagsIntent = new Intent(context, runActivity);
        allFlagsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        allFlagsIntent.putExtra(ARG_PARAM1, param1);
        allFlagsIntent.putExtra(ARG_PARAM2, param2);
        return allFlagsIntent;
    }

    public static Intent getNewTaskIntent(Context context, Class runActivity, String param1, String param2){
        Intent newTaskIntent = new Intent(context, runActivity);
        newTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newTaskIntent.putExtra(ARG_PARAM1, param1);
        newTaskIntent.putExtra(ARG_PARAM2, param2);
        return newTaskIntent;
    }

    public static Intent getSingleTopIntent(Context context, Class runActivity, String param1, String param2){
        Intent singleTopIntent = new Intent(context, runActivity);
        singleTopIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        singleTopIntent.putExtra(ARG_PARAM1, param1);
        singleTopIntent.putExtra(ARG_PARAM2, param2);
        return singleTopIntent;
    }

    public static Intent getClearTopIntent(Context context, Class runActivity, String param1, String param2){
        Intent clearTopIntent = new Intent(context, runActivity);
        clearTopIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        clearTopIntent.putExtra(ARG_PARAM1, param1);
        clearTopIntent.putExtra(ARG_PARAM2, param2);
        return clearTopIntent;
    }

    public static  PowerManager.WakeLock screenWakeLock;
    public static void acquireWakeLock(Context context) {
        PowerManager pm = (PowerManager)context.getSystemService
                (Context.POWER_SERVICE);
        if (pm != null) {
            screenWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP
                    , context.getClass().getName());
        }

        if (screenWakeLock != null) {
            screenWakeLock.acquire(10*60*1000L /*10 minutes*/);
        }
    }

    public  static void releaseWakeLock() {
        if (screenWakeLock != null) {
            screenWakeLock.release();
            screenWakeLock = null;
        }
    }
}
