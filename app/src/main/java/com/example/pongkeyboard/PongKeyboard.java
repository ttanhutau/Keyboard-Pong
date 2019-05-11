package com.example.pongkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class PongKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private  boolean isCaps = false;


    //Press Ctrl+O


    @Override
    public View onCreateInputView() {
        //   kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        // keyboard = new Keyboard(this,R.xml.qwerty);
        // kv.setKeyboard(keyboard);
        // kv.setOnKeyboardActionListener(this);
        InputConnection ic = getCurrentInputConnection();

        PongView hello = new PongView(this, ic);



        return hello ;
    }




    @Override
    public void onPress(int i) {

    }


    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection ic = getCurrentInputConnection();
        ic.commitText(String.valueOf("e"),1);

        playClick(i);
        switch (i)
        {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)i;
                if(Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);
        }

    }



    private void playClick(int i) {

        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(i)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
