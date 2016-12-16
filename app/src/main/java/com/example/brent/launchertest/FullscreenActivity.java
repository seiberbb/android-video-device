package com.example.brent.launchertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private View mContentView;
    private VideoView[] videoViews = new VideoView[4];
    private File directory = null;
    public final static String EXTRA_MESSAGE = "videoPath";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        String path = Environment.getExternalStorageDirectory().toString()+"/Turner";
        if (!loadVideos("/mnt/extsd")) {
            if (!loadVideos("/sdcard")) {
                new AlertDialog.Builder(this)
                        .setMessage("No 'Turner' directory found on SD Card or device storage")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FullscreenActivity.this.finish();
                            }
                        })
                        .show();
            }
        }

/*

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

//            vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.setLooping(true);
//                }
//            });

            vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });

            vidView.start();

//            index++;
        }

*/
    }

    private boolean loadVideos(String path) {
        Log.i("Files", "Path: " + path);
        directory = new File(path + "/Turner");
        File[] files = directory.listFiles();
        if ((files == null) || (files.length == 0)) {
            return false;
        }
        Log.e("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.e("Files", "FileName:" + files[i].getName());
        }



        int index = 1;
        videoViews[0] = (VideoView)findViewById(R.id.vidView1);
        videoViews[1] = (VideoView)findViewById(R.id.vidView2);
        videoViews[2] = (VideoView)findViewById(R.id.vidView3);
        videoViews[3] = (VideoView)findViewById(R.id.vidView4);

//        videoViews[0].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video1);
//        videoViews[1].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video2);
//        videoViews[2].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video3);
//        videoViews[3].setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video4);

        videoViews[0].setVideoPath(directory.getAbsolutePath() + "/video1.mp4");
        videoViews[1].setVideoPath(directory.getAbsolutePath() + "/video2.mp4");
        videoViews[2].setVideoPath(directory.getAbsolutePath() + "/video3.mp4");
        videoViews[3].setVideoPath(directory.getAbsolutePath() + "/video4.mp4");


        for (final VideoView vidView : videoViews) {

            final int videoIndex = index;
            MediaController mediaController = new MediaController(this);
            mediaController.setVisibility(View.GONE);
            mediaController.setAnchorView(vidView);

// Init Video
            vidView.setMediaController(mediaController);

//            vidView = (VideoView) findViewById(getResources().getIdentifier("vidView" + index, "id", getPackageName()));
            vidView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("Event", "" +event.getAction());
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        Log.i("Video", "VidView touched");
                        playVideo(vidView, videoIndex);
                    }
                    return true;
                }
            });
//            vidView.setVideoPath("android.resource://com.example.brent.launchertest/" + R.raw.video1);

            vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i("preparing", "Set to looping");
                    mp.start();
                }
            });
            vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.i("Video","VidView completed.  Should be restarting");
                    try {
                        mp.seekTo(0);
                    } catch (Exception e) {
                        Log.e("mp", "error", e);
                    }
                }
            });

            vidView.start();

            index++;
        }
        return true;
    }


    public void playVideo(VideoView view, int videoIndex) {
        Toast.makeText(view.getContext(), "Trying to play!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VideoActivity.class);
        String videoPath = directory.getAbsolutePath() + "/video" + videoIndex + ".mp4";
        intent.putExtra(EXTRA_MESSAGE, videoPath);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        stopAllVideos();
        super.onPause();
    }

    @Override
    protected void onResume() {
        startAllVideos();
        super.onResume();
    }

    private void stopAllVideos() {
        for (final VideoView vidView : videoViews) {
            vidView.start();
        }
    }

    private void startAllVideos() {
        for (final VideoView vidView : videoViews) {
            vidView.stopPlayback();
        }
    }

}
