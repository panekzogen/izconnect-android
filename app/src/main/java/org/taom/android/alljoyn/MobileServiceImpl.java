package org.taom.android.alljoyn;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.taom.izconnect.network.interfaces.MobileInterface;

public class MobileServiceImpl implements BusObject, MobileInterface {
    @Override
    public void message(String str) throws BusException {

    }
}
