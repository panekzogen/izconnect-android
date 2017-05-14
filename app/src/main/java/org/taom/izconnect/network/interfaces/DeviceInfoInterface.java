package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusProperty;

public interface DeviceInfoInterface {
    @BusProperty
    String getDeviceName() throws BusException;

    @BusProperty
    String getDeviceOS() throws BusException;
}

