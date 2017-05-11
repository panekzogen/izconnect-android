package org.taom.android.alljoyn;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.taom.android.DeviceAdapter;
import org.taom.android.R;

public class AllJoynService extends Service {
    private static final int NOTIFICATION_ID = 0xdefaced;

    private AndroidNetworkService networkService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public void onCreate() {
//        networkService = new AndroidNetworkService(R.id.devices_main);
        startForeground(NOTIFICATION_ID, createNotification());

        networkService = new AndroidNetworkService(new DeviceAdapter());
        networkService.doConnect();
    }

    private Notification createNotification() {
        CharSequence title = "IZConnect";
        CharSequence message = "Alljoyn bus service.";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message);

//        Intent resultIntent = new Intent(this, MainActivity.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(mId, mBuilder.build());
        return mBuilder.build();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        networkService.doDisconnect();
    }

    public class AllJoynBinder extends Binder {

    }
}
