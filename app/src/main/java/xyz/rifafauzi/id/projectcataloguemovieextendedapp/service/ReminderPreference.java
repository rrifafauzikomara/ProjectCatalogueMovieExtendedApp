package xyz.rifafauzi.id.projectcataloguemovieextendedapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;

public class ReminderPreference {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public ReminderPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(DatabaseContract.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setReminderReleaseTime(String time){
        editor.putString(DatabaseContract.KEY_REMINDER_Daily,time);
        editor.commit();
    }
    public void setReminderReleaseMessage (String message){
        editor.putString(DatabaseContract.KEY_REMINDER_MESSAGE_Release,message);
    }
    public void setReminderDailyTime(String time){
        editor.putString(DatabaseContract.KEY_REMINDER_Daily,time);
        editor.commit();
    }
    public void setReminderDailyMessage(String message){
        editor.putString(DatabaseContract.KEY_REMINDER_MESSAGE_Daily,message);
    }
}