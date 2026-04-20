package com.example.music_player_android_java.model;

public class Song {

    String title;
    String artist;
    int audioResId;

    public Song(String title, String artist, int audioResId) {
        this.title = title;
        this.artist = artist;
        this.audioResId = audioResId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getAudioResId() {
        return audioResId;
    }
}