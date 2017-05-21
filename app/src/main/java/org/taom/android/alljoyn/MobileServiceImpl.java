package org.taom.android.alljoyn;

import android.os.Build;
import android.os.Environment;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.taom.izconnect.network.interfaces.MobileInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MobileServiceImpl implements BusObject, MobileInterface {
    private Set<String> subscribers;
    private Map<String, FileOutputStream> incomingFiles = new ConcurrentHashMap<>();

    public MobileServiceImpl(Set<String> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public String getDeviceName() throws BusException {
        return Build.MODEL;
    }

    @Override
    public String getDeviceOS() throws BusException {
        return "Android " + Build.VERSION.RELEASE;
    }

    @Override
    public void subscribe(String busName) throws BusException {
        subscribers.add(busName);
        System.out.println(busName);
    }

    @Override
    public void notify(String devicename, String sender, String notification) throws BusException {

    }

    @Override
    public void unsubscribe(String busName) throws BusException {
        subscribers.remove(busName);
        System.out.println(busName);

    }

    @Override
    public void fileData(String filename, byte[] data) throws BusException {
        if (data.length == 0) {
            FileOutputStream out = incomingFiles.get(filename);
            if (out != null) {
                incomingFiles.remove(filename);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            File root = new File(Environment.getExternalStorageDirectory(), "izconnect");
            if(!root.exists()) {
                root.mkdirs();
            }
            File received = new File(root, filename);

            FileOutputStream out = incomingFiles.get(filename);
            if (out == null) {
                if (received.exists()) {
                    received.delete();
                }
                try {
                    out = new FileOutputStream(received, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                incomingFiles.put(filename, out);
            }
            try {
                out.write(data);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
