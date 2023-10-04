package com.example.stop_watch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btstart,btstop;

    private boolean isResume;
    Handler handler;
    long tMilliSec,tStart,tBuff,tUpdate =0L;
    int sec,min,hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer=findViewById(R.id.chronometer);
        btstart=findViewById(R.id.bt_start);
        btstop=findViewById(R.id.bt_stop);

        handler=new Handler();

        btstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    btstop.setVisibility(View.GONE);
                    btstart.setImageDrawable(getResources().getDrawable(R.drawable.pause_icon));
                }else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    btstop.setVisibility(View.VISIBLE);
                    btstart.setImageDrawable(getResources().getDrawable(R.drawable.play_icon));
                }
            }
        });
        btstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    btstart.setImageDrawable(getResources().getDrawable(R.drawable.play_icon));
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    hr=0;
                    sec = 0;
                    min = 0;

                    chronometer.setText("00.00.00");
                }
            }
        });
    }
    public Runnable runnable=new Runnable() {
        @Override
        public void run() {
            tMilliSec= SystemClock.uptimeMillis() -tStart;
            tUpdate=tBuff+tMilliSec;
            sec=(int) (tUpdate/1000);
            hr=min/60;
            min=sec/60;
            sec=sec%60;

            chronometer.setText(String.format("%02d",hr)+":"+String.format("%02d",min)+":"+String.format("%02d",sec)+":" );
            handler.postDelayed(this,60);
        }
    };
}