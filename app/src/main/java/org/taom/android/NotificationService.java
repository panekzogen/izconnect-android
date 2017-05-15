package org.taom.android;

import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationService extends NotificationListenerService {

    public static final int NOTIFY_SUBSCRIBERS = 505;
    private Handler handler;

    public NotificationService() {
    }

    public NotificationService(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        System.out.println("NOTIFICATION");
        handler.sendMessage(handler.obtainMessage(NOTIFY_SUBSCRIBERS, sbn.getNotification().tickerText));
    }


}
