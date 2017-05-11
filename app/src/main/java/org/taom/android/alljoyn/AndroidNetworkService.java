package org.taom.android.alljoyn;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.Observer;
import org.taom.android.devices.DeviceAdapter;
import org.taom.izconnect.network.AbstractNetworkService;

public class AndroidNetworkService extends AbstractNetworkService {
    private final AndroidAboutListener androidAboutListener;
    private final AndroidObserverListener androidObserverListener;

    public AndroidNetworkService() {
        super();
        androidAboutListener = new AndroidAboutListener();
        androidObserverListener = new AndroidObserverListener();
    }

    @Override
    protected AboutDataListener getAboutData() {
        return new AndroidAboutData();
    }

    @Override
    protected AboutListener getAboutListener() {
        return androidAboutListener;
    }

    @Override
    protected Observer.Listener getObserverListener() {
        return androidObserverListener;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        androidAboutListener.setDeviceAdapter(deviceAdapter);
        androidObserverListener.setDeviceAdapter(deviceAdapter);
    }
}
