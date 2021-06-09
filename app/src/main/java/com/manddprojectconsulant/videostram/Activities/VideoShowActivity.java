package com.manddprojectconsulant.videostram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.manddprojectconsulant.videostram.Fragments.UploadFragment;
import com.manddprojectconsulant.videostram.R;
import com.manddprojectconsulant.videostram.databinding.ActivityVideoShowBinding;

public class VideoShowActivity extends AppCompatActivity {

    ActivityVideoShowBinding videoShowBinding;
    int videoshow = 0;
    public static final int PERMISSION_READ = 0;
    double current_pos, duration;
    boolean isVisible = true;
    UploadFragment uploadFragment=new UploadFragment();
    Handler mHandler,handler;

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoShowBinding = ActivityVideoShowBinding.inflate(getLayoutInflater());
        setContentView(videoShowBinding.getRoot());


        videoshow = getIntent().getExtras().getInt("getpostion", 0);
        title=getIntent().getExtras().getString("getTitle");




         setVideo();



         videoShowBinding.fullscreen.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {






             }
         });

         videoShowBinding.shareButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 sharevideo();
                 
                 
             }
         });



         videoShowBinding.uploadButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 getSupportFragmentManager().beginTransaction().replace(R.id.contraint,uploadFragment).commit();

             }
         });



    }

    private void sharevideo() {

        Uri uri1= DashboardActivity.list.get(videoshow).getVideouri();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("video/*");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, "VideoStram");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Data");
        sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, uri1);
        startActivity(Intent.createChooser(sharingIntent, "Share to"));


    }

    private void setVideo() {
        mHandler = new Handler();
        handler = new Handler();
        videoShowBinding.videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoshow++;
                if (videoshow < (DashboardActivity.list.size())) {
                    playVideo(videoshow);
                } else {
                    videoshow = 0;
                    playVideo(videoshow);
                }

            }
        });


        videoShowBinding.titleT.setText(title);


        videoShowBinding.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                setVideoProgress();

            }
        });

        playVideo(videoshow);
        prevVideo();
        nextVideo();
        setPause();
        hideout();


    }

    private void hideout() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                videoShowBinding.showProgress.setVisibility(View.GONE);
                isVisible = false;
            }
        };
        handler.postDelayed(runnable, 5000);

        videoShowBinding.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                if (isVisible) {
                    videoShowBinding.showProgress.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    videoShowBinding.showProgress.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(runnable, 5000);
                    isVisible = true;
                }
            }
        });

    }

    private void setPause() {

       videoShowBinding.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoShowBinding.videoview.isPlaying()) {
                    videoShowBinding.videoview.pause();
                    videoShowBinding.pause.setImageResource(R.drawable.ic_play_button);

                } else {
                    videoShowBinding.videoview.start();
                   videoShowBinding.pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });


    }

    private void nextVideo() {

        videoShowBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoshow < (DashboardActivity.list.size()-1)) {
                    videoshow++;
                    playVideo(videoshow);
                } else {
                    videoshow = 0;
                    playVideo(videoshow);
                }
            }
        });

    }

    private void prevVideo() {

        videoShowBinding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoshow > 0) {
                    videoshow--;
                    playVideo(videoshow);
                } else {
                    videoshow =DashboardActivity.list.size() - 1;
                    playVideo(videoshow);
                }
            }
        });


    }

    private void setVideoProgress() {

        current_pos = videoShowBinding.videoview.getCurrentPosition();
        duration = videoShowBinding.videoview.getDuration();

        //display duration
        videoShowBinding.total.setText(timeConversion((long) duration));
        videoShowBinding.current.setText(timeConversion((long) current_pos));
        videoShowBinding.seekbar.setMax((int) duration);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = videoShowBinding.videoview.getCurrentPosition();
                    videoShowBinding.current.setText(timeConversion((long) current_pos));
                    videoShowBinding.seekbar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        //seekbar change listner
        videoShowBinding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                videoShowBinding.videoview.seekTo((int) current_pos);
            }
        });
    }

    private String timeConversion(long value) {

        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;

    }

    private void playVideo(int videoshow) {

        try {
            videoShowBinding.videoview.setVideoURI(DashboardActivity.list.get(videoshow).getVideouri());
            videoShowBinding.videoview.start();
            videoShowBinding.pause.setImageResource(R.drawable.ic_pause);
            this.videoshow = videoshow;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        setVideo();
                    }
                }
            }
        }
    }

}