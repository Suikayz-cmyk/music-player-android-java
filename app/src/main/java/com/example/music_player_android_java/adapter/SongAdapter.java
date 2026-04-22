package com.example.music_player_android_java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.R;
import com.example.music_player_android_java.data.FavoriteManager;
import com.example.music_player_android_java.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    List<Song> songList;
    OnSongClickListener listener;

    int currentPlayingId = -1;
    boolean isPlaying = false;

    public interface OnSongClickListener {
        void onPlayClick(Song song);
        void onFavoriteClick(Song song);
    }

    public SongAdapter(List<Song> songList, OnSongClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvArtist;
        ImageButton btnPlay, btnFavorite;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            btnPlay = itemView.findViewById(R.id.btnPlayPause);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Song song = songList.get(position);

        holder.tvTitle.setText(song.getTitle());
        holder.tvArtist.setText(song.getArtist());

        if (currentPlayingId == song.getId() && isPlaying) {
            holder.btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            holder.btnPlay.setImageResource(android.R.drawable.ic_media_play);
        }

        if (FavoriteManager.isFavorite(song)) {
            holder.btnFavorite.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_heart_outline);
        }
        if (FavoriteManager.isFavorite(song)) {
            holder.btnFavorite.setColorFilter(
                    android.graphics.Color.parseColor("#E53935")
            );
        } else {
            holder.btnFavorite.setColorFilter(
                    android.graphics.Color.parseColor("#FFFFFF")
            );
        }


        holder.btnPlay.setOnClickListener(v -> {
            listener.onPlayClick(song);
        });
        holder.btnPlay.setColorFilter(
                android.graphics.Color.parseColor("#1DB954")
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

    public void updatePlayingState(int songId, boolean playing) {
        currentPlayingId = songId;
        isPlaying = playing;
        notifyDataSetChanged();
    }
}