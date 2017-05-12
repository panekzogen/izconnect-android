package org.taom.android.alljoyn;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.taom.android.R;
import org.taom.android.devices.DeviceAdapter;

public class AllJoynService extends Service {
    private static final int NOTIFICATION_ID = 0xdefaced;

    private AndroidNetworkService networkService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AllJoynBinder();
    }

    public void onCreate() {
        startForeground(NOTIFICATION_ID, createNotification());
        networkService = new AndroidNetworkService();
        networkService.doConnect();
    }

    private Notification createNotification() {
        CharSequence title = "IZConnect";
        CharSequence message = "Alljoyn bus service.";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message);
        return mBuilder.build();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        networkService.doDisconnect();
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
//        networkService.unregisterListeners();
        networkService.setDeviceAdapter(deviceAdapter);
        networkService.registerListeners();
    }

    public class AllJoynBinder extends Binder {
        public AllJoynService getInstance() {
            return AllJoynService.this;
        }
    }
}
