package org.taom.izconnect.network;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;

public class SampleService implements SampleInterface, BusObject {
    @Override
    public void message(String str) throws BusException {
        System.out.println(str);
    }
}