package org.taom.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;

import org.taom.android.alljoyn.AllJoynService;
import org.taom.android.tabs.fragments.ControlsFragment;

public class TouchpadActivity extends Activity {
    private int x, y;
    private Intent service;
    private Handler serviceHandler;

    private boolean mBound = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touchpad);

        service = ((IZConnectApplication)getApplication()).getService();

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

            final View touchView = findViewById(R.id.touchView);
            touchView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        x = (int) event.getX();
                        y = (int) event.getY();
                    } else {
                        serviceHandler.sendMessage(serviceHandler.obtainMessage(ControlsFragment.MOUSE_MOVE, (int) event.getX() - x, (int) event.getY() - y));
                        x = (int) event.getX();
                        y = (int) event.getY();
                    }
                    return true;
                }
            });

            final View leftButton = findViewById(R.id.left_click);
            leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceHandler.sendMessage(serviceHandler.obtainMessage(ControlsFragment.MOUSE_LEFT_CLICK));
                }
            });

            final View rightButton = findViewById(R.id.right_click);
            rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    serviceHandler.sendMessage(serviceHandler.obtainMessage(ControlsFragment.MOUSE_RIGHT_CLICK));
                }
            });

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