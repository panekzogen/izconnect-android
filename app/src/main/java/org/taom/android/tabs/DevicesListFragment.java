package org.taom.android.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.taom.android.R;
import org.taom.android.devices.DeviceAdapter;

public class DevicesListFragment extends Fragment {
    private DeviceAdapter deviceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.devices_main, container, false);
        if (deviceAdapter != null) {
            final ListView devicesList = (ListView) rootView.findViewById(R.id.devicesListView);
            devicesList.setAdapter(deviceAdapter);
        }
        return rootView;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }
}
