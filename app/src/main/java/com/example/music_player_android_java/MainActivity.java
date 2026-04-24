package com.example.music_player_android_java;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.music_player_android_java.Fragments.AccountFragment;
import com.example.music_player_android_java.Fragments.FavoriteFragment;
import com.example.music_player_android_java.Fragments.HomeFragment;
import com.example.music_player_android_java.manager.MusicManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    LinearLayout miniPlayerLayout;

    TextView tvMiniTitle;
    TextView tvMiniArtist;
    TextView tvMiniTime;
    ImageView imgMiniCover;

    ImageButton btnMiniPlayPause;

    SeekBar miniSeekBar;

    Handler handler = new Handler();

    ImageButton btnMiniPrev, btnMiniNext;

    String currentTitle = "";
    String currentArtist = "";
    int currentAudioRes = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupMiniSeekBar();
        setupMiniPlayerClick();
        setupMiniPlayPauseButton();
        setupBottomNavigation();
        imgMiniCover = findViewById(R.id.imgMiniCover);

        loadFragment(new HomeFragment());
    }
    private void initViews() {

        imgMiniCover = findViewById(R.id.imgMiniCover);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        miniPlayerLayout = findViewById(R.id.miniPlayerLayout);

        tvMiniTitle = findViewById(R.id.tvMiniTitle);
        tvMiniArtist = findViewById(R.id.tvMiniArtist);
        tvMiniTime = findViewById(R.id.tvMiniTime);

        btnMiniPlayPause = findViewById(R.id.btnMiniPlayPause);
        btnMiniPrev = findViewById(R.id.btnMiniPrev);
        btnMiniNext = findViewById(R.id.btnMiniNext);

        btnMiniPrev.setOnClickListener(v -> {
            MusicManager.prev(this);
            refreshMiniPlayer();
        });

        btnMiniNext.setOnClickListener(v -> {
            MusicManager.next(this);
            refreshMiniPlayer();
        });

        miniSeekBar = findViewById(R.id.miniSeekBar);
    }

    private void setupBottomNavigation() {

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                loadFragment(new HomeFragment());

            } else if (item.getItemId() == R.id.nav_favorite) {
                loadFragment(new FavoriteFragment());

            } else {
                loadFragment(new AccountFragment());
            }

            return true;
        });
    }

    private void setupMiniPlayerClick() {

        miniPlayerLayout.setOnClickListener(v -> {

            Intent intent = new Intent(this, PlayerActivity.class);

            intent.putExtra("title", currentTitle);
            intent.putExtra("artist", currentArtist);
            intent.putExtra("audio", currentAudioRes);

            startActivity(intent);
        });
    }

    private void setupMiniPlayPauseButton() {

        btnMiniPlayPause.setOnClickListener(v -> {

            MusicManager.toggle();

            if (MusicManager.isPlaying()) {
                btnMiniPlayPause.setImageResource(
                        android.R.drawable.ic_media_pause
                );
            } else {
                btnMiniPlayPause.setImageResource(
                        android.R.drawable.ic_media_play
                );
            }

            refreshCurrentFragment();
        });
    }

    private void setupMiniSeekBar() {

        miniSeekBar.setOnSeekBarChangeListener(
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

    public void playSong(String title,
                         String artist,
                         int audioResId,
                         int imageResId) {

        boolean sameSong =
                MusicManager.currentAudioRes == audioResId;

        if (sameSong && MusicManager.mediaPlayer != null) {

            MusicManager.toggle();

        } else {

            MusicManager.play(
                    this,
                    title,
                    artist,
                    audioResId,
                    imageResId
            );
        }

        currentTitle = title;
        currentArtist = artist;
        currentAudioRes = audioResId;

        tvMiniTitle.setText(title);
        tvMiniArtist.setText(artist);
        imgMiniCover.setImageResource(imageResId);

        miniPlayerLayout.setVisibility(LinearLayout.VISIBLE);

        if (MusicManager.isPlaying()) {
            btnMiniPlayPause.setImageResource(
                    android.R.drawable.ic_media_pause
            );
        } else {
            btnMiniPlayPause.setImageResource(
                    android.R.drawable.ic_media_play
            );
        }

        miniSeekBar.setMax(MusicManager.getDuration());

        updateMiniSeekBar();
    }

    private void updateMiniSeekBar() {

        int current = MusicManager.getCurrentPosition();
        int total = MusicManager.getDuration();

        miniSeekBar.setProgress(current);

        tvMiniTime.setText(
                formatTime(current) + " / " + formatTime(total)
        );

        if (MusicManager.mediaPlayer != null &&
                current >= total - 500) {

            btnMiniPlayPause.setImageResource(
                    android.R.drawable.ic_media_play
            );

            miniSeekBar.setProgress(0);

            tvMiniTime.setText(
                    "00:00 / " + formatTime(total)
            );

            return;
        }

        handler.postDelayed(this::updateMiniSeekBar, 500);
    }

    private void refreshCurrentFragment() {

        Fragment fragment =
                getSupportFragmentManager()
                        .findFragmentById(R.id.frame_container);

        if (fragment instanceof HomeFragment) {
            ((HomeFragment) fragment).refreshList();

        } else if (fragment instanceof FavoriteFragment) {
            ((FavoriteFragment) fragment).refreshList();
        }
    }

    private String formatTime(int ms) {

        int seconds = ms / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    private void refreshMiniPlayer() {

        tvMiniTitle.setText(MusicManager.currentTitle);
        tvMiniArtist.setText(MusicManager.currentArtist);

        imgMiniCover.setImageResource(
                MusicManager.currentImageRes
        );

        miniSeekBar.setMax(MusicManager.getDuration());

        if (MusicManager.isPlaying()) {
            btnMiniPlayPause.setImageResource(
                    android.R.drawable.ic_media_pause
            );
        } else {
            btnMiniPlayPause.setImageResource(
                    android.R.drawable.ic_media_play
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MusicManager.mediaPlayer != null) {
            miniPlayerLayout.setVisibility(View.VISIBLE);
            refreshMiniPlayer();
            updateMiniSeekBar();
        }
    }
}