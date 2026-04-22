package com.example.music_player_android_java;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

    ImageButton btnMiniPlayPause;

    SeekBar miniSeekBar;

    Handler handler = new Handler();

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

        loadFragment(new HomeFragment());
    }

    private void initViews() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        miniPlayerLayout = findViewById(R.id.miniPlayerLayout);

        tvMiniTitle = findViewById(R.id.tvMiniTitle);
        tvMiniArtist = findViewById(R.id.tvMiniArtist);
        tvMiniTime = findViewById(R.id.tvMiniTime);

        btnMiniPlayPause = findViewById(R.id.btnMiniPlayPause);

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

    public void playSong(String title, String artist, int audioResId) {

        MusicManager.play(this, title, artist, audioResId);

        currentTitle = title;
        currentArtist = artist;
        currentAudioRes = audioResId;

        tvMiniTitle.setText(title);
        tvMiniArtist.setText(artist);

        miniPlayerLayout.setVisibility(LinearLayout.VISIBLE);

        btnMiniPlayPause.setImageResource(
                android.R.drawable.ic_media_pause
        );

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

        if (MusicManager.isPlaying()) {
            handler.postDelayed(this::updateMiniSeekBar, 500);
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
}