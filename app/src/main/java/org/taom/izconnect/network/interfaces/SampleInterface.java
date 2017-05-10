package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusSignal;

@BusInterface(name = SampleInterface.INTERFACE_NAME, announced = "true")
public interface SampleInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.SampleInterface";

    @BusSignal
    void message(String str) throws BusException;
}
