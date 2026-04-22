package com.example.music_player_android_java.Fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.MainActivity;
import com.example.music_player_android_java.PlayerActivity;
import com.example.music_player_android_java.R;
import com.example.music_player_android_java.adapter.SongAdapter;
import com.example.music_player_android_java.data.FavoriteManager;
import com.example.music_player_android_java.model.Song;

public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    SongAdapter adapter;
    MediaPlayer mediaPlayer;

    int currentSongId = -1;
    boolean isPlaying = false;

    public FavoriteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SongAdapter(FavoriteManager.favoriteSongs,
                new SongAdapter.OnSongClickListener() {

                    @Override
                    public void onPlayClick(Song song) {
                        adapter.notifyDataSetChanged();
                        ((MainActivity)getActivity()).playSong(
                                song.getTitle(),
                                song.getArtist(),
                                song.getAudioResId(),
                                song.getImageResId()
                        );
                    }

                    @Override
                    public void onFavoriteClick(Song song) {
                        FavoriteManager.toggleFavorite(song);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCardClick(Song song) {

                        ((MainActivity)getActivity()).playSong(
                                song.getTitle(),
                                song.getArtist(),
                                song.getAudioResId(),
                                song.getImageResId()
                        );

                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        startActivity(intent);
                    }
                });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void playSong(Song song) {

        if (currentSongId == song.getId() && mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                mediaPlayer.start();
                isPlaying = true;
            }

        } else {

            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(getContext(), song.getAudioResId());
            mediaPlayer.start();

            currentSongId = song.getId();
            isPlaying = true;
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}