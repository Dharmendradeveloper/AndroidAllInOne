package com.avisys.allinone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import com.avisys.allinone.ontouchcollission.TouchGameView;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TouchGameView(this));
        String url = BuildConfig.BASE_URL;
       /** Thread thread = Thread.currentThread();
        System.out.println("current thread "+thread);
        Medical medical = new Medical();
        medical.start();
        try {
            medical.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        TestDrive td = new TestDrive();
        try {
            td.start();
            td.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        OfficerSign os = new OfficerSign();
        os.start();
        try {
            os.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }*/
    }
}