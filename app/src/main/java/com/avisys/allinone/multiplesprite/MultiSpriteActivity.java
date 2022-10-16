package com.avisys.allinone.multiplesprite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MultiSpriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MultipleGameView(this));
    }
}