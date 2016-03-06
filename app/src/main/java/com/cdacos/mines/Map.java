package com.cdacos.mines;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Map extends AppCompatActivity {
    private int WIDTH = 8;
    private Data data;
    private TextView score;
    private MapAdapter mapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        score = (TextView)findViewById(R.id.score);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int orientation = getResources().getConfiguration().orientation;
        int boardWidthInPixels = orientation == Configuration.ORIENTATION_LANDSCAPE ? dm.heightPixels : dm.widthPixels;

        if (savedInstanceState != null) data = savedInstanceState.getParcelable("data");

        if (data == null) data = new Data(WIDTH);

        mapAdapter = new MapAdapter(data, (int)(boardWidthInPixels * 0.9f), new OnScoreUpdateListener() {
            @Override
            public void update() {
                Map.this.update();
            }
        });

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.map);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, WIDTH));
        recyclerView.setAdapter(mapAdapter);

        update();
    }

    private void update() {
        if (data.hasExploded) {
            Map.this.score.setText("BOOM! You lost :(");
        } else if (Logic.hasWon(data)) {
            Map.this.score.setText("You won!");
        } else {
            Map.this.score.setText(String.format(Locale.ENGLISH, "Mines: %d/%d", data.foundMines, data.totalMines));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("data", data);
    }

    public void startNewGameClick(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }
}
