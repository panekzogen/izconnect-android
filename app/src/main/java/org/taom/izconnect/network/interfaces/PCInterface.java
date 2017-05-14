package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

@BusInterface(name = PCInterface.INTERFACE_NAME, announced = "true")
public interface PCInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.PCInterface";

    @BusSignal
    void message(String str) throws BusException;
}
