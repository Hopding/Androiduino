package ajd2.com.robotcontroller;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

import ajd2.com.robotcontroller.Controller;


//BtDevSelect Activity lists all of the devices that have been paired to your device
//Then displays a series of buttons with each devices name and MAC
//When a button is pressed an intent containing the MAC address listed on that button
//is send to the Controller Activity
public class BtDevSelect extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.ajd1.btdevscanselect";
    BluetoothAdapter btAdapter;
    TextView availableDevices;
    LinearLayout layout;
    BluetoothDevice[] btDevices;
    Set<BluetoothDevice> devices;
    String message;
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Controller.class);
            message = btDevices[v.getId()].toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_dev_select);
        layout = (LinearLayout) findViewById(R.id.availableDeviceButtons);
        availableDevices = (TextView) findViewById(R.id.availableDevices);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        devices = btAdapter.getBondedDevices();
        btDevices = new BluetoothDevice[devices.size()];
        devices.toArray(btDevices);
        for (int i = 0; i < devices.size(); i++) {
            Button button = new Button(this);
            button.setId(i);
            button.setText("Name: " + btDevices[i].getName() + '\n' + "MAC: " + btDevices[i]);
            button.setOnClickListener(clickListener);
            button.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            layout.addView(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bt_dev_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
