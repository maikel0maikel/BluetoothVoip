package com.maikel.blutoothvoip.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.bean.BTDevice;
import com.maikel.blutoothvoip.constant.Constants;
import com.maikel.blutoothvoip.setting.ISettingController;
import com.maikel.blutoothvoip.setting.SettingControl;
import com.maikel.blutoothvoip.ui.adapter.BluetoothScanAdapter;
import com.maikel.blutoothvoip.widget.DividerItemDecoration;
import com.maikel.blutoothvoip.widget.SwitchButton;


/**
 * Created by maikel on 2018/3/24.
 */

public class SettingFragment extends Fragment implements ISettingController.SettingView {
    private View mRootView;
    private ISettingController.SettingController mController;
    private SwitchButton mSwitchButton;
    private ProgressBar mProgressBar;
    private Activity mActivity;
    private TextView connect_device_tv;
    BluetoothScanAdapter adapter;
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

    private BluetoothScanAdapter.OnItemClickListener onItemClickListener = new BluetoothScanAdapter.OnItemClickListener() {
        @Override
        public void itemClickListener(BTDevice btDevice) {
            mController.connectDevice(btDevice.getBtAddress());
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
            connect_device_tv = (TextView) mRootView.findViewById(R.id.connect_device_tv);
            RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.bluetooth_device_rv);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            //设置布局管理器
            recyclerView.setLayoutManager(layoutManager);
            //设置为垂直布局，这也是默认的
            layoutManager.setOrientation(OrientationHelper.VERTICAL);
            //设置增加或删除条目的动画
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,DividerItemDecoration.VERTICAL_LIST));
            mSwitchButton = (SwitchButton) mRootView.findViewById(R.id.connect_device_switch);
            mProgressBar = (ProgressBar) mRootView.findViewById(R.id.connect_device_progress);
            mSwitchButton.setSwitchChangeListener(changeListener);
            adapter = new BluetoothScanAdapter(mActivity);
            adapter.setOnItemClickListener(onItemClickListener);
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
                connect_device_tv.setText(R.string.label_bt_openning);
                break;
            case Constants.BTState.STATE_ON:
                connect_device_tv.setText(R.string.lable_bt_opened);
                break;
            case Constants.BTState.STATE_TURNING_OFF:
                connect_device_tv.setText(R.string.lable_bt_closing);
                break;
            case Constants.BTState.STATE_OFF:
                connect_device_tv.setText(R.string.lable_bt_closed);
                break;
        }
    }

    @Override
    public void notifyBoundState(int boundState) {
        int btState = boundState;
        switch (btState) {
            case Constants.BTState.BOND_BONDING:
                connect_device_tv.setText(R.string.label_bt_bounding);
                break;
            case Constants.BTState.BOND_BONDED:
                connect_device_tv.setText(R.string.lable_bt_bounded);
                break;
            case Constants.BTState.BOND_NONE:
                connect_device_tv.setText(R.string.label_bt_boundnone);
                break;
        }
    }

    @Override
    public void notifyConnectedState(int connectedState) {
        int btState = connectedState;
        switch (btState) {
            case Constants.BTState.STATE_TURNING_ON:
                connect_device_tv.setText(R.string.label_bt_openning);
                break;
            case Constants.BTState.STATE_ON:
                connect_device_tv.setText(R.string.lable_bt_opened);
                break;
            case Constants.BTState.STATE_TURNING_OFF:
                connect_device_tv.setText(R.string.lable_bt_closing);
                break;
            case Constants.BTState.STATE_OFF:
                connect_device_tv.setText(R.string.lable_bt_closed);
                break;
        }
    }

    @Override
    public void notifyScanStarted() {
        connect_device_tv.setText(R.string.label_bt_scan);
    }

    @Override
    public void notifyScanFinished() {
        connect_device_tv.setText(R.string.label_bt_scaned);
        setmProgressBarVisible(View.GONE);
        setmSwitchButtonState(View.VISIBLE);
    }

    @Override
    public void notifyDeviceFound(BTDevice btDevice) {
        adapter.addDevice(btDevice);
    }
}
