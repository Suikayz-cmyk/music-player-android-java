package com.example.music_player_android_java.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music_player_android_java.PhotoViewActivity;
import com.example.music_player_android_java.R;
import com.example.music_player_android_java.model.Song;

import java.util.List;

public class CoverPagerAdapter
        extends RecyclerView.Adapter<CoverPagerAdapter.ViewHolder> {

    private final List<Song> songList;

    public CoverPagerAdapter(List<Song> songList) {
        this.songList = songList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCover;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCover = itemView.findViewById(
                    R.id.imgCoverPager
            );
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.item_cover_pager,
                parent,
                false
        );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Song song = songList.get(position);

        holder.imgCover.setImageResource(
                song.getImageResId()
        );

        holder.imgCover.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    PhotoViewActivity.class
            );

            intent.putExtra(
                    "imageRes",
                    song.getImageResId()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}