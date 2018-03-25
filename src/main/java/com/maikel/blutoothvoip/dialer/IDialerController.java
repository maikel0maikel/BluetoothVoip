package com.maikel.blutoothvoip.dialer;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.BaseView;

/**
 * Created by maikel on 2018/3/22.
 */

public interface IDialerController {

    public interface View extends BaseView{

        void setVolumControllerStream(int streamType);

        void dispatchEvent(KeyEvent event,int keyCode);

        EditText getInputEditText();
    }


    public interface Controller extends BasePresenter{

        void disableSoftInput();

        void dial(String number);

        void genarateTone();

        void releaseTone();

        void playTone(int tone);

        void sendKeyEvent(int keyCode);
    }

}
