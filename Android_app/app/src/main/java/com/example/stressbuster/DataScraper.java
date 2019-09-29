package com.example.stressbuster;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class DataScraper extends Service {

    private WindowManager windowManager;
    private View view;



    public DataScraper() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();



//
//        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.overlay_button, null);
//
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.OPAQUE
//        );
//
//        params.gravity = Gravity.TOP | Gravity.LEFT;
//        params.x = 0;
//        params.y = 100;
//
//
//        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        windowManager.addView(view, params);
//
//        counterFab = (CounterFab) view.findViewById(R.id.head);
//        counterFab.setCount(1);
//
//        counterFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        counterFab.setOnTouchListener(new View.OnTouchListener() {
//
//            int initialX;
//            int initialY;
//            float initialTouchX;
//            float initialTouchY;
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        initialX = params.x;
//                        initialY = params.y;
//
//                        initialTouchX = motionEvent.getRawX();
//                        initialTouchY = motionEvent.getRawY();
//                        return true;
//
//                    case MotionEvent.ACTION_UP:
//                        return true;
//
//                    case MotionEvent.ACTION_MOVE:
//
//                        float diffX = Math.round(motionEvent.getRawX() - initialTouchX);
//                        float diffY = Math.round(motionEvent.getRawY() - initialTouchY);
//
//                        params.x = initialX + (int) diffX;
//                        params.y = initialY + (int) diffY;
//
//                        windowManager.updateViewLayout(view, params);
//                }
//                return false;
//            }
//        });


        Intent intent = new Intent(this, UserDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_SERVICE)
                .setSmallIcon(R.drawable.ic_pages_black_24dp)
                .setContentTitle("Blue Flag")
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
        if(view != null) {
            windowManager.removeView(view);
        }
    }
}
