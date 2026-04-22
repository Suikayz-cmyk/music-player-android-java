package com.example.music_player_android_java.manager;

import android.content.Context;
import android.media.MediaPlayer;


public class MusicManager {

    public static MediaPlayer mediaPlayer;
    public static int currentImageRes = 0;

    public static String currentTitle = "";
    public static String currentArtist = "";
    public static int currentAudioRes = 0;

    public static void play(Context context,
                            String title,
                            String artist,
                            int audioResId,
                            int imageResId) {

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, audioResId);
        mediaPlayer.start();

        currentTitle = title;
        currentArtist = artist;
        currentAudioRes = audioResId;
        currentImageRes = imageResId;
    }

    public static void toggle() {

        if (mediaPlayer == null) return;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public static boolean isPlaying() {

        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public static int getDuration() {
        if (mediaPlayer == null) return 0;
        return mediaPlayer.getDuration();
    }

    public static int getCurrentPosition() {
        if (mediaPlayer == null) return 0;
        return mediaPlayer.getCurrentPosition();
    }

    public static void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }
}