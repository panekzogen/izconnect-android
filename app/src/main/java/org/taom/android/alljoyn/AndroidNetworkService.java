package org.taom.android.alljoyn;

import android.widget.ListView;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.Observer;
import org.taom.izconnect.network.AbstractNetworkService;

public class AndroidNetworkService extends AbstractNetworkService {
    private final AndroidAboutListener androidAboutListener;
    private final AndroidObserverListener androidObserverListener;

    public AndroidNetworkService(ListView devices) {
        super();
        androidAboutListener = new AndroidAboutListener();
        androidObserverListener = new AndroidObserverListener();
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
