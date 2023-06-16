package com.example.infinitescroll.normalrecycler;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.infinitescroll.CustomVolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private final int id;
    private final String name;
    private final int cover;
    private String coverUrl = "";
    private final String url;
    private final Context context;
    private final Integer release_date;
    private final String summary;

    public Game(int id, String name, int cover, String url, Integer release_date, String summary, Context context) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.url = url;
        this.release_date = release_date;
        this.summary = summary;
        this.context = context;

        this.apiGetCoverUrl();
    }

    public String getReleaseDate() {
        return String.valueOf(release_date);
    }

    public String getSummary() {
        return summary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCover() {
        return cover;
    }

    public String getUrl() {
        return url;
    }

    public String getCoverUrl() {
        coverUrl = coverUrl.replace("thumb", "720p");
        return "https:" + coverUrl;
    }

    public Context getContext() {
        return context;
    }

    private void apiGetCoverUrl() {
        coverUrl = "//vglist.co/packs/media/images/no-cover-369ad8f0ea82dde5923c942ba1a26482.png";
        if (getCover() != 0) {
            String url = "https://api.igdb.com/v4/covers/"+ this.getCover() +"?fields=url";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray responses) {

                            Log.i("TAG", "good " + responses.toString());
                            try {
                                coverUrl = (String) responses.getJSONObject(0).get("url");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG", "pas good " + error.toString());
                            apiGetCoverUrl();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Client-ID", "277vd0r8dast4luc7ekg6e0scjephn");
                    params.put("Authorization", "Bearer zr6w67kmoyzlkq4k394r7dl4tvjh5u");

                    return params;
                }
            };

            RequestQueue requestQueue = CustomVolleyRequestQueue.getInstance(context).getRequestQueue();
            requestQueue.add(jsonArrayRequest);
        }
    }
}
