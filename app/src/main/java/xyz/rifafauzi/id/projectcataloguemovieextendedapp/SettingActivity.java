package xyz.rifafauzi.id.projectcataloguemovieextendedapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.service.ReminderPreference;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.service.ReminderReceiver;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.service.ReminderReleaseReceiver;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.dailyReminder)
    Switch dailyReminder;
    @BindView(R.id.relaseReminder)
    Switch releaseReminder;

    public ReminderReceiver reminderReceiverDaily;
    public ReminderReleaseReceiver reminderReceiverRelease;
    public ReminderPreference reminderPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        reminderReceiverDaily = new ReminderReceiver();
        reminderReceiverRelease = new ReminderReleaseReceiver();
        reminderPreference = new ReminderPreference(this);
        setPreference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnCheckedChanged(R.id.dailyReminder)
    public  void  setDailyRemind(boolean isChecked){
        editorDailyReminder = sDailyReminder.edit();
        if (isChecked) {
            editorDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, true);
            editorDailyReminder.commit();
            dailyReminderOn();
        } else {
            editorDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
            editorDailyReminder.commit();
            dailyReminderOff();
        }
    }
    @OnCheckedChanged(R.id.relaseReminder)
    public  void setReleaseRemind(boolean isChecked){
        editorReleaseReminder = sReleaseReminder.edit();
        if (isChecked) {
            editorReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, true);
            editorReleaseReminder.commit();
            releaseReminderOn();
        } else {
            editorReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
            editorReleaseReminder.commit();
            releaseReminderOff();
        }
    }

    @OnClick({R.id.txtlangauge, R.id.imgLanguage})
    public void onViewClicked(View view) {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    private void  setPreference(){
        sReleaseReminder = getSharedPreferences(DatabaseContract.KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(DatabaseContract.KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkSwUpcomingReminder = sReleaseReminder.getBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
        releaseReminder.setChecked(checkSwUpcomingReminder);
        boolean checkSwDailyReminder = sDailyReminder.getBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkSwDailyReminder);
    }

    private void releaseReminderOn() {
        String time = "03:31";
        String message = "Release Movie, please wait come back soon";
        reminderPreference.setReminderReleaseTime(time);
        reminderPreference.setReminderReleaseMessage(message);
        reminderReceiverRelease.setReminder(SettingActivity.this, DatabaseContract.TYPE_REMINDER_PREF, time, message);
    }

    private void releaseReminderOff() {
        reminderReceiverRelease.cancelReminder(SettingActivity.this);
    }
    private void dailyReminderOn() {
        String time = "03:31";
        String message = "Daily Movie, please wait come back soon";
        reminderPreference.setReminderDailyTime(time);
        reminderPreference.setReminderDailyMessage(message);
        reminderReceiverDaily.setReminder(SettingActivity.this, DatabaseContract.TYPE_REMINDER_RECIEVE, time, message);
    }

    private void dailyReminderOff() {
        reminderReceiverDaily.cancelReminder(SettingActivity.this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
