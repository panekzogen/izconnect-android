package org.taom.izconnect.network.interfaces;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.annotation.BusProperty;

@BusInterface(name = PCInterface.INTERFACE_NAME, announced = "true")
public interface PCInterface extends DeviceInfoInterface {

    String INTERFACE_NAME = "org.taom.izconnect.network.PCInterface";

    @BusMethod
    void subscribe(String busName) throws BusException;

    @BusMethod
    void notify(String devicename, String sender, String notification) throws BusException;

    @BusMethod
    void unsubscribe(String busName) throws BusException;

    @BusProperty
    void setVolume(int level) throws BusException;

    @BusProperty
    int getVolume() throws BusException;

    @BusMethod
    void mediaControlPlayPause() throws BusException;

    @BusMethod
    void mediaControlStop() throws BusException;

    @BusMethod
    void mediaControlNext() throws BusException;

    @BusMethod
    void mediaControlPrevious() throws BusException;

    @BusMethod
    void mouseMove(int x, int y) throws BusException;

    @BusMethod
    void mouseLeftClick() throws BusException;

    @BusMethod
    void mouseRightClick() throws BusException;

    @BusMethod
    void keyPressed(int code) throws BusException;

    @BusMethod
    void slideshowStart() throws BusException;

    @BusMethod
    void slideshowStop() throws BusException;

    @BusMethod
    void nextSlide() throws BusException;

    @BusMethod
    void previousSlide() throws BusException;

    @BusMethod
    void fileData(String filename, byte[] data, boolean isScript) throws BusException;

    @BusMethod
    void runScript(String scriptName) throws BusException;

}
