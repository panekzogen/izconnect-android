package org.taom.izconnect.network;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.AboutObj;
import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.Observer;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;
import org.taom.izconnect.network.interfaces.SampleInterface;
import org.taom.izconnect.network.interfaces.SampleService;

public abstract class AbstractNetworkService {

    private static final String ALLJOYN_TAG = "Alljoyn";

    public static final String PACKAGE_NAME = "org.taom.izconnect.network";
    private static final String OBJECT_PATH = "/izconnectService";
    private static final short CONTACT_PORT = 4753;

    private BusAttachment mBus;
    Observer mObserver;
    private SampleService sampleService = new SampleService();

    static {
        System.loadLibrary("alljoyn_java");
    }

    public AbstractNetworkService() {
    }

    public void doConnect() {
        mBus = new BusAttachment(PACKAGE_NAME, BusAttachment.RemoteMessage.Receive);

        Status status = mBus.connect();
        if (Status.OK != status) {
            System.out.println(ALLJOYN_TAG + "Cannot connect");
            return;
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
                // Allow all connections on our contact port.
                return sessionPort == CONTACT_PORT;
            }
        });
        if (status != Status.OK) {
            System.out.println("FAIL bind");
            return;
        }

        status = mBus.registerBusObject(sampleService, OBJECT_PATH);
        if (status != Status.OK) {
            System.out.printf("FAIL register");
        }

        AboutObj mAboutObj = new AboutObj(mBus);
        AboutDataListener mAboutData = getAboutData();
        status = mAboutObj.announce(CONTACT_PORT, mAboutData);
        if (status != Status.OK) {
            System.out.println(ALLJOYN_TAG + "Problem while sending about info");
            return;
        }

        mBus.whoImplements(new String[]{PACKAGE_NAME});
//        mBus.whoImplements(new String[]{SampleInterface.INTERFACE_NAME});

        status = mBus.registerSignalHandlers(sampleService);
        if (status != Status.OK) {
            System.out.println(ALLJOYN_TAG + "Problem while registering signal handler");
            return;
        }
    }

    public void registerListeners() {
        if (mBus != null) {
            AboutListener aboutListener = getAboutListener();
            if (aboutListener != null) {
                mBus.registerAboutListener(aboutListener);
            }

            mObserver = new Observer(mBus, new Class[]{SampleInterface.class});
            Observer.Listener observerListener = getObserverListener();
            if (observerListener != null) {
                mObserver.registerListener(observerListener);
            }
        }
    }

    public void unregisterListeners() {
        if (mBus != null) {
            mBus.unregisterAboutListener(getAboutListener());

            if (mObserver != null) {
                mObserver.unregisterListener(getObserverListener());
            }
        }
    }

    public void doDisconnect() {
        if (mBus != null) {
            unregisterListeners();
            mBus.unregisterBusObject(sampleService);
            mBus.unregisterSignalHandlers(sampleService);
            mBus.disconnect();
        }
    }

    protected abstract AboutDataListener getAboutData();

    protected abstract AboutListener getAboutListener();

    protected abstract Observer.Listener getObserverListener();
}