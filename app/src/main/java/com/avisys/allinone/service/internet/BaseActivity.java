package com.avisys.allinone.service.internet;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.avisys.allinone.R;

public class BaseActivity extends AppCompatActivity {
    private Dialog dialog;
    public static CheckInternetConnectivityStatus status;
    private IntentFilter filter;
    private MyBroadCastReceiver receiver;
    private String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Log.e(TAG, "OnCreate" + status);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* create intent filter **/
        Log.e(TAG, "OnResume" + status);
        if (filter == null && receiver == null) {
            filter = new IntentFilter();
            receiver = new MyBroadCastReceiver();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            Log.e(TAG, "OnResume filter  " + filter);
        }
        registerReceiver(receiver, filter);
    }

    private void getStatus(IntentFilter intentFilter) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);

    }

    /**
     * Create an inner class
     * which extends BroadCastReceiver
     * and after that we can override a @method onReceive()
     */
    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive");
            if (status != null) {
                boolean isConnected = isConnectedOrConnecting(context);
                if (isConnected) {
                    if (dialog != null)
                        dialog.dismiss();
                } else
                    showDialog();
                status.isNetworkAvailable(isConnected);
            }
        }

        private boolean isConnectedOrConnecting(Context context) {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.
                    CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo!=null&& netInfo.isConnectedOrConnecting();
        }
    }

    // interface
    interface CheckInternetConnectivityStatus{
        void isNetworkAvailable(boolean isConnected);
    }

    private void showDialog(){
        if (dialog==null){
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_for_no_internet);
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.drawable.background_dialog);
        }
        dialog.show();
    }
}
