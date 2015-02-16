package ajd2.com.robotcontroller;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//Robot class sends bytes to the Arduino. 1-5 are used to control direction and
//stopping. 6-201 are used to control speed.
public class Robot extends Thread {
    final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream output;

    public Robot(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        output = tmpOut;
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            output.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }

    public void forward() {
        try {
            output.write((byte) 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reverse() {
        try {
            output.write((byte) 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void right() {
        try {
            output.write((byte) 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void left() {
        try {
            output.write((byte) 4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMoving() {
        try {
            output.write((byte) 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSpeed(int speed) {
        try {
            output.write((byte) speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
