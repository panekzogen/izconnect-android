package org.taom.android;

import android.app.Application;
import android.content.Intent;

public class IZConnectApplication extends Application {
    private Intent service;

    @Override
    public void onCreate() {
    }

    public Intent getService() {
        return service;
    }

    public void setService(Intent service) {
        this.service = service;
    }
}
