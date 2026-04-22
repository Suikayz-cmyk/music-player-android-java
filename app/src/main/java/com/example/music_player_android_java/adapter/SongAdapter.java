package com.example.music_player_android_java.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.R;
import com.example.music_player_android_java.data.FavoriteManager;
import com.example.music_player_android_java.manager.MusicManager;
import com.example.music_player_android_java.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private final List<Song> songList;
    private final OnSongClickListener listener;

    private static final String COLOR_GREEN = "#1DB954";
    private static final String COLOR_WHITE = "#FFFFFF";
    private static final String COLOR_RED = "#E53935";
    private static final String COLOR_CARD_NORMAL = "#1E1E1E";
    private static final String COLOR_CARD_ACTIVE = "#2A2A2A";

    public interface OnSongClickListener {
        void onPlayClick(Song song);
        void onFavoriteClick(Song song);
        void onCardClick(Song song);
    }

    public SongAdapter(List<Song> songList, OnSongClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        ImageView imgCover;

        TextView tvTitle;
        TextView tvArtist;

        ImageButton btnPlay;
        ImageButton btnFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardSong);

            imgCover = itemView.findViewById(R.id.imgCover);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);

            btnPlay = itemView.findViewById(R.id.btnPlayPause);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Song song = songList.get(position);

        bindSongInfo(holder, song);
        bindPlayState(holder, song);
        bindFavoriteState(holder, song);
        bindCardState(holder, song);
        bindClickActions(holder, song, position);
    }

    private void bindSongInfo(ViewHolder holder, Song song) {

        holder.imgCover.setImageResource(song.getImageResId());
        holder.tvTitle.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());
    }

    private void bindPlayState(ViewHolder holder, Song song) {

        boolean isCurrentSong =
                MusicManager.currentAudioRes == song.getAudioResId();

        if (isCurrentSong && MusicManager.isPlaying()) {
            holder.btnPlay.setImageResource(
                    android.R.drawable.ic_media_pause
            );
        } else {
            holder.btnPlay.setImageResource(
                    android.R.drawable.ic_media_play
            );
        }

        holder.btnPlay.setColorFilter(
                Color.parseColor(COLOR_GREEN)
        );
    }

    private void bindFavoriteState(ViewHolder holder, Song song) {

        boolean isFavorite = FavoriteManager.isFavorite(song);

        if (isFavorite) {
            holder.btnFavorite.setImageResource(
                    R.drawable.ic_heart_filled
            );

            holder.btnFavorite.setColorFilter(
                    Color.parseColor(COLOR_RED)
            );

        } else {

            holder.btnFavorite.setImageResource(
                    R.drawable.ic_heart_outline
            );

            holder.btnFavorite.setColorFilter(
                    Color.parseColor(COLOR_WHITE)
            );
        }
    }

    private void bindCardState(ViewHolder holder, Song song) {

        boolean isCurrentSong =
                MusicManager.currentAudioRes == song.getAudioResId();

        if (isCurrentSong) {
            holder.cardView.setCardBackgroundColor(
                    Color.parseColor(COLOR_CARD_ACTIVE)
            );
        } else {
            holder.cardView.setCardBackgroundColor(
                    Color.parseColor(COLOR_CARD_NORMAL)
            );
        }
    }

    private void bindClickActions(
            ViewHolder holder,
            Song song,
            int position
    ) {

        holder.itemView.setOnClickListener(v ->
                listener.onCardClick(song)
        );

        holder.btnPlay.setOnClickListener(v ->
                listener.onPlayClick(song)
        );

        holder.btnFavorite.setOnClickListener(v -> {
            listener.onFavoriteClick(song);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}