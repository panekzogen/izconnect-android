package org.taom.android.alljoyn.listeners;

import org.taom.android.alljoyn.AllJoynService;
import org.taom.android.devices.DeviceAdapterItem;

public class BoardListener extends AbstractListener {
    public static final int IDENTIFIER = DeviceAdapterItem.DeviceType.BOARD.getId();

    public BoardListener(AllJoynService.BackgroundHandler handler) {
        super(handler);
    }

    @Override
    protected int getIdentifier() {
        return IDENTIFIER;
    }
}
