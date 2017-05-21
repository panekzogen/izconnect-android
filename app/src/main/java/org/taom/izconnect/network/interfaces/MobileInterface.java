package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;

@BusInterface(name = MobileInterface.INTERFACE_NAME, announced = "true")
public interface MobileInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.MobileInterface";

    @BusMethod
    void subscribe(String busName) throws BusException;

    @BusMethod
    void notify(String devicename, String sender, String notification) throws BusException;

    @BusMethod
    void unsubscribe(String busName) throws BusException;

    @BusMethod
    void fileData(String filename, byte[] data) throws BusException;

}
