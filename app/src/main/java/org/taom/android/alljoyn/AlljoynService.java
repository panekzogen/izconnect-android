package org.taom.android.alljoyn;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.alljoyn.bus.ProxyBusObject;
import org.taom.android.R;
import org.taom.android.devices.DeviceAdapter;
import org.taom.android.devices.DeviceAdapterItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AllJoynService extends Service {
    private static final int NOTIFICATION_ID = 0xdefaced;

    private AndroidNetworkService mNetworkService;
    private MobileServiceImpl mobileService;
    private BackgroundHandler mBackgroundHandler;

    private DeviceAdapter deviceAdapter;
    private Map<DeviceAdapterItem, ProxyBusObject> map = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AllJoynBinder();
    }

    public void onCreate() {
        HandlerThread busThread = new HandlerThread("BackgroundHandler");
        busThread.start();
        mBackgroundHandler = new BackgroundHandler(busThread.getLooper());

        startForeground(NOTIFICATION_ID, createNotification());

        mobileService = new MobileServiceImpl();
        mNetworkService = new AndroidNetworkService(mBackgroundHandler);

        mBackgroundHandler.connect();
        mBackgroundHandler.registerInterface();
        mBackgroundHandler.announce();
        mBackgroundHandler.registerListeners();
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

    public final class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        public void connect() {
            Message msg = mBackgroundHandler.obtainMessage(CONNECT);
            mBackgroundHandler.sendMessage(msg);
        }

        public void disconnect() {
            Message msg = mBackgroundHandler.obtainMessage(DISCONNECT);
            mBackgroundHandler.sendMessage(msg);
        }

        public void registerInterface() {
            Message msg = mBackgroundHandler.obtainMessage(REGISTER_INTERFACE);
            mBackgroundHandler.sendMessage(msg);
        }

        public void unregisterInterface() {
            Message msg = mBackgroundHandler.obtainMessage(UNREGISTER_INTERFACE);
            mBackgroundHandler.sendMessage(msg);
        }

        public void announce() {
            Message msg = mBackgroundHandler.obtainMessage(ANNOUNCE);
            mBackgroundHandler.sendMessage(msg);
        }

        public void registerListeners() {
            Message msg = mBackgroundHandler.obtainMessage(REGISTER_LISTENERS);
            mBackgroundHandler.sendMessage(msg);
        }

        public void unregisterListeners() {
            Message msg = mBackgroundHandler.obtainMessage(UNREGISTER_LISTENERS);
            mBackgroundHandler.sendMessage(msg);
        }

        public void deviceDiscovered(int id, ProxyBusObject proxyBusObject) {
            Message msg = mBackgroundHandler.obtainMessage(DEVICE_DISCOVERED, id, 0, proxyBusObject);
            mBackgroundHandler.sendMessage(msg);
        }

        public void deviceLost(ProxyBusObject proxyBusObject) {
            Message msg = mBackgroundHandler.obtainMessage(DEVICE_LOST, proxyBusObject);
            mBackgroundHandler.sendMessage(msg);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONNECT:
                    mNetworkService.doConnect();
                    break;
                case DISCONNECT:
                    mNetworkService.doDisconnect();
                    getLooper().quit();
                    break;
                case REGISTER_INTERFACE:
                    mNetworkService.registerInterface(mobileService);
                    break;
                case UNREGISTER_INTERFACE:
                    if (mobileService != null) {
                        mNetworkService.unregisterInterface(mobileService);
                    }
                    break;
                case ANNOUNCE:
                    mNetworkService.announce();
                    break;
                case REGISTER_LISTENERS:
                    mNetworkService.registerListeners();
                    break;
                case UNREGISTER_LISTENERS:
                    mNetworkService.unregisterListeners();
                    break;
                case DEVICE_DISCOVERED:
                    doDeviceDiscovered(msg.arg1, (ProxyBusObject)msg.obj);
                    break;
                case DEVICE_LOST:
                    doDeviceLost((ProxyBusObject)msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

    private static final int CONNECT = 1;
    private static final int DISCONNECT = 2;
    private static final int REGISTER_INTERFACE = 3;
    private static final int UNREGISTER_INTERFACE = 4;
    private static final int ANNOUNCE = 5;
    private static final int REGISTER_LISTENERS = 6;
    private static final int UNREGISTER_LISTENERS = 7;
    private static final int DEVICE_DISCOVERED = 8;
    private static final int DEVICE_LOST = 9;

    private void doDeviceDiscovered(int id, ProxyBusObject proxyBusObject) {
        String busName = proxyBusObject.getBusName();
        DeviceAdapterItem.DeviceType deviceType = DeviceAdapterItem.DeviceType.valueOf(id);
        String deviceName = "device" + id;
        String deviceOS = "device" + id;

        DeviceAdapterItem deviceAdapterItem = new DeviceAdapterItem(busName, deviceType, deviceName, deviceOS);

        if (deviceAdapter != null) {
            deviceAdapter.add(deviceAdapterItem);
        }
        map.put(deviceAdapterItem, proxyBusObject);
    }

    private void doDeviceLost(ProxyBusObject device) {
        for (Map.Entry<DeviceAdapterItem, ProxyBusObject> entry : map.entrySet()) {
            if (device == entry.getValue()) {
                if (deviceAdapter != null) {
                    deviceAdapter.remove(entry.getKey());
                }
                map.remove(entry.getKey());
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        mBackgroundHandler.unregisterListeners();
        mBackgroundHandler.unregisterInterface();
        mBackgroundHandler.disconnect();
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        deviceAdapter.addAll(map.keySet());
        this.deviceAdapter = deviceAdapter;
    }

    public class AllJoynBinder extends Binder {
        public AllJoynService getInstance() {
            return AllJoynService.this;
        }
    }
}
