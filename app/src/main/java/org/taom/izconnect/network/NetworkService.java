/*
 * Copyright AllSeen Alliance. All rights reserved.
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
 * REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
 * LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
 * OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package org.taom.izconnect.network;

import android.widget.ListView;

import org.alljoyn.bus.*;

import java.util.Map;

/** This class will handle all AllJoyn calls. */
public class NetworkService {

    private static final String ALLJOYN_TAG = "Alljoyn";

    private static final String PACKAGE_NAME = "org.taom.izconnect.network";
    private static final String OBJECT_PATH = "/izconnectService";
    private static final short CONTACT_PORT = 4753;

    private ListView devices;

    private BusAttachment mBus;
    private Observer mObserver;
    private SampleService sampleService = new SampleService();

    static {
        System.loadLibrary("alljoyn_java");
    }

    public NetworkService(ListView devices) {
        this.devices = devices;
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
        MyAboutData mAboutData = new MyAboutData();
        status = mAboutObj.announce(CONTACT_PORT, mAboutData);
        if (status != Status.OK) {
            System.out.println(ALLJOYN_TAG + "Problem while sending about info");
            return;
        }

        mBus.registerAboutListener(new AboutListener() {
            @Override
            public void announced(String s, int i, short i1, AboutObjectDescription[] aboutObjectDescriptions, Map<String, Variant> map) {
                System.out.print("announced: ");
                System.out.println(s);
            }
        });

        mBus.whoImplements(new String[]{PACKAGE_NAME});
        mBus.whoImplements(new String[]{SampleInterface.INTERFACE_NAME});

        status = mBus.registerSignalHandlers(sampleService);
        if (status != Status.OK) {
            System.out.println(ALLJOYN_TAG + "Problem while registering signal handler");
            return;
        }

        mObserver = new Observer(mBus, new Class[] { SampleInterface.class });
        mObserver.registerListener(new Observer.Listener() {

            @Override
            public void objectDiscovered(ProxyBusObject obj) {
                System.out.println("founded");
            }

            @Override
            public void objectLost(ProxyBusObject proxyBusObject) {

            }
        });

    }

    public void doDisconnect() {
//        mBus.unregisterBusListener();
        mBus.disconnect();
    }
}
