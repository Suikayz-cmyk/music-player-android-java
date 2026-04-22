package com.example.music_player_android_java;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.os.Handler;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.music_player_android_java.Fragments.AccountFragment;
import com.example.music_player_android_java.Fragments.FavoriteFragment;
import com.example.music_player_android_java.Fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    LinearLayout miniPlayerLayout;
    TextView tvMiniTitle, tvMiniArtist;
    ImageButton btnMiniPlayPause;

    MediaPlayer mediaPlayer;

    SeekBar miniSeekBar;
    Handler handler = new Handler();

    String currentTitle = "";
    String currentArtist = "";
    int currentAudioRes = 0;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miniSeekBar = findViewById(R.id.miniSeekBar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        miniPlayerLayout = findViewById(R.id.miniPlayerLayout);
        miniPlayerLayout.setOnClickListener(v -> {

            Intent intent = new Intent(this, PlayerActivity.class);

            intent.putExtra("title", currentTitle);
            intent.putExtra("artist", currentArtist);
            intent.putExtra("audio", currentAudioRes);

            startActivity(intent);
        });
        tvMiniTitle = findViewById(R.id.tvMiniTitle);
        tvMiniArtist = findViewById(R.id.tvMiniArtist);
        btnMiniPlayPause = findViewById(R.id.btnMiniPlayPause);

        loadFragment(new HomeFragment());

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

        btnMiniPlayPause.setOnClickListener(v -> {

            if (mediaPlayer == null) return;

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnMiniPlayPause.setImageResource(
                        android.R.drawable.ic_media_play
                );
            } else {
                mediaPlayer.start();
                btnMiniPlayPause.setImageResource(
                        android.R.drawable.ic_media_pause
                );
            }
        });
    }

    public void playSong(String title, String artist, int audioResId) {

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, audioResId);
        mediaPlayer.start();

        currentTitle = title;
        currentArtist = artist;
        currentAudioRes = audioResId;

        tvMiniTitle.setText(title);
        tvMiniArtist.setText(artist);

        miniPlayerLayout.setVisibility(LinearLayout.VISIBLE);

        btnMiniPlayPause.setImageResource(
                android.R.drawable.ic_media_pause
        );

        miniSeekBar.setMax(mediaPlayer.getDuration());

        updateMiniSeekBar();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    private void updateMiniSeekBar() {

        if (mediaPlayer == null) return;

        miniSeekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            handler.postDelayed(this::updateMiniSeekBar, 500);
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