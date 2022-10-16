package com.avisys.allinone.service.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.avisys.allinone.R;

public class BlueToothActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView b1,b2;
    private Switch aSwitch;
    private BluetoothAdapter bAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        init();
    }

    private void init(){
        bAdapter  = BluetoothAdapter.getDefaultAdapter();
        b1 = findViewById(R.id.getBluetoothDevice);
        b2 = findViewById(R.id.getPairedBluetoothDevice);
        aSwitch = findViewById(R.id.bluetooth_on_off);
        // set OnClick
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        aSwitch.setChecked(bAdapter.isEnabled());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    bAdapter.enable();
                else
                    bAdapter.disable();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getBluetoothDevice:
                getBluetoothDevice();
                break;
            case R.id.getPairedBluetoothDevice:
                getVibrate();
                break;
        }
    }//onClick closed

    private void getBluetoothDevice(){
        bAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(new MyBroadcastReceiver(),filter);
    }

    private void getVibrate(){
        Vibrator vibrate = (Vibrator) getApplicationContext().getSystemService(Context.
                VIBRATOR_SERVICE);
        vibrate.vibrate(10000);
    }

    // creating Inner class
    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        }
    }
}