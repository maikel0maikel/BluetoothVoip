package com.maikel.blutoothvoip.dialer;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.EditText;

import com.maikel.blutoothvoip.BluetoothApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by maikel on 2018/3/25.
 */

public class DialerControll implements IDialerController.Controller {
    private IDialerController.View mView;
    private ToneGenerator mToneGenerator;
    private Object mToneGeneratorLock = new Object();

    public DialerControll(IDialerController.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void disableSoftInput() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1) {
            mView.getInputEditText().setInputType(InputType.TYPE_NULL);
        } else {
            Method m;
            Class<EditText> editTextClass = EditText.class;

            try {
                m = editTextClass.getMethod("setShowSoftInputOnFocus", boolean.class);
                m.setAccessible(true);
                m.invoke(mView.getInputEditText(), false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void dial(String number) {

    }

    @Override
    public void genarateTone() {
        if (mToneGenerator == null) {
            synchronized (mToneGeneratorLock) {
                mToneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);
                mView.setVolumControllerStream(AudioManager.STREAM_MUSIC);
            }
        }
    }

    @Override
    public void releaseTone() {
        synchronized (mToneGeneratorLock) {
            mToneGenerator.release();
            mToneGenerator = null;
        }
    }

    @Override
    public void playTone(int tone) {
        AudioManager audioManager = (AudioManager) BluetoothApplication.getContextInstance().getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if ((ringerMode == AudioManager.RINGER_MODE_SILENT) || (ringerMode == AudioManager.RINGER_MODE_VIBRATE)) {
            return;
        }
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                return;
            }
            mToneGenerator.startTone(tone, 100);
        }
    }

    @Override
    public void sendKeyEvent(int keyCode) {
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        mView.dispatchEvent(event, keyCode);
    }
}
