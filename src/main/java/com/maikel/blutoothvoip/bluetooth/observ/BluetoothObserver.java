package com.maikel.blutoothvoip.bluetooth.observ;

/**
 * Created by maikel on 2018/3/25.
 */

public interface BluetoothObserver {
    void notifyOpenState(int openedState);
    void notifyBoundState(int boundState);
    void notifyConnectedState(int connectedState);
    void notifyDeviceFound(String name,String address);
    void notifyScanStarted();
    void notifyScanFinished();
}
