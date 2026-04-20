package com.example.music_player_android_java;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity {

    TextView tvSongTitle, tvArtistName;
    Button btnPlayPause;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvSongTitle = findViewById(R.id.tvSongTitle);
        tvArtistName = findViewById(R.id.tvArtistName);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBar);

        String title = getIntent().getStringExtra("title");
        String artist = getIntent().getStringExtra("artist");
        int audioRes = getIntent().getIntExtra("audio", 0);

        tvSongTitle.setText(title);
        tvArtistName.setText(artist);

        mediaPlayer = MediaPlayer.create(this, audioRes);
        mediaPlayer.start();

        btnPlayPause.setOnClickListener(v -> {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setText("Play");
            } else {
                mediaPlayer.start();
                btnPlayPause.setText("Pause");
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());

        updateSeekBar();
    }

    private void updateSeekBar() {

        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable runnable = this::updateSeekBar;
            handler.postDelayed(runnable, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}