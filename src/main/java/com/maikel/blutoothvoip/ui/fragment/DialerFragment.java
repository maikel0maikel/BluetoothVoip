package com.maikel.blutoothvoip.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.dialer.DialerControll;
import com.maikel.blutoothvoip.dialer.IDialerController;

/**
 * Created by maikel on 2018/3/22.
 */

public class DialerFragment extends Fragment implements IDialerController.View, View.OnClickListener, View.OnLongClickListener {
    View mRootView;
    private static final int[] NUMBER_BTNS = {R.id.num_zero_iv, R.id.num_one_iv, R.id.num_two_iv, R.id.num_three_iv, R.id.num_four_iv, R.id.num_five_iv, R.id.num_six_iv,
            R.id.num_seven_iv, R.id.num_eight_iv, R.id.num_nine_iv, R.id.num_star_iv, R.id.num_poud_iv, R.id.contact_add_iv, R.id.dial_iv, R.id.delete_iv,};

    private Activity mActivity;
    private IDialerController.Controller mController;
    private EditText phoneNumber;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_dialer, container, false);
            ViewStub viewStub = mRootView.findViewById(R.id.dial_pad_stub);

            View padView = viewStub.inflate();
            phoneNumber = padView.findViewById(R.id.input_num_et);
            setupBtn();
            new DialerControll(this).disableSoftInput();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mController.genarateTone();
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.releaseTone();
    }

    private void setupBtn() {
        for (int id : NUMBER_BTNS) {
            View v = mRootView.findViewById(id);
            v.setOnClickListener(this);
            if (id == R.id.delete_iv || id == R.id.num_zero_iv) {
                v.setOnLongClickListener(this);
            }
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mController = (IDialerController.Controller) presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num_zero_iv:
                mController.playTone(0);
                mController.sendKeyEvent(KeyEvent.KEYCODE_0);
                break;
            case R.id.num_one_iv:
                mController.playTone(1);
                mController.sendKeyEvent(KeyEvent.KEYCODE_1);
                break;
            case R.id.num_two_iv:
                mController.playTone(2);
                mController.sendKeyEvent(KeyEvent.KEYCODE_2);
                break;
            case R.id.num_three_iv:
                mController.playTone(3);
                mController.sendKeyEvent(KeyEvent.KEYCODE_3);
                break;
            case R.id.num_four_iv:
                mController.playTone(4);
                mController.sendKeyEvent(KeyEvent.KEYCODE_4);
                break;
            case R.id.num_five_iv:
                mController.playTone(5);
                mController.sendKeyEvent(KeyEvent.KEYCODE_5);
                break;
            case R.id.num_six_iv:
                mController.playTone(6);
                mController.sendKeyEvent(KeyEvent.KEYCODE_6);
                break;
            case R.id.num_seven_iv:
                mController.playTone(7);
                mController.sendKeyEvent(KeyEvent.KEYCODE_7);
                break;
            case R.id.num_eight_iv:
                mController.playTone(8);
                mController.sendKeyEvent(KeyEvent.KEYCODE_8);
                break;
            case R.id.num_nine_iv:
                mController.playTone(9);
                mController.sendKeyEvent(KeyEvent.KEYCODE_9);
                break;
            case R.id.num_star_iv:
                mController.playTone(10);
                mController.sendKeyEvent(KeyEvent.KEYCODE_STAR);
                break;
            case R.id.num_poud_iv:
                mController.playTone(11);
                mController.sendKeyEvent(KeyEvent.KEYCODE_POUND);
                break;
            case R.id.contact_add_iv:
                break;
            case R.id.dial_iv:
                break;
            case R.id.delete_iv:
                mController.sendKeyEvent(KeyEvent.KEYCODE_DEL);
                break;
        }
    }

    @Override
    public void setVolumControllerStream(int streamType) {
        if (mActivity != null) {
            mActivity.setVolumeControlStream(streamType);
        }
    }

    @Override
    public void dispatchEvent(KeyEvent event, int keyCode) {
        if (phoneNumber != null) {
            phoneNumber.onKeyDown(keyCode, event);
        }
    }

    @Override
    public EditText getInputEditText() {
        return phoneNumber == null ? (EditText) mRootView.findViewById(R.id.input_num_et) : phoneNumber;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.num_zero_iv:
                mController.sendKeyEvent(KeyEvent.KEYCODE_PLUS);
                break;
            case R.id.delete_iv:
                phoneNumber.getText().clear();
                break;

        }
        return true;
    }
}
