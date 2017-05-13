package org.taom.android.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.taom.android.R;
import org.taom.android.devices.DeviceAdapter;
import org.taom.android.devices.DeviceAdapterItem;

public class ControlsFragment extends Fragment {
    private DeviceAdapter deviceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DeviceAdapterItem deviceAdapterItem = deviceAdapter.getSelectedItem();
        int resource;
        if (deviceAdapterItem == null) {
            resource = R.layout.controls_main;
            System.out.println("null LOL");
        } else {
            System.out.println(deviceAdapterItem.getDeviceType());
            switch (deviceAdapterItem.getDeviceType()) {
                case BOARD:
                    resource = R.layout.board_controls;
                    break;
                case MOBILE:
                    resource = R.layout.pc_controls;
                    break;
                case PC:
                    resource = R.layout.pc_controls;
                    break;
                default:
                    resource = R.layout.controls_main;
                    break;
            }
        }
        View rootView = inflater.inflate(resource, container, false);
        return rootView;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }
}
