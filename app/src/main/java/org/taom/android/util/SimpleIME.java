package org.taom.android.util;

import android.app.Activity;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.view.View;

import org.taom.android.R;
import org.taom.android.tabs.fragments.ControlsFragment;

public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private Handler handler;
    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    Activity host;

    public SimpleIME() {
    }

    public SimpleIME(Activity h, View kbview, Handler handler) {
        host = h;
        kv = (KeyboardView) kbview;
        keyboard = new Keyboard(h, R.xml.qwerty);
        this.handler = handler;
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    public void hideCustomKeyboard() {
        kv.setVisibility(View.GONE);
        kv.setEnabled(false);
    }

    public void showCustomKeyboard() {
        kv.setVisibility(View.VISIBLE);
        kv.setEnabled(true);
        //if( v!=null ) ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                break;
            case Keyboard.KEYCODE_DONE:
                break;
            case 20:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
        }
        handler.sendMessage(handler.obtainMessage(ControlsFragment.KEY_PRESSED, primaryCode, 0));
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
        hideCustomKeyboard();
        keyboard = new Keyboard(host, R.xml.numpad);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    @Override
    public void swipeRight() {
        hideCustomKeyboard();
        keyboard = new Keyboard(host, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        showCustomKeyboard();
    }

    @Override
    public void swipeUp() {
    }
}