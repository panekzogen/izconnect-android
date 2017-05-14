package org.taom.android.alljoyn;

import android.os.Build;

import org.taom.izconnect.network.AbstractAboutData;

public class AndroidAboutData extends AbstractAboutData {
    @Override
    protected String getDeviceName() {
        return Build.MODEL;
    }

    @Override
    protected String getSoftwareVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }
}
