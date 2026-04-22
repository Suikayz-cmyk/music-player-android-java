package com.example.music_player_android_java.model;

public class Song {

    int id;
    String title, artist;
    int audioResId;

    public Song(int id, String title, String artist, int audioResId) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.audioResId = audioResId;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getAudioResId() { return audioResId; }
}