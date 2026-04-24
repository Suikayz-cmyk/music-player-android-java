package com.example.music_player_android_java.manager;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.music_player_android_java.model.Song;

import java.util.List;

public class MusicManager {

    // Media player instance
    public static MediaPlayer mediaPlayer;

    // Current playing song data
    public static String currentTitle = "";
    public static String currentArtist = "";
    public static int currentAudioRes = 0;
    public static int currentImageRes = 0;

    // Playlist state
    public static List<Song> playlist;
    public static int currentIndex = 0;

    public static void play(Context context,
                            String title,
                            String artist,
                            int audioResId,
                            int imageResId) {

        releasePlayer();

        mediaPlayer = MediaPlayer.create(context, audioResId);

        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        currentTitle = title;
        currentArtist = artist;
        currentAudioRes = audioResId;
        currentImageRes = imageResId;
    }

    public static void playSong(Context context, Song song) {

        if (song == null) return;

        play(
                context,
                song.getTitle(),
                song.getArtist(),
                song.getAudioResId(),
                song.getImageResId()
        );
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

    public static void setPlaylist(List<Song> songs) {
        playlist = songs;
    }

    public static void next(Context context) {

        if (playlist == null || playlist.isEmpty()) return;

        currentIndex++;

        if (currentIndex >= playlist.size()) {
            currentIndex = 0;
        }

        playSong(context, playlist.get(currentIndex));
    }

    public static void prev(Context context) {

        if (playlist == null || playlist.isEmpty()) return;

        currentIndex--;

        if (currentIndex < 0) {
            currentIndex = playlist.size() - 1;
        }

        playSong(context, playlist.get(currentIndex));
    }

    public static void releasePlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}