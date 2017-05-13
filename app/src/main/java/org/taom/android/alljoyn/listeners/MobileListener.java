package org.taom.android.alljoyn.listeners;

import org.taom.android.alljoyn.AllJoynService;
import org.taom.android.devices.DeviceAdapterItem;

public class MobileListener extends AbstractListener {
    public static final int IDENTIFIER = DeviceAdapterItem.DeviceType.MOBILE.getId();

    public MobileListener(AllJoynService.BackgroundHandler handler) {
        super(handler);
    }

    @Override
    protected int getIdentifier() {
        return IDENTIFIER;
    }
}
