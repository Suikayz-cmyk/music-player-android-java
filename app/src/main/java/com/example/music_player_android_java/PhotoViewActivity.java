package com.example.music_player_android_java;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;

public class PhotoViewActivity extends AppCompatActivity {

    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photoView = findViewById(R.id.photoView);

        int imageRes = getIntent().getIntExtra(
                "imageRes",
                0
        );

        photoView.setImageResource(imageRes);
    }
}