package com.example.brent.launchertest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

//TODO: Disable sound on thumbnails
//TODO: Add text to screen under thumbnails
//TODO: Intro vid
//
public class FullscreenActivity extends AppCompatActivity {
    private View mContentView;
    private VideoView[] videoViews = new VideoView[4];
    private TextView[] textViews = new TextView[4];
    private File directory = null;
    public final static String EXTRA_MESSAGE = "videoPath";
    private boolean videosLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mContentView = findViewById(R.id.imageView);
//        mContentView.setSystemUiVisibility(
//                  View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        if (!loadVideos(Environment.getExternalStorageDirectory().toString())) {
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
        File topLevel = new File(path);
        File[] topFiles = topLevel.listFiles();

        if ((topFiles == null) || (topFiles.length == 0)) {
            return false;
        }
        Log.e("TopFiles", "Size: "+ topFiles.length);
        for (int i = 0; i < topFiles.length; i++)
        {
            Log.e("TopFiles", "FileName:" + topFiles[i].getName());
        }





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
                    mp.setVolume(0f,0f);
                    mp.start();
                }
            });
            vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.i("Video","VidView completed.  Should be restarting");
                    try {
//                        if (!mp.isPlaying()) {
                            mp.seekTo(0);
                            mp.start();
//                        } else {
//                            Log.w("Video", videoIndex + "completed but still playing!");
//                        }
                    } catch (Exception e) {
                        Log.e("mp", "error", e);
                    }
                }
            });

            vidView.start();

            index++;
        }
        videosLoaded = true;
        loadText(path);
        return true;
    }

    @Override
    public void onBackPressed() {

    }

    private boolean loadText(String path) {

          Log.i("Text Files", "Path: " + path);
          File topLevel = new File(path);
          int index = 1;
          textViews[0] = (TextView) findViewById(R.id.textView1);
          textViews[1] = (TextView) findViewById(R.id.textView2);
          textViews[2] = (TextView) findViewById(R.id.textView3);
          textViews[3] = (TextView) findViewById(R.id.textView4);

          for (final TextView textView : textViews) {


              //Get the text file
              File file = new File(path + "/Turner", "video" + index + ".txt");

              //Read text from file
              StringBuilder text = new StringBuilder();

              try {
                  BufferedReader br = new BufferedReader(new FileReader(file));
                  String line;

                  text.append(br.readLine());
                  br.close();
              } catch (IOException e) {
                  //You'll need to add proper error handling here
                  Log.e("TextFiles", "CANT LOAD!");
                  return false;
              }

              //Set the text
              textView.setText(text.toString());
              index++;
          }
        return true;
      }


    public void playVideo(VideoView view, int videoIndex) {
//        Toast.makeText(view.getContext(), "Trying to play!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VideoActivity.class);
        String videoPath = directory.getAbsolutePath() + "/video" + videoIndex + ".mp4";
        intent.putExtra(EXTRA_MESSAGE, videoPath);
        Log.i("Activity", "Leaving");
        startActivity(intent);
        Log.i("Activity", "Returned");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle", "Pause!");
        stopAllVideos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "Resume!");
        startAllVideos();
    }

//    @Override
//    protected void onStop() {
//        Log.i("Lifecycle", "Stop!");
//        stopAllVideos();
//        super.onStop();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.i("Lifecycle", "Restart!");
//        startAllVideos();
//        super.onRestart();
//    }
//
//    @Override
//    protected void onStart() {
//        Log.i("Lifecycle", "Start!");
//        super.onStart();
//    }

    private void stopAllVideos() {
        for (final VideoView vidView : videoViews) {
            if (videosLoaded && (vidView!=null) && vidView.isPlaying()) {
                Log.i("Lifecycle", "already playing, pausing");
                vidView.pause();
            }
        }
    }

    private void startAllVideos() {
        for (final VideoView vidView : videoViews) {
            if (videosLoaded && (vidView!=null) && !vidView.isPlaying()) {
                Log.i("Lifecycle", "not already playing, resuming");
                vidView.resume();
            }
        }
    }

}
