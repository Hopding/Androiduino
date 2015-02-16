package ajd2.com.robotcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Set;

//Controller class receives the MAC address for the selected device from BtDevSelect activity
//and uses it as a parameter for a new instance of BluetoothConnect. The various method available
//in BluetoothConnect are then called from four buttons and a slider. The slider can be shown or
//hidden by pressing a button on the action bar.
public class Controller extends ActionBarActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter;
    BluetoothDevice robot;
    SeekBar speedBar;
    int speed = 256;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        Intent intent = getIntent();
        String message = intent.getStringExtra(BtDevSelect.EXTRA_MESSAGE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothConnect btConnect = new BluetoothConnect(robot = btAdapter.getRemoteDevice(message), btAdapter);
        textView = (TextView) findViewById(R.id.currentSpeed);
        speedBar = (SeekBar) findViewById(R.id.speed_bar);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress;
                textView.setText("Current Speed = " + String.valueOf(progress + 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                btConnect.changeSpeed(speed + 6);

            }
        });
        speedBar.setVisibility(View.GONE);

        // Getting the Bluetooth adapter

        btConnect.run();
        final Button forwardButton = (Button) findViewById(R.id.forward_button);
        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btConnect.forward();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btConnect.stopMoving();
                }
                return false;
            }
        });

        final Button reverseButton = (Button) findViewById(R.id.reverse_button);
        reverseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btConnect.reverse();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btConnect.stopMoving();
                }
                return false;
            }
        });

        final Button rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btConnect.right();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btConnect.stopMoving();
                }
                return false;
            }
        });

        final Button leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btConnect.left();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btConnect.stopMoving();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.controller_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_speed:
                if (speedBar.getVisibility() == View.GONE) {
                    speedBar.setVisibility(View.VISIBLE);
                } else {
                    speedBar.setVisibility(View.GONE);
                }
                return true;
            case R.id.action_settings:
                //IMPLEMENT SETTINGS
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

