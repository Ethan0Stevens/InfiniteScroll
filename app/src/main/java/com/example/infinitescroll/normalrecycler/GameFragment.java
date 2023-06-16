package com.example.infinitescroll.normalrecycler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.infinitescroll.R;

public class GameFragment extends Fragment {

    TextView txtGameName, txtGameSummary, txtGameReleaseDate;
    ImageView imgCover;
    Game game;
    public GameFragment(Game game) {
        super(R.layout.game_fragment_item);
        this.game = game;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgCover = view.findViewById(R.id.imgCover);
        txtGameName = view.findViewById(R.id.txtGameName);
        txtGameSummary = view.findViewById(R.id.txtSummary);
        txtGameReleaseDate = view.findViewById(R.id.txtReleaseDate);

        txtGameName.setText(game.getName());
        txtGameSummary.setText(game.getSummary());


        //txtGameReleaseDate.setText(date);

        Glide.with(game.getContext())
                .load(game.getCoverUrl())
                .override(600, 600)
                .fitCenter()
                .into(imgCover);
    }
}
