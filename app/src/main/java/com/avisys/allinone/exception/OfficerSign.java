package com.avisys.allinone.exception;

import android.util.Log;

public class OfficerSign extends Thread{

    @Override
    public void run() {
        try {
            Log.e("OfficerSign","Officer Sign started");
            sleep(3000);
            Log.e("OfficerSign","Officer Sign Completed");
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
