package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.annotation.BusProperty;
import org.alljoyn.bus.annotation.BusSignal;

@BusInterface(name = BoardInterface.INTERFACE_NAME, announced = "true")
public interface BoardInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.BoardInterface";

    @BusProperty
    void setLight(boolean turnOn) throws BusException;

    @BusProperty
    boolean getLight() throws BusException;

    @BusProperty
    void setAutoMode(boolean autoMode) throws BusException;

    @BusProperty
    boolean getAutoMode() throws BusException;

    @BusMethod
    void fileData(String filename, byte[] data, boolean isScript) throws BusException;

    @BusMethod
    void runScript(String scriptName) throws BusException;

}
