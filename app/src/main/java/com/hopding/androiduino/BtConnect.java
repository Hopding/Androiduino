package com.hopding.androiduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * BTConnect's run() method establishes a connection with a BluetoothDevice using the specified
 * BluetoothAdapter, then connects it with the specified Robot instance.
 */
public class BtConnect extends Thread {
    private static final UUID SP_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private final BluetoothSocket btSocket;
    private BluetoothDevice btDevice;
    private BluetoothAdapter btAdapter;
    private OutputStream outStream;
    private Robot robot;

    public BtConnect(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, Robot robot) {
        // Use a temporary object that is later assigned to btSocket,
        // because btSocket is final
        BluetoothSocket tmp = null;
        btDevice = device;
        btAdapter = bluetoothAdapter;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // SP_UUID is the app's SP_UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(SP_UUID);
        } catch (IOException e) {
        }
        btSocket = tmp;

        this.robot = robot;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        btAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            btSocket.connect();
            outStream = btSocket.getOutputStream();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                btSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }
        robot.setSocket(btSocket);

    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) {
        }
    }

}
