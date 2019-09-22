package com.example.stressbuster;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Date;

public class DataScraper extends Service {
    public DataScraper() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Date currentTime = Calendar.getInstance().getTime();
        Log.d("DateTime", currentTime.toString());

        Intent intent = new Intent(this, UserDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_SERVICE)
                .setSmallIcon(R.drawable.ic_pages_black_24dp)
                .setContentTitle("Stress Buster")
                .setContentIntent(pendingIntent)
                .setContentText("Hope your day was good! Rate it now :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NotificationManagerCompat.IMPORTANCE_DEFAULT,builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
