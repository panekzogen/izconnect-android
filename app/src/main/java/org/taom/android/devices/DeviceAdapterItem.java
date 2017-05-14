package org.taom.android.devices;

import org.taom.android.R;
import org.taom.izconnect.network.interfaces.BoardInterface;
import org.taom.izconnect.network.interfaces.MobileInterface;
import org.taom.izconnect.network.interfaces.PCInterface;

public class DeviceAdapterItem {

    public enum DeviceType {
        BOARD(1, R.drawable.board, BoardInterface.class),
        MOBILE(2, R.drawable.mobile, MobileInterface.class),
        PC(3, R.drawable.pc, PCInterface.class),
        UNKNOWN(4, 0, null);

        private final int id;
        private final int drawableId;
        private final Class interfaceClass;

        DeviceType(int id, int drawableId, Class interfaceClass) {
            this.id = id;
            this.drawableId = drawableId;
            this.interfaceClass = interfaceClass;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public int getId() {
            return id;
        }

        public Class getInterfaceClass() {
            return interfaceClass;
        }

        public static DeviceType valueOf(int id) {
            switch (id) {
                case 1:
                    return BOARD;
                case 2:
                    return MOBILE;
                case 3:
                    return PC;
                default:
                    return UNKNOWN;
            }
        }
    }

    private String busName;
    private DeviceType deviceType;
    private String deviceName;
    private String deviceOS;

    public DeviceAdapterItem(String busName, DeviceType deviceType, String deviceName, String deviceOS) {
        this.busName = busName;
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.deviceOS = deviceOS;
    }

    public String getBusName() {
        return busName;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

}