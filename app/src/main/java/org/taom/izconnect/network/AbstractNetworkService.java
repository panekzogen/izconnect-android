package org.taom.izconnect.network;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.AboutObj;
import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusObject;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.Observer;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;
import org.taom.izconnect.network.interfaces.BoardInterface;
import org.taom.izconnect.network.interfaces.DeviceInfoInterface;
import org.taom.izconnect.network.interfaces.MobileInterface;
import org.taom.izconnect.network.interfaces.PCInterface;

import java.util.logging.Level;

import static org.taom.izconnect.network.GFLogger.log;

public abstract class AbstractNetworkService {

    private static final String TAG = "AllJoyn";

    protected static final String PACKAGE_NAME = "org.taom.izconnect.network";
    private static final String OBJECT_PATH = "/izconnectService";
    private static final short CONTACT_PORT = 4753;

    private BusAttachment mBus;
    private Observer boardObserver;
    private Observer mobileObserver;
    private Observer pcObserver;
    private AboutObj mAboutObj;
    private AboutDataListener mAboutData;

    static {
        System.loadLibrary("alljoyn_java");
    }

    public AbstractNetworkService() {
    }

    public Status doConnect() {
        Status status;
        mBus = new BusAttachment(PACKAGE_NAME, BusAttachment.RemoteMessage.Receive);

        status = mBus.connect();
        if (Status.OK != status) {
            log(Level.SEVERE, TAG, "Cannot connect to bus", PACKAGE_NAME);
            return status;
        }

        Mutable.ShortValue contactPort = new Mutable.ShortValue(CONTACT_PORT);

        SessionOpts sessionOpts = new SessionOpts();
        sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
        sessionOpts.isMultipoint = false;
        sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
        sessionOpts.transports = SessionOpts.TRANSPORT_ANY;

        status = mBus.bindSessionPort(contactPort, sessionOpts, new SessionPortListener() {
            @Override
            public boolean acceptSessionJoiner(short sessionPort,
                                               String joiner, SessionOpts sessionOpts) {
                return sessionPort == CONTACT_PORT;
            }
        });
        if (status != Status.OK) {
            log(Level.SEVERE, TAG, "Cannot bind port", String.valueOf(CONTACT_PORT));
            return status;
        }

        return status;
    }

    public Status registerInterface(BusObject busObject) {
        Status status;
        status = mBus.registerBusObject(busObject, OBJECT_PATH);
        if (status != Status.OK) {
            log(Level.SEVERE, TAG, "Cannot register bus object");
            return status;
        }

        status = mBus.registerSignalHandlers(busObject);
        if (status != Status.OK) {
            log(Level.SEVERE, TAG, "Problem while registering signal handler");
            return status;
        }

        return status;
    }

    public void unregisterInterface(BusObject busObject) {
        mBus.unregisterSignalHandlers(busObject);
        mBus.unregisterBusObject(busObject);
    }

    public Status announce() {
        Status status;

        mAboutObj = new AboutObj(mBus);
        mAboutData = getAboutData();
        status = mAboutObj.announce(CONTACT_PORT, mAboutData);
        if (status != Status.OK) {
            log(Level.SEVERE, TAG, "Problem while sending about info");
            return status;
        }

        return status;
    }

    public void registerListeners() {
        if (mBus != null) {
//            AboutListener aboutListener = getAboutListener();
//            if (aboutListener != null) {
//                mBus.registerAboutListener(aboutListener);
//            }

            Observer.Listener boardListener = getBoardListener();
            if (boardListener != null) {
                boardObserver = new Observer(mBus, new Class[]{BoardInterface.class});
                boardObserver.registerListener(boardListener);
            }

            Observer.Listener mobileListener = getMobileListener();
            if (mobileListener != null) {
                mobileObserver = new Observer(mBus, new Class[]{MobileInterface.class});
                mobileObserver.registerListener(mobileListener);
            }

            Observer.Listener pcListener = getPCListener();
            if (pcListener != null) {
                pcObserver = new Observer(mBus, new Class[]{PCInterface.class});
                pcObserver.registerListener(pcListener);
            }


            mBus.whoImplements(getInterestingInterfaces());
        }
    }

    public void unregisterListeners() {
        if (mBus != null) {
//            mBus.unregisterAboutListener(getAboutListener());

            if (boardObserver != null) {
                boardObserver.unregisterListener(getBoardListener());
            }

            if (mobileObserver != null) {
                mobileObserver.unregisterListener(getMobileListener());
            }

            if (pcObserver != null) {
                pcObserver.unregisterListener(getPCListener());
            }

            mBus.cancelWhoImplements(getInterestingInterfaces());
        }
    }

    public void doDisconnect() {
        if (mBus != null) {
            unregisterListeners();
            mBus.disconnect();
        }
    }

    protected abstract AboutDataListener getAboutData();

    protected abstract AboutListener getAboutListener();

    protected abstract Observer.Listener getMobileListener();
    protected abstract Observer.Listener getPCListener();
    protected abstract Observer.Listener getBoardListener();

    protected abstract String[] getInterestingInterfaces();
}