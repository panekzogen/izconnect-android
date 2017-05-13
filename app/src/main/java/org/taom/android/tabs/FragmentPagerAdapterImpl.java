package org.taom.android.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.AdapterView;

import org.taom.android.devices.DeviceAdapter;
import org.taom.android.devices.DeviceAdapterItem;

public class FragmentPagerAdapterImpl extends FragmentPagerAdapter {
    public static final int DEVICES_FRAGMEENT_POSITION = 0;
    public static final int CONTROLS_FRAGMENT_POSITION = 1;

    private static final CharSequence DEVICES_TITLE = "Devices";
    private static final CharSequence CONTROLS_TITLE = "Controls";

    private DeviceAdapter deviceAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;

    public FragmentPagerAdapterImpl(FragmentManager fm, DeviceAdapter deviceAdapter) {
        super(fm);
        this.deviceAdapter = deviceAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DEVICES_FRAGMEENT_POSITION:
                DevicesListFragment devicesListFragment = new DevicesListFragment();
                devicesListFragment.setDeviceAdapter(deviceAdapter);
                devicesListFragment.setOnItemClickListener(onItemClickListener);
                return devicesListFragment;
            case CONTROLS_FRAGMENT_POSITION:
                ControlsFragment controlsFragment = new ControlsFragment();
                controlsFragment.setDeviceAdapter(deviceAdapter);
                return controlsFragment;
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

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof ControlsFragment) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
