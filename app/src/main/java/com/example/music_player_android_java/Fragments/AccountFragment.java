package com.example.music_player_android_java.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.music_player_android_java.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountFragment extends Fragment {

    public AccountFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_account,
                container,
                false
        );

        View btnFavoriteBox =
                view.findViewById(R.id.btnFavoriteBox);

        btnFavoriteBox.setOnClickListener(v -> {

            BottomNavigationView nav =
                    getActivity().findViewById(
                            R.id.bottom_navigation
                    );

            nav.setSelectedItemId(R.id.nav_favorite);
        });

        return view;
    }
}