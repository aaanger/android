package com.mirea.kt.ribo.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.ribo.adapter.MuseumAdapter;
import com.mirea.kt.ribo.R;
import com.mirea.kt.ribo.adapter.RecyclerViewInterface;
import com.mirea.kt.ribo.models.Museum;
import com.mirea.kt.ribo.models.MuseumDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<Museum> museums = new ArrayList<>();
    private MuseumAdapter museumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMuseumsFromJSON();

        museumAdapter = new MuseumAdapter(this, museums, this);
        recyclerView.setAdapter(museumAdapter);
    }

    private void loadMuseumsFromJSON() {
        try {
            JSONArray museumsArray = new JSONArray(loadJSONFromAssets(this));
            for (int i = 0; i < museumsArray.length(); i++) {
                JSONObject museum = museumsArray.getJSONObject(i);
                String name = museum.getString("name");
                int imageResId = getResources().getIdentifier(museum.getString("image"), "drawable", getPackageName());

                museums.add(new Museum(name, imageResId));
            }
        } catch (JSONException e) {
            Log.e("JSONError", "Ошибка парсинга JSON", e);
        }
    }

    private String loadJSONFromAssets(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("museums.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onItemClick(int position) {
        Museum selectedMuseum = museums.get(position);
        MuseumDetail museumDetail = getMuseumDetail(selectedMuseum.getName());

        if (museumDetail != null) {
            Intent intent = new Intent(MainPage.this, MuseumDetailPage.class);
            intent.putExtra("name", museumDetail.getName());
            intent.putExtra("image", museumDetail.getImage());
            intent.putExtra("address", museumDetail.getAddress());
            intent.putExtra("phone", museumDetail.getPhone());
            intent.putExtra("website", museumDetail.getWebsite());
            startActivity(intent);
        } else {
            Log.e("MainPage", "Museum details not found for: " + selectedMuseum.getName());
        }
    }

    public MuseumDetail getMuseumDetail(String museumName) {
        try {
            InputStream is = getAssets().open("museums.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("name").equals(museumName)) {
                    return new MuseumDetail(
                            obj.getString("name"),
                            getResources().getIdentifier(obj.getString("image"), "drawable", getPackageName()),
                            obj.getString("address"),
                            obj.getString("phone"),
                            obj.getString("website")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}