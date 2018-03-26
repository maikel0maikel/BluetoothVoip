package com.maikel.blutoothvoip.bluetooth.task;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by maikel on 2018/3/26.
 */

public class BluetoothTask {

    private static volatile BluetoothTask task = new BluetoothTask();
    private volatile boolean startConnect = false;

    private BluetoothTask() {
    }

    public static BluetoothTask getTask() {
        return task;
    }

    public void connectDevice(BluetoothDevice device) {
        synchronized (this) {
            if (!startConnect) {
                startConnect = true;
                FutureTask futureTask = new FutureTask(new ConnectThread(device));
                new Thread(futureTask).start();
                try {
                    startConnect = (boolean) futureTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class ConnectThread implements Callable<Boolean> {
        BluetoothDevice device;
        private static final String BLUETOOTH_UUID = "00001101-0000-1000-8000-00805F9B34FB";

        ConnectThread(BluetoothDevice device) {
            this.device = device;
        }

        @Override
        public Boolean call() throws Exception {
            BluetoothSocket btSocket = null;
            UUID uuid = UUID.fromString(BLUETOOTH_UUID);
            try {
                if (Build.VERSION.SDK_INT >= 10) {
                    btSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
                } else {
                    btSocket = device.createRfcommSocketToServiceRecord(uuid);
                }
                btSocket.connect();
                return true;
            } catch (IOException e) {
                if (btSocket != null) {
                    btSocket.close();
                }
                Thread.sleep(1000);
                connect2();
            }
            return false;
        }

        private boolean connect2() {
            Method m;
            BluetoothSocket btSocket;
            try {
                if (Build.VERSION.SDK_INT >= 10) {
                    m = device.getClass().getMethod("createInsecureRfcommSocket", new Class[]{int.class});
                } else {
                    m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                }
                btSocket = (BluetoothSocket) m.invoke(device, 1);
                btSocket.connect();
                return true;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return false;
        }
    }


}
