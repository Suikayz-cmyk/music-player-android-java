package com.example.music_player_android_java.Fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.PlayerActivity;
import com.example.music_player_android_java.R;
import com.example.music_player_android_java.adapter.SongAdapter;
import com.example.music_player_android_java.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    List<Song> songList;
    MediaPlayer mediaPlayer;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        songList = new ArrayList<>();

        songList.add(new Song("ハッピーエンド(Happy End)", "back number", R.raw.happy_end));
        songList.add(new Song("Lemon", "米津玄師 Kenshi Yonezu", R.raw.lemon));
        songList.add(new Song("Pretender", "Official髭男dism", R.raw.pretender));
        songList.add(new Song("Anymore", "D-LITE (from BIGBANG)", R.raw.anymore));
        songList.add(new Song("Wedding Dress", "Taeyang", R.raw.wedding_dress));
        songList.add(new Song("눈물뿐인 바보(A FOOL OF TEARS)", "BIGBANG", R.raw.a_fool_of_tears));

        SongAdapter adapter = new SongAdapter(songList, song -> openPlayer(song));

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void playSong(Song song) {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(getContext(), song.getAudioResId());
        mediaPlayer.start();

        Toast.makeText(getContext(),
                "Playing: " + song.getTitle(),
                Toast.LENGTH_SHORT).show();
    }

    private void openPlayer(Song song) {

        Intent intent = new Intent(getActivity(), PlayerActivity.class);

        intent.putExtra("title", song.getTitle());
        intent.putExtra("artist", song.getArtist());
        intent.putExtra("audio", song.getAudioResId());

        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}