/*
 * Copyright AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package org.taom.izconnect.network;

import org.alljoyn.bus.AboutDataListener;
import org.alljoyn.bus.ErrorReplyBusException;
import org.alljoyn.bus.Variant;
import org.alljoyn.bus.Version;

import java.util.HashMap;
import java.util.Map;

/**
 * A sample class providing about data.
 */
public abstract class AbstractAboutData implements AboutDataListener {
    @Override
    public Map<String, Variant> getAboutData(String language)
            throws ErrorReplyBusException {
        Map<String, Variant> aboutData = new HashMap<>();
        aboutData.put("AppId", new Variant(getAppId()));
        aboutData.put("DefaultLanguage", new Variant(getDefaultLanguage()));
        aboutData.put("DeviceName", new Variant(getDeviceName()));
        aboutData.put("DeviceId", new Variant(getDeviceId()));
        aboutData.put("AppName", new Variant(getAppName()));
        aboutData.put("Manufacturer", new Variant(getManufacturer()));
        aboutData.put("ModelNumber", new Variant(getModelNumber()));
        aboutData.put("DateOfManufacture", new Variant(getDateOfManufacture()));
        aboutData.put("SupportedLanguages", new Variant(getSupportedLanguages()));
        aboutData.put("SoftwareVersion", new Variant(getSoftwareVersion()));
        aboutData.put("AJSoftwareVersion", new Variant(Version.get()));
        aboutData.put("HardwareVersion", new Variant(getHardwareVersion()));
        aboutData.put("SupportUrl", new Variant(getSupportUrl()));
        aboutData.put("Description", new Variant(getDescription()));
        return aboutData;
    }

    @Override
    public Map<String, Variant> getAnnouncedAboutData()
            throws ErrorReplyBusException {
        Map<String, Variant> aboutData = new HashMap<>();
        aboutData.put("AppId", new Variant(getAppId()));
        aboutData.put("DefaultLanguage", new Variant(getDefaultLanguage()));
        aboutData.put("DeviceName", new Variant(getDeviceName()));
        aboutData.put("DeviceId", new Variant(getDeviceId()));
        aboutData.put("AppName", new Variant(getAppName()));
        aboutData.put("Manufacturer", new Variant(getManufacturer()));
        aboutData.put("ModelNumber", new Variant(getModelNumber()));
        return aboutData;
    }

    protected byte[] getAppId() {
        return new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    }

    protected String getDefaultLanguage() {
        return "en";
    }

    protected String getDeviceName() {
        return "A device name";
    }

    protected String getDeviceId() {
        return "93c06771-c725-48c2-b1ff-6a2a59d445b8";
    }

    protected String getAppName() {
        return "An application name";
    }

    protected String getManufacturer() {
        return "A mighty manufacturing company";
    }

    protected String getModelNumber() {
        return "A1B2C3";
    }

    protected String getDateOfManufacture() {
        return "2014-09-23";
    }

    protected String[] getSupportedLanguages() {
        return new String[] {"en"};
    }

    protected String getSoftwareVersion() {
        return "1.0";
    }

    protected String getHardwareVersion() {
        return "0.1alpha";
    }

    protected String getSupportUrl() {
        return "http://www.example.com/support";
    }

    protected String getDescription() {
        return "Sample showing the about feature in a service application";
    }
}
