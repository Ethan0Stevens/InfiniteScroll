package com.example.infinitescroll.normalrecycler;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.infinitescroll.CustomVolleyRequestQueue;
import com.example.infinitescroll.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private Context context;
    private ArrayList<Game> games;

    public GameAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
    }


    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.game_layout_item, parent, false);

        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.GameHolder holder, int position) {
        Game game = games.get(position);
        holder.SetDetails(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GameHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView imgCover;

        public GameHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgCover = itemView.findViewById(R.id.imgCover);
        }

        void SetDetails(Game game) {
            txtName.setText(game.getName());
            imgCover.setId(game.getId());

            Glide.with(game.getContext())
                    .load(game.getCoverUrl())
                    .override(500, 500)
                    .fitCenter()
                    .into(imgCover);
        }
    }
}
