package com.example.infinitescroll.normalrecycler;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.infinitescroll.CustomVolleyRequestQueue;
import com.example.infinitescroll.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NormalRecyclerView extends AppCompatActivity {

    RecyclerView normal_recycler;
    private ArrayList<Game> gameArrayList;
    GameAdapter adapter;
    RequestQueue requestQueue;
    Context context;
    private GameFragmentAdapter gameFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_recycler);

        context = this;

        gameArrayList = new ArrayList<>();
        requestQueue = CustomVolleyRequestQueue.getInstance(this).getRequestQueue();

        test();

        normal_recycler = findViewById(R.id.normal_recycler);
        normal_recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameAdapter(this, gameArrayList);
        normal_recycler.setAdapter(adapter);
        normal_recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        gameFragmentAdapter = new GameFragmentAdapter(getSupportFragmentManager(), getLifecycle());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll("TAG");
        }
    }

    public void test() {
        String url = "https://api.igdb.com/v4/games?fields=id,name,cover,first_release_date,summary&limit=500";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                null,

                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray responses) {
                        Log.i("TAG", "good " + responses.toString());

                        // "I want to iterate though the objects in the array..."
                        for (int i = 0, size = responses.length(); i < size; i++)
                        {

                            try {
                                Log.i(String.valueOf(i), String.valueOf(responses.getJSONObject(i)));

                                JSONObject response = responses.getJSONObject(i);

                                int id = i + 1;
                                String name = id + " : " + response.getString("name");
                                int cover = 0;
                                String url = "";
                                Integer release_date = null;
                                String summary = "";

                                try {
                                    // JSONArray artworks = (JSONArray) response.get("cover");
                                    cover = (int) response.get("cover");
                                } catch (JSONException e) {
                                    e=e;
                                }

                                try {
                                    url = (String) response.get("url");
                                } catch (JSONException e) {
                                    e=e;
                                }

                                try {
                                    release_date = (Integer) response.get("first_release_date");
                                } catch (JSONException e) {
                                    e=e;
                                }

                                try {
                                    summary = (String) response.get("summary");
                                } catch (JSONException e) {
                                    e=e;
                                }

                                Game game = new Game(id, name, cover, url, release_date, summary, context);
                                gameArrayList.add(game);
                                gameFragmentAdapter.addFragment(new GameFragment(game));
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e=e;
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "pas good " + error.toString());
                        test();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fields", "id,name");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Client-ID", "277vd0r8dast4luc7ekg6e0scjephn");
                params.put("Authorization", "Bearer zr6w67kmoyzlkq4k394r7dl4tvjh5u");

                return params;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    @SuppressLint("ResourceType")
    public void openFragment(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager(); // Utilisez getFragmentManager() pour les activités héritant de FragmentActivity

        GameFragment fragment = (GameFragment) gameFragmentAdapter.createFragment(view.getId() - 1);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null); // Permet de revenir en arrière (optionnel)

        fragmentTransaction.commit();
    }
}