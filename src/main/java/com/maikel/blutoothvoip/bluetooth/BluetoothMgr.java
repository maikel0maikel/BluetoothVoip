package com.maikel.blutoothvoip.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;

import com.maikel.blutoothvoip.BluetoothApplication;
import com.maikel.blutoothvoip.bluetooth.observ.BluetoothSubjectManager;
import com.maikel.blutoothvoip.bluetooth.task.BluetoothTask;
import com.maikel.blutoothvoip.constant.Constants;
import com.maikel.blutoothvoip.receiver.BluetoothReceiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothMgr implements IBluetoothManager {

    BluetoothAdapter bluetoothManager;
    private BluetoothReceiver bluetoothReceiver;
    private BluetoothDevice device;

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
        } else {
            BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_ON);
        }
    }

    @Override
    public void closeBluetooth() {
        disableBluetooth();
        unRegistBluetoothReceiver();
    }

    @Override
    public void startDiscovery() {
        bluetoothManager.startDiscovery();
    }

    @Override
    public void connectDevice(String address) {
        device = bluetoothManager.getRemoteDevice(address);
        if (device == null) {
            return;
        }
        int boundState = device.getBondState();
        switch (boundState) {
            case BluetoothDevice.BOND_BONDED:
                realConnectDevice();
                break;
            case BluetoothDevice.BOND_BONDING:
                break;
            case BluetoothDevice.BOND_NONE:
                createBond();
                break;
        }
    }

    @Override
    public void startConnecting() {
        realConnectDevice();
    }

    private void createBond() {
        Class clz = BluetoothDevice.class;
        try {
            Method m = clz.getDeclaredMethod("createBond");
            m.invoke(device);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void removeBond() {

    }

    private void setPin() {

    }

    private BluetoothSocket createBluetoothSocket() {
        return null;
    }

    private void cancelPairingUserInput() {

    }

    private boolean disableBluetooth() {
        if (bluetoothManager == null) {
            return true;
        }
        if (bluetoothManager.isEnabled()) {
            bluetoothManager.disable();
        } else {
            BluetoothSubjectManager.getInstance().notifyOpenState(Constants.BTState.STATE_OFF);
        }
        return false;
    }

    private void unRegistBluetoothReceiver() {
        if (bluetoothReceiver != null) {
            BluetoothApplication.getContextInstance().unregisterReceiver(bluetoothReceiver);
        }
    }


    private void realConnectDevice() {
        synchronized (this) {
            bluetoothManager.cancelDiscovery();
            BluetoothTask.getTask().connectDevice(device);
        }
    }

}

