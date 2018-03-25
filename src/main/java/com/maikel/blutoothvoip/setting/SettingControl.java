package com.maikel.blutoothvoip.setting;

import com.maikel.blutoothvoip.bluetooth.BluetoothMgr;
import com.maikel.blutoothvoip.bluetooth.IBluetoothManager;

/**
 * Created by maikel on 2018/3/25.
 */

public class SettingControl implements ISettingController.SettingController {

    private ISettingController.SettingView mView;

    private IBluetoothManager bluetoothManager;

    public SettingControl(ISettingController.SettingView v) {
        mView = v;
        mView.setPresenter(this);
        bluetoothManager = new BluetoothMgr();
    }

    @Override
    public void start() {
        bluetoothManager.openBluetooth();
    }
}
