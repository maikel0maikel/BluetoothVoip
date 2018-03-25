package com.maikel.blutoothvoip.bluetooth.observ;

import java.util.ArrayList;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothSubject implements IBluetoothSubject {

    private ArrayList<BluetoothObserver> observers = new ArrayList<>();

    protected BluetoothSubject(){}
    @Override
    public void attchBluetoothObserver(BluetoothObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detachBluetoothObserver(BluetoothObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyOpenState(int openedState) {
        for (BluetoothObserver observer:observers){
            observer.notifyOpenState(openedState);
        }
    }

    @Override
    public void notifyBoundState(int boundState) {
        for (BluetoothObserver observer:observers){
            observer.notifyBoundState(boundState);
        }
    }

    @Override
    public void notifyConnectedState(int connectedState) {
        for (BluetoothObserver observer:observers){
            observer.notifyConnectedState(connectedState);
        }
    }

    @Override
    public void notifyDeviceFound(String name, String address) {
        for (BluetoothObserver observer:observers){
            observer.notifyDeviceFound(name,address);
        }
    }

    @Override
    public void notifyScanStarted() {
        for (BluetoothObserver observer:observers){
            observer.notifyScanStarted();
        }
    }

    @Override
    public void notifyScanFinished() {
        for (BluetoothObserver observer:observers){
            observer.notifyScanFinished();
        }
    }
}
