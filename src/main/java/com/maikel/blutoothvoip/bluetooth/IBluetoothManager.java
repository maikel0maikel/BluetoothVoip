package com.maikel.blutoothvoip.bluetooth;

/**
 * Created by maikel on 2018/3/25.
 */

public interface IBluetoothManager {

    void openBluetooth();

    void closeBluetooth();

    void startDiscovery();
}
