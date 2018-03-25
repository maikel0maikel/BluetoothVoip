package com.maikel.blutoothvoip.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.bean.BTDevice;
import com.maikel.blutoothvoip.bluetooth.BluetoothMgr;
import com.maikel.blutoothvoip.constant.Constants;
import com.maikel.blutoothvoip.setting.ISettingController;
import com.maikel.blutoothvoip.setting.SettingControl;
import com.maikel.blutoothvoip.ui.adapter.BluetoothScanAdapter;
import com.maikel.blutoothvoip.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maikel on 2018/3/24.
 */

public class SettingFragment extends Fragment implements ISettingController.SettingView {
    private View mRootView;
    private ISettingController.SettingController mController;
    private SwitchButton mSwitchButton;
    private ProgressBar mProgressBar;
    private Activity mActivity;

    private SwitchButton.OnSwitchChangeListener changeListener = new SwitchButton.OnSwitchChangeListener() {
        @Override
        public void onSwitchChange(String name, boolean switched) {
            if (switched) {
                setmProgressBarVisible(View.VISIBLE);
                setmSwitchButtonState(View.GONE);
                mController.start();
            } else {
                setmProgressBarVisible(View.GONE);
                setmSwitchButtonState(View.VISIBLE);
                mController.end();
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_setting, container, false);
            RecyclerView recyclerView = mRootView.findViewById(R.id.bluetooth_device_rv);
            mSwitchButton = mRootView.findViewById(R.id.connect_device_switch);
            mProgressBar = mRootView.findViewById(R.id.connect_device_progress);
            mSwitchButton.setSwitchChangeListener(changeListener);
            List<BTDevice> devices = new ArrayList<>();
            for (int i=0;i<10;i++){
                BTDevice btDevice = new BTDevice("i="+i,"12-bc-io-"+i);
                devices.add(btDevice);
            }
            BluetoothScanAdapter adapter = new BluetoothScanAdapter(mActivity,devices);
            recyclerView.setAdapter(adapter);
            new SettingControl(this);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mController = (ISettingController.SettingController) presenter;
    }

    private void setmProgressBarVisible(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    private void setmSwitchButtonState(int visibility) {
        mSwitchButton.setVisibility(visibility);
    }

    @Override
    public void notifyOpenState(int openedState) {
        int btState = openedState;
        switch (btState) {
            case Constants.BTState.STATE_TURNING_ON:
                break;
            case Constants.BTState.STATE_ON:
                break;
            case Constants.BTState.STATE_TURNING_OFF:
                break;
            case Constants.BTState.STATE_OFF:
                break;
        }
    }

    @Override
    public void notifyBoundState(int boundState) {

    }

    @Override
    public void notifyConnectedState(int connectedState) {

    }

    @Override
    public void notifyScanStarted() {

    }

    @Override
    public void notifyScanFinished() {

    }
}
