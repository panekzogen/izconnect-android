package org.taom.android.alljoyn;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.Observer;
import org.taom.izconnect.network.AbstractNetworkService;

public class AndroidNetworkService extends AbstractNetworkService {
    public AndroidNetworkService() {
        super();
    }

    @Override
    protected AboutDataListener getAboutData() {
        return new AndroidAboutData();
    }

    @Override
    protected AboutListener getAboutListener() {
        return null;
    }

    @Override
    protected Observer.Listener getObserverListener() {
        return null;
    }
}
