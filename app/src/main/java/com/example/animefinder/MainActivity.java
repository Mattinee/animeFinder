package com.example.animefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;
    private ArrayList<AnimeItem> mAnimeList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAnimeList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        Button btn = (Button) findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.queryText);
                String query = et.getText().toString();
                parseJSON(query);
                et.setText("");
            }
        });
    }

    private void parseJSON(String query) {
        if(mAnimeList != null) {
            mAnimeList.clear();
        }
        String url = "https://api.jikan.moe/v4/anime?q=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    closeKeyboard();
                    JSONArray array = response.getJSONArray("data");

                    for(int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        String title = data.getString("title_english");
                        if(title == "null")
                        {
                            title = data.getString("title");
                        }
                        String synopsis = data.getString("synopsis");
                        JSONObject images = data.getJSONObject("images");
                        JSONObject webp = images.getJSONObject("webp");
                        String imageURL = webp.getString("image_url");

                        mAnimeList.add(new AnimeItem(imageURL, title, synopsis));
                    }

                    mItemAdapter = new ItemAdapter(MainActivity.this, mAnimeList);
                    mRecyclerView.setAdapter(mItemAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mAnimeList.size() == 0)
                {
                    Toast.makeText(getBaseContext(), "No matches", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if( view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}