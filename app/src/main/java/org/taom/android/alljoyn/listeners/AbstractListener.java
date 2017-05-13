package org.taom.android.alljoyn.listeners;

import org.alljoyn.bus.Observer;
import org.alljoyn.bus.ProxyBusObject;
import org.taom.android.alljoyn.AllJoynService;

public abstract class AbstractListener implements Observer.Listener {
    private AllJoynService.BackgroundHandler handler;

    public AbstractListener(AllJoynService.BackgroundHandler handler) {
        this.handler = handler;
    }

    @Override
    public void objectDiscovered(ProxyBusObject proxyBusObject) {
        handler.deviceDiscovered(getIdentifier(), proxyBusObject);
    }

    @Override
    public void objectLost(ProxyBusObject proxyBusObject) {
        handler.deviceLost(proxyBusObject);
    }

    protected abstract int getIdentifier();
}
