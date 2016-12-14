package com.example.brent.launchertest;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ResourceBundle;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private View mContentView;
    private VideoView[] videoViews = new VideoView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int index = 1;
        videoViews[0] = (VideoView)findViewById(R.id.vidView1);
        videoViews[1] = (VideoView)findViewById(R.id.vidView2);
        videoViews[2] = (VideoView)findViewById(R.id.vidView3);
        videoViews[3] = (VideoView)findViewById(R.id.vidView4);
        videoViews[0].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video1);
        videoViews[1].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video2);
        videoViews[2].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video3);
        videoViews[3].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video4);



        for (VideoView vidView : videoViews) {
//            vidView = (VideoView) findViewById(getResources().getIdentifier("vidView" + index, "id", getPackageName()));
            vidView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    playVideo(v);
                    return true;
                }
            });
//            vidView.setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video1);
            vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            vidView.start();
//            index++;
        }
    }

    public void playVideo(View view) {
        Toast.makeText(FullscreenActivity.this, "Trying to play!", Toast.LENGTH_SHORT).show();
    }
}
