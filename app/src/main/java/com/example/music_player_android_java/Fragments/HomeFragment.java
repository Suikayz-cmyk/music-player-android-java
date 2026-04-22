package com.example.music_player_android_java.Fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.MainActivity;
import com.example.music_player_android_java.R;
import com.example.music_player_android_java.adapter.SongAdapter;
import com.example.music_player_android_java.data.FavoriteManager;
import com.example.music_player_android_java.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Song> songList;
    SongAdapter adapter;
    MediaPlayer mediaPlayer;

    int currentSongId = -1;
    boolean isPlaying = false;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        songList = new ArrayList<>();

        songList.add(new Song(1,"ハッピーエンド(Happy End)","back number",R.raw.happy_end));
        songList.add(new Song(2,"Lemon","Kenshi Yonezu",R.raw.lemon));
        songList.add(new Song(3,"Pretender","Official HIGE DANDism",R.raw.pretender));
        songList.add(new Song(4,"Anymore","D-LITE",R.raw.anymore));
        songList.add(new Song(5,"Wedding Dress","Taeyang",R.raw.wedding_dress));
        songList.add(new Song(6,"A Fool Of Tears","BIGBANG",R.raw.a_fool_of_tears));

        adapter = new SongAdapter(songList, new SongAdapter.OnSongClickListener() {
            @Override
            public void onPlayClick(Song song) {
                ((MainActivity)getActivity()).playSong(
                        song.getTitle(),
                        song.getArtist(),
                        song.getAudioResId()
                );
            }

            @Override
            public void onFavoriteClick(Song song) {
                FavoriteManager.toggleFavorite(song);
                adapter.notifyDataSetChanged();
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

        adapter.updatePlayingState(currentSongId, isPlaying);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}