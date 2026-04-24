package com.example.music_player_android_java;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_player_android_java.manager.MusicManager;

public class PlayerActivity extends AppCompatActivity {

    // Song info
    private TextView tvSongTitle;
    private TextView tvArtistName;
    private TextView tvTime;

    // Cover image
    private ImageView imgDetailCover;
    private ImageView imgBgCover;

    // Controls
    private Button btnPlayPause;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private ImageButton btnBack;

    private SeekBar seekBar;

    // UI updater
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable seekRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            handler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initViews();
        setupSongData();
        setupButtons();
        setupSeekBar();

        refreshUI();
        handler.post(seekRunnable);
    }

    private void initViews() {
        tvSongTitle = findViewById(R.id.tvSongTitle);
        tvArtistName = findViewById(R.id.tvArtistName);
        tvTime = findViewById(R.id.tvTime);

        imgDetailCover = findViewById(R.id.imgDetailCover);
        imgBgCover = findViewById(R.id.imgBgCover);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnBack = findViewById(R.id.btnBack);

        seekBar = findViewById(R.id.seekBar);
    }

    private void setupSongData() {
        imgDetailCover.setImageResource(MusicManager.currentImageRes);
        imgBgCover.setImageResource(MusicManager.currentImageRes);

        tvSongTitle.setText(MusicManager.currentTitle);
        tvArtistName.setText(MusicManager.currentArtist);
    }

    private void setupButtons() {

        btnBack.setOnClickListener(v -> finish());

        btnPlayPause.setOnClickListener(v -> {
            MusicManager.toggle();
            updatePlayButton();
        });

        btnNext.setOnClickListener(v -> {
            MusicManager.next(this);
            refreshUI();
        });

        btnPrev.setOnClickListener(v -> {
            MusicManager.prev(this);
            refreshUI();
        });
    }

    private void setupSeekBar() {

        seekBar.setMax(MusicManager.getDuration());

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser
                    ) {
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
                }
        );
    }

    private void refreshUI() {
        tvSongTitle.setText(MusicManager.currentTitle);
        tvArtistName.setText(MusicManager.currentArtist);

        imgDetailCover.setImageResource(MusicManager.currentImageRes);
        imgBgCover.setImageResource(MusicManager.currentImageRes);

        seekBar.setMax(MusicManager.getDuration());

        updatePlayButton();
        updateSeekBar();
    }

    private void updatePlayButton() {
        if (MusicManager.isPlaying()) {
            btnPlayPause.setText("Pause");
        } else {
            btnPlayPause.setText("Play");
        }
    }

    private void updateSeekBar() {

        int current = MusicManager.getCurrentPosition();
        int total = MusicManager.getDuration();

        seekBar.setProgress(current);

        tvTime.setText(
                formatTime(current) + " / " + formatTime(total)
        );

        if (total > 0 && current >= total - 500) {
            seekBar.setProgress(0);
            updatePlayButton();
        }
    }

    private String formatTime(int ms) {

        int seconds = ms / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(seekRunnable);
    }
}