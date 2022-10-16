package com.avisys.allinone.exception;

import android.util.Log;

public class Medical extends Thread{

    @Override
    public void run() {

        try {
            Log.e("Medical","Medical started");
            sleep(3000);
            Log.e("Medical","Medical completed");
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
