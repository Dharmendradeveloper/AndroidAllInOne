package com.avisys.allinone.exception;

import android.util.Log;

public class TestDrive extends Thread{

    @Override
    public void run() {
        try {
            Log.e("TestDrive","TestDrive started");
            sleep(3000);
            Log.e("TestDrive","TestDrive Completed");
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
