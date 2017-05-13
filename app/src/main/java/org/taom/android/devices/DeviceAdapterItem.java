package org.taom.android.devices;

import org.taom.android.R;

public class DeviceAdapterItem {

    public enum DeviceType {
        BOARD(1, 0),
        MOBILE(2, R.drawable.mobile),
        PC(3, R.drawable.pc),
        UNKNOWN(4, 0);

        private final int id;
        private final int drawableId;

        DeviceType(int id, int drawableId) {
            this.id = id;
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public int getId() {
            return id;
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