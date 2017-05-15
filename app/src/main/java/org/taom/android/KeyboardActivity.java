package org.taom.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import org.taom.android.alljoyn.AllJoynService;
import org.taom.android.util.SimpleIME;

public class KeyboardActivity extends Activity {
    private Intent service;
    private Handler serviceHandler;

    private boolean mBound = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ((IZConnectApplication) getApplication()).getService();
        setContentView(R.layout.keyboard);

        setResult(Activity.RESULT_OK, null);

    }

    @Override
    protected void onStart() {
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        super.onStop();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AllJoynService.AllJoynBinder binder = ((AllJoynService.AllJoynBinder) service);
            serviceHandler = binder.getHandler();

            SimpleIME ss = new SimpleIME(KeyboardActivity.this, findViewById(R.id.keyboard), serviceHandler);

            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}