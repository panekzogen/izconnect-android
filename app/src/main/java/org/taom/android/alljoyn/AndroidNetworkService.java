package org.taom.android.alljoyn;

import android.os.Handler;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.Observer;
import org.taom.android.alljoyn.listeners.AndroidAboutListener;
import org.taom.android.alljoyn.listeners.BoardListener;
import org.taom.android.alljoyn.listeners.MobileListener;
import org.taom.android.alljoyn.listeners.PCListener;
import org.taom.android.devices.DeviceAdapter;
import org.taom.izconnect.network.AbstractNetworkService;

public class AndroidNetworkService extends AbstractNetworkService {
    private final MobileListener mobileListener;
    private final PCListener pcListener;
    private final BoardListener boardListener;

    public AndroidNetworkService(AllJoynService.BackgroundHandler handler) {
        super();
        mobileListener = new MobileListener(handler);
        pcListener = new PCListener(handler);
        boardListener = new BoardListener(handler);
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
    protected Observer.Listener getMobileListener() {
        return mobileListener;
    }

    @Override
    protected Observer.Listener getPCListener() {
        return pcListener;
    }

    @Override
    protected Observer.Listener getBoardListener() {
        return boardListener;
    }

    @Override
    protected String[] getInterestingInterfaces() {
        return new String[]{PACKAGE_NAME + ".interfaces."};
    }
}
