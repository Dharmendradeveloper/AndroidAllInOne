package com.avisys.allinone.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.avisys.allinone.R;

public class LifeCycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        getLifecycle().addObserver(new MusicPlayer());

    }
}