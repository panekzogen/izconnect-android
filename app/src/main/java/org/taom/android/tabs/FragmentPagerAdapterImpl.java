package org.taom.android.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.taom.android.devices.DeviceAdapter;

public class FragmentPagerAdapterImpl extends FragmentPagerAdapter {
    private static final int DEVICES_FRAGMEENT_POSITION = 0;
    private static final int CONTROLS_FRAGMENT_POSITION = 1;

    private static final CharSequence DEVICES_TITLE = "Devices";
    private static final CharSequence CONTROLS_TITLE = "Controls";

    private DeviceAdapter deviceAdapter;

    public FragmentPagerAdapterImpl(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DEVICES_FRAGMEENT_POSITION:
                DevicesListFragment devicesListFragment = new DevicesListFragment();
                devicesListFragment.setDeviceAdapter(deviceAdapter);
                return devicesListFragment;
            case CONTROLS_FRAGMENT_POSITION:
                return new ControlsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == DEVICES_FRAGMEENT_POSITION) {
            return DEVICES_TITLE;
        } else if (position == CONTROLS_FRAGMENT_POSITION) {
            return CONTROLS_TITLE;
        }
        return "Fragment " + position;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }
}
