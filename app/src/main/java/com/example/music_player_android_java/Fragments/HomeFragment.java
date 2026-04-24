package com.example.music_player_android_java.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.music_player_android_java.MainActivity;
import com.example.music_player_android_java.PlayerActivity;
import com.example.music_player_android_java.R;
import com.example.music_player_android_java.adapter.CoverPagerAdapter;
import com.example.music_player_android_java.adapter.SongAdapter;
import com.example.music_player_android_java.data.FavoriteManager;
import com.example.music_player_android_java.manager.MusicManager;
import com.example.music_player_android_java.model.Song;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ViewPager2 viewPagerCover;

    private List<Song> songList;
    private SongAdapter adapter;

    public HomeFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );

        initViews(view);
        setupSongData();
        setupBanner();
        setupSongList();

        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recyclerSongs);
        viewPagerCover = view.findViewById(R.id.viewPagerCover);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
    }

    private void setupSongData() {

        songList = new ArrayList<>();

        songList.add(new Song(
                1,
                "ハッピーエンド(Happy End)",
                "back number",
                R.raw.happy_end,
                R.drawable.cover_happy_end
        ));

        songList.add(new Song(
                2,
                "Lemon",
                "Kenshi Yonezu",
                R.raw.lemon,
                R.drawable.cover_lemon
        ));

        songList.add(new Song(
                3,
                "Pretender",
                "Official HIGE DANDism",
                R.raw.pretender,
                R.drawable.cover_pretender
        ));

        songList.add(new Song(
                4,
                "Anymore",
                "D-LITE(from BIGBANG)",
                R.raw.anymore,
                R.drawable.cover_anymore
        ));

        songList.add(new Song(
                5,
                "Wedding Dress",
                "Taeyang",
                R.raw.wedding_dress,
                R.drawable.cover_wedding_dress
        ));

        songList.add(new Song(
                6,
                "A Fool Of Tears",
                "BIGBANG",
                R.raw.a_fool_of_tears,
                R.drawable.cover_a_fool_of_tears
        ));

        // Simpan playlist global untuk next/prev
        MusicManager.setPlaylist(songList);
    }

    private void setupBanner() {

        CoverPagerAdapter coverAdapter =
                new CoverPagerAdapter(songList);

        viewPagerCover.setAdapter(coverAdapter);

        viewPagerCover.setOffscreenPageLimit(3);

        viewPagerCover.setPageTransformer((page, position) -> {

            float scale =
                    0.85f + (1 - Math.abs(position)) * 0.15f;

            page.setScaleY(scale);

            page.setAlpha(
                    0.7f + (1 - Math.abs(position)) * 0.3f
            );
        });
    }

    private void setupSongList() {

        adapter = new SongAdapter(
                songList,
                new SongAdapter.OnSongClickListener() {

                    @Override
                    public void onPlayClick(Song song) {

                        MusicManager.currentIndex =
                                songList.indexOf(song);

                        ((MainActivity) requireActivity())
                                .playSong(
                                        song.getTitle(),
                                        song.getArtist(),
                                        song.getAudioResId(),
                                        song.getImageResId()
                                );

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFavoriteClick(Song song) {

                        FavoriteManager.toggleFavorite(song);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCardClick(Song song) {

                        MusicManager.currentIndex =
                                songList.indexOf(song);

                        ((MainActivity) requireActivity())
                                .playSong(
                                        song.getTitle(),
                                        song.getArtist(),
                                        song.getAudioResId(),
                                        song.getImageResId()
                                );

                        Intent intent =
                                new Intent(
                                        getActivity(),
                                        PlayerActivity.class
                                );

                        startActivity(intent);
                    }
                }
        );

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void refreshList() {

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}