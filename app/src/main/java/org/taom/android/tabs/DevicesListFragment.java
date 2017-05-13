package org.taom.android.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.taom.android.R;
import org.taom.android.devices.DeviceAdapter;

public class DevicesListFragment extends Fragment {
    private DeviceAdapter deviceAdapter;
    private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.devices_main, container, false);
        if (deviceAdapter != null) {
            final ListView devicesList = (ListView) rootView.findViewById(R.id.devicesListView);
            devicesList.setAdapter(deviceAdapter);
            if (onItemClickListener != null) {
                devicesList.setOnItemClickListener(onItemClickListener);
            }
        }
        System.out.println("Im fragment create");
        return rootView;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
