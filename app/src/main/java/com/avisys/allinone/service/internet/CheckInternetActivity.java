package com.avisys.allinone.service.internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.avisys.allinone.R;

public class CheckInternetActivity extends BaseActivity implements BaseActivity.CheckInternetConnectivityStatus {
    private String TAG = BaseActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet);
        BaseActivity.status = this;
        Log.e(TAG,"OnCreate__");
    }

    @Override
    public void isNetworkAvailable(boolean isConnected) {
        Toast.makeText(CheckInternetActivity.this, "isConnected : "+isConnected, Toast.LENGTH_SHORT).show();
    }
}