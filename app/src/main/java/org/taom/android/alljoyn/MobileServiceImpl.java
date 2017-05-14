package org.taom.android.alljoyn;

import android.os.Build;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.taom.izconnect.network.interfaces.MobileInterface;

public class MobileServiceImpl implements BusObject, MobileInterface {
    @Override
    public String getDeviceName() throws BusException {
        return Build.MODEL;
    }

    @Override
    public String getDeviceOS() throws BusException {
        return "Android " + Build.VERSION.RELEASE;
    }

    @Override
    public void message(String str) throws BusException {
        System.out.println(str);
    }
}
