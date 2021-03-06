package com.maikel.blutoothvoip.setting;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.BaseView;
import com.maikel.blutoothvoip.bean.BTDevice;
import com.maikel.blutoothvoip.bluetooth.observ.BluetoothObserver;

/**
 * Created by maikel on 2018/3/25.
 */

public interface ISettingController {

    public interface SettingView extends BaseView{
        void notifyOpenState(int openedState);
        void notifyBoundState(int boundState);
        void notifyConnectedState(int connectedState);
        void notifyScanStarted();
        void notifyScanFinished();
        void notifyDeviceFound(BTDevice btDevice);
    }

    public interface SettingController extends BasePresenter,BluetoothObserver{

        void start();

        void connectDevice(String address);

        void end();


    }

}
