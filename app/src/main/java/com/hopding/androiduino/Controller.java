package com.hopding.androiduino;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Controller class receives the MAC address for the selected device from BtDevSelect activity
 * and connects it with a Robot instance using BtConnect. The Buttons on the GUI send bytes with
 * Robot when pressed by the user. The slider adjusts the Robots speed - it can be shown or hidden
 * by pressing a button on the action bar.
 */
public class Controller extends ActionBarActivity {
    private BluetoothAdapter btAdapter;
    private Robot robot;
    private SeekBar speedBar;
    private int speed = 256;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
////////////////////////////////////////Set up GUI//////////////////////////////////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
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
                robot.changeSpeed(speed + 6);
            }
        });
        speedBar.setVisibility(View.GONE);

        final Button forwardButton = (Button) findViewById(R.id.forward_button);
        forwardButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    robot.forward();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    robot.stopMoving();
                }
                return false;
            }
        });

        final Button reverseButton = (Button) findViewById(R.id.reverse_button);
        reverseButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    robot.reverse();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    robot.stopMoving();
                }
                return false;
            }
        });

        final Button rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    robot.right();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    robot.stopMoving();
                }
                return false;
            }
        });

        final Button leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    robot.left();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    robot.stopMoving();
                }
                return false;
            }
        });
////////////////////////////////////////End of GUI Setup////////////////////////////////////////////


        //Use BtConnect to connect with the bluetooth device chosen by user in BtDevSelect Activity,
        //and connect it with robot.
        String message = getIntent().getStringExtra(BtDevSelect.EXTRA_MESSAGE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        new BtConnect(btAdapter.getRemoteDevice(message), btAdapter, robot);
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

