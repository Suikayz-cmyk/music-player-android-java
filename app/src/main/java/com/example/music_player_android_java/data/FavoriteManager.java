package com.example.music_player_android_java.data;

import com.example.music_player_android_java.model.Song;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    public static List<Song> favoriteSongs = new ArrayList<>();

    public static boolean isFavorite(Song song) {
        for (Song item : favoriteSongs) {
            if (item.getId() == song.getId()) {
                return true;
            }
        }
        return false;
    }

    public static void toggleFavorite(Song song) {

        for (Song item : favoriteSongs) {
            if (item.getId() == song.getId()) {
                favoriteSongs.remove(item);
                return;
            }
        }

        favoriteSongs.add(song);
    }
}