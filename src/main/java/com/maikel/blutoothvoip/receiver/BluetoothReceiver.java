package com.maikel.blutoothvoip.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.maikel.blutoothvoip.bluetooth.observ.BluetoothSubjectManager;
import com.maikel.blutoothvoip.constant.Constants;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothReceiver extends BroadcastReceiver {
    private static final String TAG = "BluetoothReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e(TAG, "action=" + TAG);
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_TURNING_ON);
                    break;
                case BluetoothAdapter.STATE_ON:
                    BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_ON);
                    break;
                case BluetoothAdapter.STATE_CONNECTING:
                    BluetoothSubjectManager.getInstance().notifyConnectedState(Constants.BTState.STATE_CONNECTING);
                    break;
                case BluetoothAdapter.STATE_CONNECTED:
                    BluetoothSubjectManager.getInstance().notifyConnectedState(Constants.BTState.STATE_CONNECTED);
                    break;
                case BluetoothAdapter.STATE_DISCONNECTING:
                    BluetoothSubjectManager.getInstance().notifyConnectedState(Constants.BTState.STATE_DISCONNECTING);
                    break;
                case BluetoothAdapter.STATE_DISCONNECTED:
                    BluetoothSubjectManager.getInstance().notifyConnectedState(Constants.BTState.STATE_DISCONNECTED);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_TURNING_OFF);
                    break;
                case BluetoothAdapter.STATE_OFF:
                    BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_OFF);
                    break;
            }
        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            String deviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (bluetoothDevice != null) {
                BluetoothSubjectManager.getInstance().notifyDeviceFound(bluetoothDevice.getName() == null ?
                        deviceName : bluetoothDevice.getName(), bluetoothDevice.getAddress());
            }
        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
            int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
            switch (state) {
                case BluetoothDevice.BOND_BONDING:
                    BluetoothSubjectManager.getInstance().notifyBoundState(Constants.BTState.BOND_BONDING);
                    break;
                case BluetoothDevice.BOND_BONDED:
                    BluetoothSubjectManager.getInstance().notifyBoundState(Constants.BTState.BOND_BONDED);
                    break;
                case BluetoothDevice.BOND_NONE:
                    BluetoothSubjectManager.getInstance().notifyBoundState(Constants.BTState.BOND_NONE);
                    break;
            }
        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            BluetoothSubjectManager.getInstance().notifyScanStarted();

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            BluetoothSubjectManager.getInstance().notifyScanFinished();
        }
    }
}
