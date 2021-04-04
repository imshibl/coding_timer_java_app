package com.example.codingtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerView;
    SeekBar timerSeekBar;
    Button startStopButton;
    CountDownTimer countDownTimer;
    Boolean timerIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.seekBar);
        timerView = findViewById(R.id.timer);
        startStopButton = findViewById(R.id.button);

        timerSeekBar.setMax(1800);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onButtonClicked(View view) {
        Log.d("time", "btn pressed");

        if (timerIsRunning) {
            resetTimer();

        } else {
            startStopButton.setText("STOP!");
            timerIsRunning = true;
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);


                    timerSeekBar.setEnabled(false);

                }

                @Override
                public void onFinish() {
                    Log.d("timer", "finished");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    timerSeekBar.setEnabled(true);
                    resetTimer();

                }
            }.start();
        }
    }

    public void updateTimer(int i){
        int minutes = i / 60;
        int seconds = i - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9){
            secondString = "0" + secondString;
        }
        timerView.setText(Integer.toString(minutes) + ":" + secondString);

    }

    public void resetTimer(){
        countDownTimer.cancel();
        startStopButton.setText("GO!");
        timerIsRunning = false;
        timerSeekBar.setEnabled(true);
        timerView.setText("0:30");


    }


}
