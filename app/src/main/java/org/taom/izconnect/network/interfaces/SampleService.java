package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.taom.izconnect.network.interfaces.SampleInterface;

public class SampleService implements SampleInterface, BusObject {
    @Override
    public void message(String str) throws BusException {
        System.out.println(str);
    }
}