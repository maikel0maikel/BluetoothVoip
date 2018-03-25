package com.maikel.blutoothvoip.bluetooth.observ;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothSubjectManager {
    IBluetoothSubject subject;
    private BluetoothSubjectManager() {
        subject = new BluetoothSubject();
    }

    private static class BluetoothSubjectInstance {
        private static final BluetoothSubjectManager INSTANCE = new BluetoothSubjectManager();
    }


    public static final BluetoothSubjectManager getInstance() {
        return BluetoothSubjectInstance.INSTANCE;
    }

    public void attchBluetoothObserver(BluetoothObserver observer){
        subject.attchBluetoothObserver(observer);
    }
    public void detachBluetoothObserver(BluetoothObserver observer){
        subject.detachBluetoothObserver(observer);
    }
    public void notifyOpenState(int openedState){
        subject.notifyOpenState(openedState);
    }
    public void notifyBoundState(int boundState){
        subject.notifyBoundState(boundState);
    }
    public void notifyConnectedState(int connectedState){
        subject.notifyConnectedState(connectedState);
    }
    public void notifyDeviceFound(String name,String address){
        subject.notifyDeviceFound(name,address);
    }
    public void notifyScanStarted(){
        subject.notifyScanStarted();
    }
    public void notifyScanFinished(){
        subject.notifyScanFinished();
    }

}
