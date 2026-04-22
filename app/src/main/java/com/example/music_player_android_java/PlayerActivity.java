package com.example.music_player_android_java;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_player_android_java.manager.MusicManager;

public class PlayerActivity extends AppCompatActivity {

    TextView tvSongTitle, tvArtistName;
    Button btnPlayPause;
    SeekBar seekBar;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        tvSongTitle = findViewById(R.id.tvSongTitle);
        tvArtistName = findViewById(R.id.tvArtistName);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBar);

        tvSongTitle.setText(MusicManager.currentTitle);
        tvArtistName.setText(MusicManager.currentArtist);

        seekBar.setMax(MusicManager.getDuration());

        updateSeekBar();

        btnPlayPause.setOnClickListener(v -> {

            MusicManager.toggle();

            if (MusicManager.isPlaying()) {
                btnPlayPause.setText("Pause");
            } else {
                btnPlayPause.setText("Play");
            }
        });
    }

    private void updateSeekBar() {

        seekBar.setProgress(
                MusicManager.getCurrentPosition()
        );

        handler.postDelayed(this::updateSeekBar, 500);
    }
}