package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

@BusInterface(name = MobileInterface.INTERFACE_NAME, announced = "true")
public interface MobileInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.MobileInterface";

    @BusSignal
    void message(String str) throws BusException;
}
