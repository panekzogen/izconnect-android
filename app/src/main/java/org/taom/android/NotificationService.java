package org.taom.android;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import org.taom.izconnect.network.GFLogger;

import java.util.logging.Level;

public class NotificationService extends NotificationListenerService {

    public static final int NOTIFY_SUBSCRIBERS = 505;
    public static final String NOTIFICATION_BROADCAST_ACTION = "org.taom.android.NOTIFICATION_BROADCAST_ACTION";
    public static final String NOTIFICATION_EXTRA = "notificationText";

    private static final String TAG = "NotificationService";

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        GFLogger.log(Level.SEVERE, TAG, "Notification received: " + sbn.getNotification().tickerText);
        Intent i = new Intent(NOTIFICATION_BROADCAST_ACTION);
        i.putExtra(NOTIFICATION_EXTRA, (String) sbn.getNotification().tickerText);
        sendBroadcast(i);

    }

}
