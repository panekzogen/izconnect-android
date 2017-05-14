package org.taom.android.tabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.taom.android.R;
import org.taom.android.devices.DeviceAdapterItem;
import org.taom.izconnect.network.interfaces.PCInterface;

public class ControlsFragment extends Fragment {
    private DeviceAdapterItem selectedItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resource;
        if (selectedItem == null) {
            resource = R.layout.controls_main;
        } else {
            switch (selectedItem.getDeviceType()) {
                case BOARD:
                    resource = R.layout.board_controls;
                    break;
                case MOBILE:
                    resource = R.layout.mobile_controls;
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

    public void setSelectedItem(DeviceAdapterItem selectedItem) {
        this.selectedItem = selectedItem;
    }
}
