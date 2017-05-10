package org.taom.android.alljoyn;

import android.os.Build;

import org.taom.izconnect.network.AbstractAboutData;

public class AndroidAboutData extends AbstractAboutData {
    @Override
    protected String getDeviceName() {
        StringBuilder deviceName = new StringBuilder();
        deviceName.append(Build.MODEL)
                .append(";")
                .append("Android ")
                .append(Build.VERSION.RELEASE);
        return deviceName.toString();
    }
}
