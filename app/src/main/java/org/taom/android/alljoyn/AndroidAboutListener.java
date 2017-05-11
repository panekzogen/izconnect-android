package org.taom.android.alljoyn;

import org.alljoyn.bus.AboutListener;
import org.alljoyn.bus.AboutObjectDescription;
import org.alljoyn.bus.Variant;
import org.taom.android.devices.DeviceAdapter;

import java.util.Map;

public class AndroidAboutListener implements AboutListener {
    private DeviceAdapter deviceAdapter;

    public AndroidAboutListener() {
    }

    @Override
    public void announced(String s, int i, short i1, AboutObjectDescription[] aboutObjectDescriptions, Map<String, Variant> map) {
        if (deviceAdapter != null)
            deviceAdapter.add(s, map);
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }
}
