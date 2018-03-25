package com.maikel.blutoothvoip.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;

import com.maikel.blutoothvoip.BluetoothApplication;
import com.maikel.blutoothvoip.receiver.BluetoothReceiver;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothMgr implements IBluetoothManager {

    BluetoothAdapter bluetoothManager;
    private BluetoothReceiver bluetoothReceiver;

    public BluetoothMgr() {
        initBluetoothReceiver();
    }

    private void initBluetoothReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        bluetoothReceiver = new BluetoothReceiver();
        BluetoothApplication.getContextInstance().registerReceiver(bluetoothReceiver, intentFilter);
    }


    @Override
    public void openBluetooth() {

        bluetoothManager = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothManager == null) {
            return;
        }
        if (!bluetoothManager.isEnabled()) {
            bluetoothManager.enable();
        }
    }

    @Override
    public void closeBluetooth() {
        if (bluetoothManager == null) {
            return;
        }
        if (bluetoothManager.isEnabled()) {
            bluetoothManager.disable();
        }
    }
}
