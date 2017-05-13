package org.taom.android.devices;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.Variant;
import org.taom.android.R;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class DeviceAdapter extends BaseAdapter implements Serializable {

    private static class DeviceViewHolder {
        final TextView deviceName;
        final ImageView deviceIcon;

        public DeviceViewHolder(TextView name, ImageView icon) {
            this.deviceName = name;
            this.deviceIcon = icon;
        }
    }

    private final Vector<DeviceAdapterItem> list;
    private UIUpdater uiUpdater;
    private DeviceAdapterItem selectedItem;

    public DeviceAdapter() {
        list = new Vector<>();
    }

    public DeviceAdapterItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = list.get(selectedItem);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return System.identityHashCode(list.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.deviceName);
            ImageView icon = (ImageView) convertView.findViewById(R.id.deviceIcon);
            convertView.setTag(new DeviceViewHolder(name, icon));
        }
        DeviceViewHolder data = (DeviceViewHolder) convertView.getTag();
        DeviceAdapterItem item = list.get(position);
        data.deviceName.setText(item.getDeviceName() + "\n" + item.getDeviceOS());
        data.deviceIcon.setImageResource(item.getDeviceType().getDrawableId());
        return convertView;
    }

    public void add(String busName, Map<String, Variant> map) {
        String deviceName = "";
        try {
            deviceName = map.get("DeviceName").getObject(String.class);
        } catch (BusException e) {
            Log.w("deviceAdapter", "Cannot get device name");
        }
        String[] deviceNameWithOS = deviceName.split(";");
        DeviceAdapterItem.DeviceType deviceType = deviceNameWithOS[1].contains("Android") ? DeviceAdapterItem.DeviceType.MOBILE : DeviceAdapterItem.DeviceType.PC;
        final DeviceAdapterItem deviceAdapterItem = new DeviceAdapterItem(busName, deviceType, deviceNameWithOS[0], deviceNameWithOS[1]);
        add(deviceAdapterItem);
    }

    public void add(final DeviceAdapterItem device) {
        if (uiUpdater != null) {
            uiUpdater.updateUI(new Runnable() {
                @Override
                public void run() {
                    list.add(device);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void remove(final DeviceAdapterItem device) {
        if (uiUpdater != null) {
            uiUpdater.updateUI(new Runnable() {
                @Override
                public void run() {
                    if (list.contains(device)) {
                        list.remove(device);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void remove(final String busName) {
        if (uiUpdater != null) {
            uiUpdater.updateUI(new Runnable() {
                @Override
                public void run() {
                    Iterator<DeviceAdapterItem> it = list.iterator();
                    while (it.hasNext()) {
                        DeviceAdapterItem device = it.next();
                        if (device.getBusName().equals(busName)) {
                            it.remove();
                            notifyDataSetChanged();
                            break;
                        }
                    }
                }
            });
        }
    }

    public void setUiUpdater(UIUpdater uiUpdater) {
        this.uiUpdater = uiUpdater;
    }

    public void addAll(Set<DeviceAdapterItem> items) {
        for (DeviceAdapterItem item : items) {
            add(item);
        }
    }
}