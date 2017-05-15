package org.taom.android.alljoyn;

import android.os.Build;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.taom.android.devices.DeviceAdapter;
import org.taom.android.devices.DeviceAdapterItem;
import org.taom.izconnect.network.interfaces.MobileInterface;

import java.util.Set;

public class MobileServiceImpl implements BusObject, MobileInterface {
    private Set<String> subscribers;
    private DeviceAdapter deviceAdapter;

    public MobileServiceImpl(Set<String> subscribers, DeviceAdapter deviceAdapter) {
        this.subscribers = subscribers;
        this.deviceAdapter = deviceAdapter;
    }

    @Override
    public String getDeviceName() throws BusException {
        return Build.MODEL;
    }

    @Override
    public String getDeviceOS() throws BusException {
        return "Android " + Build.VERSION.RELEASE;
    }

    @Override
    public void subscribe(String busName) throws BusException {
        subscribers.add(busName);
        System.out.println(busName);
    }

    @Override
    public void notify(String sender, String notification) throws BusException {

    }

    @Override
    public void unsubscribe(String busName) throws BusException {
        subscribers.remove(busName);
        System.out.println(busName);

    }
}
