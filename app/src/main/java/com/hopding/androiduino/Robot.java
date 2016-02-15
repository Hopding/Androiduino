package com.hopding.androiduino;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Robot class sends bytes to the Arduino. 1-5 are used to control direction and
 * stopping. 6-201 are used to control speed.
 */
public class Robot {
    private BluetoothSocket btSocket;
    private InputStream btInStream;
    private OutputStream btOutStream;

    public Robot() {
    }

    public void setSocket(BluetoothSocket socket) {
        btSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and btOutStream streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        btInStream = tmpIn;
        btOutStream = tmpOut;
    }

    public void write(byte[] bytes) {
        try {
            btOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    public void cancel() {
        try {
            btSocket.close();
        } catch (IOException e) {
        }
    }

    public void forward() {
        try {
            btOutStream.write((byte) 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reverse() {
        try {
            btOutStream.write((byte) 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void right() {
        try {
            btOutStream.write((byte) 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void left() {
        try {
            btOutStream.write((byte) 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMoving() {
        try {
            btOutStream.write((byte) 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSpeed(int speed) {
        try {
            btOutStream.write((byte) speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
