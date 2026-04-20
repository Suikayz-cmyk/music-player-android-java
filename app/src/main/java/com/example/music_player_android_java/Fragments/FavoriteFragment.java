package com.example.music_player_android_java.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.R;
import com.example.music_player_android_java.adapter.SongAdapter;
import com.example.music_player_android_java.data.FavoriteManager;

public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;

    public FavoriteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerFavorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SongAdapter adapter = new SongAdapter(
                FavoriteManager.favoriteSongs,
                new SongAdapter.OnSongClickListener() {

                    @Override
                    public void onSongClick(com.example.music_player_android_java.model.Song song) {}

                    @Override
                    public void onFavoriteClick(com.example.music_player_android_java.model.Song song) {}
                });

        recyclerView.setAdapter(adapter);

        return view;
    }
}