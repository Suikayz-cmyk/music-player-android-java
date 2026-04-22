package com.example.music_player_android_java;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_player_android_java.manager.MusicManager;

public class PlayerActivity extends AppCompatActivity {

    TextView tvSongTitle;
    TextView tvArtistName;
    TextView tvTime;

    Button btnPlayPause;
    SeekBar seekBar;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvSongTitle = findViewById(R.id.tvSongTitle);
        tvArtistName = findViewById(R.id.tvArtistName);
        tvTime = findViewById(R.id.tvTime);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBar);

        tvSongTitle.setText(MusicManager.currentTitle);
        tvArtistName.setText(MusicManager.currentArtist);

        seekBar.setMax(MusicManager.getDuration());

        updateSeekBar();

        updatePlayButton();

        btnPlayPause.setOnClickListener(v -> {

            MusicManager.toggle();
            updatePlayButton();
        });

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress,
                                                  boolean fromUser) {

                        if (fromUser) {
                            MusicManager.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
    }

    private void updateSeekBar() {

        int current = MusicManager.getCurrentPosition();
        int total = MusicManager.getDuration();

        seekBar.setProgress(current);

        tvTime.setText(
                formatTime(current) + " / " + formatTime(total)
        );

        if (MusicManager.mediaPlayer != null &&
                current >= total - 500) {

            seekBar.setProgress(0);

            tvTime.setText(
                    "00:00 / " + formatTime(total)
            );

            updatePlayButton();
            return;
        }

        handler.postDelayed(this::updateSeekBar, 500);
    }

    private void updatePlayButton() {

        if (MusicManager.isPlaying()) {
            btnPlayPause.setText("Pause");
        } else {
            btnPlayPause.setText("Play");
        }
    }

    private String formatTime(int ms) {

        int seconds = ms / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}