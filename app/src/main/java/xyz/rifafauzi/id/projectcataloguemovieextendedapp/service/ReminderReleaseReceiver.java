package xyz.rifafauzi.id.projectcataloguemovieextendedapp.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.BuildConfig;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.DetailMovieActivity;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.R;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.BaseApiService;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.RetrofitClient;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.db.DatabaseContract;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.Movies;
import xyz.rifafauzi.id.projectcataloguemovieextendedapp.entity.ResponseMovies;

import static xyz.rifafauzi.id.projectcataloguemovieextendedapp.api.Server.BASE_URL_API;

public class ReminderReleaseReceiver extends BroadcastReceiver {

    private final int NOTIF_ID_RELEASE = 21;
    public List<Movies> listMovie = new ArrayList<>();

    public ReminderReleaseReceiver() {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        getUpCommingMovie(context);
    }

    public void setReminder(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        intent.putExtra(DatabaseContract.EXTRA_MESSAGE_RECIEVE,message);
        intent.putExtra(DatabaseContract.EXTRA_TYPE_RECIEVE,type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context,R.string.reminderSave, Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReminderReleaseReceiver.class);
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context,R.string.reminderCancel, Toast.LENGTH_SHORT).show();
    }

    private void getUpCommingMovie(final Context context){
        BaseApiService service = RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
        Call<ResponseMovies> call = service.getUpComingMovie(BuildConfig.MOVIE_DB_API_KEY, DatabaseContract.LANG);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {

                listMovie = response.body().getMovies();

                List<Movies> items = response.body().getMovies();
                int index = new Random().nextInt(items.size());

                Movies item = items.get(index);

                int notifId = 200;
                String title = items.get(index).getTitle();
                String message = items.get(index).getOverview();
                showNotification(context, title, message, notifId, item);

            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Log.d("getUpCommingMovie", "onFailure: " + t.toString());
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId, Movies item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(context, DetailMovieActivity.class);
        i.putExtra("title", item.getTitle());
        i.putExtra("poster_path", item.getPosterPath());
        i.putExtra("overview", item.getOverview());
        i.putExtra("release_date", item.getReleaseDate());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

}