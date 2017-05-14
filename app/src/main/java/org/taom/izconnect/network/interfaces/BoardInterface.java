package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

@BusInterface(name = BoardInterface.INTERFACE_NAME, announced = "true")
public interface BoardInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.BoardInterface";

    @BusSignal
    void message(String str) throws BusException;
}
