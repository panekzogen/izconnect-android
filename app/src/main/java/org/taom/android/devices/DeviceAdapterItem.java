package org.taom.android.devices;

import org.taom.android.R;

public class DeviceAdapterItem {

    public enum DeviceType {
        PC(R.drawable.pc),
        MOBILE(R.drawable.mobile),
        BOARD(0);

        private final int drawableId;

        DeviceType(int drawableId) {
            this.drawableId = drawableId;
        }

        public int getDrawableId() {
            return drawableId;
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