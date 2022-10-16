package com.avisys.allinone.service.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avisys.allinone.R;
import com.avisys.allinone.service.model.MediaPlayerMusic;

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mMediaPlayer;
    private TextView playMusic,stopMusic;
    /**
     * Normal flow of Media Player works perfectly under @method onPause and @method onStop
     * If the music is playing it will keep on playing until application gets
     * destroyed from the background
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        init();
    }

    private void init(){
//         mMediaPlayer = MediaPlayer.create(this,R.raw.barish_bn_jana);
         playMusic = findViewById(R.id.play);
         stopMusic = findViewById(R.id.stop);
         playMusic.setOnClickListener(this);
         stopMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
//                mMediaPlayer.start();/* mMediaPlayer.start() method is allowed to playMusic */
                playMusic();
                break;
            case R.id.stop:
//                mMediaPlayer.stop();/* mMediaPlayer.stop() method is allowed to stop music */
                stopMusic();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MediaPlayer","OnPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MediaPlayer","OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MediaPlayer","OnDestroy");
    }

    private void playMusic(){
        Intent i = new Intent();
        i.setComponent(new ComponentName(this, MediaPlayerMusic.class));
        startService(i);
    }

    private void stopMusic(){
        Intent i = new Intent();
        i.setComponent(new ComponentName(this,MediaPlayerMusic.class));
        stopService(i);
    }
}