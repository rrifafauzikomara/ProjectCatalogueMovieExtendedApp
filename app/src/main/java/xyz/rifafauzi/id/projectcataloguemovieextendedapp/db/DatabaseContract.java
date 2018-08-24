package xyz.rifafauzi.id.projectcataloguemovieextendedapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import xyz.rifafauzi.id.projectcataloguemovieextendedapp.BuildConfig;

public final class DatabaseContract {

    public static final String AUTHORITY = "xyz.rifafauzi.id.projectcataloguemovieextendedapp";
    public static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class FavoriteColumns implements BaseColumns {

        public static String TABLE_FAVORITE = "favorite";

        public static String NAME = "name";
        public static String POSTER = "poster";
        public static String RELEASE_DATE = "date";
        public static String DESCRIPTION = "description";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();

    }

    public static final String KEY_HEADER_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_HEADER_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";
    public final static String PREF_NAME = "reminderPreferences";
    public static final String LINK_IMAGE = "http://image.tmdb.org/t/p/w185/";
    public static final String LANG = "en-US";
    public static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    public final static String KEY_REMINDER_Daily = "DailyTime";
    public final static String KEY_REMINDER_MESSAGE_Release = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_Daily = "reminderMessageDaily";
    public static final String EXTRA_MESSAGE_PREF = "message";
    public static final String EXTRA_TYPE_PREF = "type";
    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String EXTRA_MESSAGE_RECIEVE = "messageRelease";
    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}