package com.avisys.allinone.service.wifimanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.avisys.allinone.R;

import java.util.List;

public class WifiServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private WifiManager wifiManager;
    private Switch aSwitch;
    private TextView d1,d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_service);
        init();
    }

    private void init() {
        d1 = findViewById(R.id.getWifiDevice);
        d2 = findViewById(R.id.getPairedWifiDevice);
        d1.setOnClickListener(this);
        d2.setOnClickListener(this);

        // initialize wifi service
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        aSwitch = findViewById(R.id.switch_on_off);
        int state = wifiManager.getWifiState();
        if (state == 0 || state == 1)
            aSwitch.setChecked(false);
        else if (state == 2 || state == 3)
            aSwitch.setChecked(true);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(compoundButton.getContext(), "Wifi is enabled", Toast.LENGTH_SHORT).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(compoundButton.getContext(), "Wifi is disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getWifiDevice(View view) {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {/**Service set identifier : SSID*/
            Toast.makeText(view.getContext(),
                    "SSID :" + scanResult.SSID + "\n" +
                            "Frequency " + scanResult.frequency, Toast.LENGTH_SHORT).show();
            Log.e("ScanResults ","SSID "+scanResult.SSID+" Frequency "+scanResult.frequency);
        }
    }

    private void getPairedWifiDevice(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<WifiConfiguration> config = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration:config){
            Toast.makeText(view.getContext(), "SSID : "+configuration.SSID+" \n"+
                    " Status :"+configuration.status, Toast.LENGTH_SHORT).show();
            Log.e("ScanResults ","SSID "+configuration.SSID+" Status "+configuration.status);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getWifiDevice:
                getWifiDevice(view);
                break;
            case R.id.getPairedWifiDevice:
                getPairedWifiDevice(view);
                break;
        }
    }
}