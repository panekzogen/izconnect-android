package org.taom.android.alljoyn;

import org.alljoyn.bus.Observer;
import org.alljoyn.bus.ProxyBusObject;
import org.taom.android.devices.DeviceAdapter;

public class AndroidObserverListener implements Observer.Listener {
    private DeviceAdapter deviceAdapter;

    public AndroidObserverListener() {
    }

    @Override
    public void objectDiscovered(ProxyBusObject proxyBusObject) {

    }

    @Override
    public void objectLost(ProxyBusObject proxyBusObject) {
        if (deviceAdapter != null)
            deviceAdapter.remove(proxyBusObject.getBusName());
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }
}
