package com.maikel.blutoothvoip.setting;

import com.maikel.blutoothvoip.bean.BTDevice;
import com.maikel.blutoothvoip.bluetooth.BluetoothMgr;
import com.maikel.blutoothvoip.bluetooth.IBluetoothManager;
import com.maikel.blutoothvoip.bluetooth.observ.BluetoothSubjectManager;
import com.maikel.blutoothvoip.constant.Constants;

/**
 * Created by maikel on 2018/3/25.
 */

public class SettingControl implements ISettingController.SettingController {

    private ISettingController.SettingView mView;

    private IBluetoothManager bluetoothManager;

    public SettingControl(ISettingController.SettingView v) {
        BluetoothSubjectManager.getInstance().attchBluetoothObserver(this);
        mView = v;
        mView.setPresenter(this);
        bluetoothManager = new BluetoothMgr();
    }

    @Override
    public void start() {
        bluetoothManager.openBluetooth();
    }

    @Override
    public void end() {
        bluetoothManager.closeBluetooth();
    }

    @Override
    public void notifyOpenState(int openedState) {
        int state = openedState;
        switch (state){
            case Constants.BTState.STATE_ON:
                bluetoothManager.startDiscovery();
                break;
        }
        mView.notifyOpenState(state);
    }

    @Override
    public void notifyBoundState(int boundState) {
        int state = boundState;
        mView.notifyBoundState(state);
    }

    @Override
    public void notifyConnectedState(int connectedState) {
        int state = connectedState;
        mView.notifyBoundState(state);
    }

    @Override
    public void notifyDeviceFound(String name, String address) {
        BTDevice btDevice = new BTDevice(name,address);
        mView.notifyDeviceFound(btDevice);
    }

    @Override
    public void notifyScanStarted() {
        mView.notifyScanStarted();
    }

    @Override
    public void notifyScanFinished() {
        mView.notifyScanFinished();
    }
}
