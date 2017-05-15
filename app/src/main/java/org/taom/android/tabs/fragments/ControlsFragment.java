package org.taom.android.tabs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import org.taom.android.KeyboardActivity;
import org.taom.android.MainActivity;
import org.taom.android.R;
import org.taom.android.TouchpadActivity;
import org.taom.android.devices.DeviceAdapter;
import org.taom.android.devices.DeviceAdapterItem;

import java.io.Serializable;

public class ControlsFragment extends Fragment {
    private DeviceAdapter deviceAdapter;
    private Handler handler;

    public static final int VOLUME_CHANGED = 101;
    public static final int MEDIA_PLAY_BUTTON = 102;
    public static final int MEDIA_STOP_BUTTON = 103;
    public static final int MEDIA_NEXT_BUTTON = 104;
    public static final int MEDIA_PREVIOUS_BUTTON = 105;
    public static final int MOUSE_MOVE = 106;
    public static final int MOUSE_LEFT_CLICK = 107;
    public static final int MOUSE_RIGHT_CLICK = 108;
    public static final int KEY_PRESSED = 109;
    public static final int SLIDESHOW_START = 110;
    public static final int SLIDESHOW_STOP = 111;
    public static final int NEXT_SLIDE = 112;
    public static final int PREV_SLIDE = 113;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DeviceAdapterItem selectedItem = deviceAdapter == null ? null : deviceAdapter.getSelectedItem();
        View rootView;
        if (selectedItem == null) {
            rootView = inflater.inflate(R.layout.controls_main, container, false);
        } else {
            switch (selectedItem.getDeviceType()) {
                case BOARD:
                    rootView = inflater.inflate(R.layout.board_controls, container, false);
                    bindBoardView(rootView);
                    break;
                case MOBILE:
                    rootView = inflater.inflate(R.layout.mobile_controls, container, false);
                    bindMobileView(rootView);
                    break;
                case PC:
                    rootView = inflater.inflate(R.layout.pc_controls, container, false);
                    bindPCView(rootView);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.controls_main, container, false);
                    break;
            }
        }

        System.out.println("Im controls fragment create");

        return rootView;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setDeviceAdapter(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }

    private void bindBoardView(View rootView) {

    }

    private void bindMobileView(View rootView) {

    }

    private void bindPCView(View rootView) {
        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.volumeSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                handler.sendMessage(handler.obtainMessage(VOLUME_CHANGED, progress, 0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ImageButton playPauseButton = (ImageButton) rootView.findViewById(R.id.playButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(MEDIA_PLAY_BUTTON));
            }
        });

        ImageButton stopButton = (ImageButton) rootView.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(MEDIA_STOP_BUTTON));
            }
        });

        ImageButton nextButton = (ImageButton) rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(MEDIA_NEXT_BUTTON));
            }
        });

        ImageButton previousButton = (ImageButton) rootView.findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(MEDIA_PREVIOUS_BUTTON));
            }
        });

        Button mouseControl = (Button) rootView.findViewById(R.id.mouseControl);
        mouseControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TouchpadActivity.class);
                startActivity(intent);
            }
        });

        Button keyboardControl = (Button) rootView.findViewById(R.id.keyboardControl);
        keyboardControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KeyboardActivity.class);
                startActivity(intent);
            }
        });

        ImageButton slideshowStart = (ImageButton) rootView.findViewById(R.id.startSlideShow);
        slideshowStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(SLIDESHOW_START));
            }
        });

        ImageButton slideshowStop = (ImageButton) rootView.findViewById(R.id.stopSlideShow);
        slideshowStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(SLIDESHOW_STOP));
            }
        });

        final ImageButton nextSlide = (ImageButton) rootView.findViewById(R.id.nextSlide);
        nextSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(NEXT_SLIDE));
            }
        });

        ImageButton prevSlide = (ImageButton) rootView.findViewById(R.id.prevSlide);
        prevSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendMessage(handler.obtainMessage(PREV_SLIDE));
            }
        });
    }
}
