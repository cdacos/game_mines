package com.cdacos.mines;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

/**
 * Created by carlos on 06/03/2016.
 */
public class MapAdapter extends RecyclerView.Adapter<MapViewHolder> {
    private Data data;
    private OnScoreUpdateListener scoreUpdateListener;
    public int itemWidthInPixels;

    public MapAdapter(Data data, int boardWidthInPixels, OnScoreUpdateListener scoreUpdateListener) {
        this.data = data;
        this.itemWidthInPixels = boardWidthInPixels / data.boardColumns;
        this.scoreUpdateListener = scoreUpdateListener;
    }

    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item, parent, false);
        return new MapViewHolder(view, itemWidthInPixels, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logic.toggleFlaggedState(data, (int) v.getTag());
                    update();
                }
            },
            new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Logic.reveal(data, (int) v.getTag());
                    update();
                    return true;
                }
            });
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, int position) {
        boolean hasMine = data.map[position] == Logic.HAS_MINE;
        boolean isRevealed = data.revealed[position];
        State flag = data.flagged[position];

        if (isRevealed && (
                (hasMine && flag == State.UNKNOWN) ||
                (!hasMine && flag != State.UNKNOWN))) {
            holder.label.setBackgroundColor(Color.RED);
        }

        holder.button.setTag(position);
        holder.button.setVisibility(isRevealed ? View.GONE : View.VISIBLE);
        holder.button.setText(flag == State.FLAGGED ? "\u2691" : flag == State.TENTATIVE ? "\u2047" : "");

        holder.label.setVisibility(isRevealed ? View.VISIBLE : View.GONE);
        holder.label.setText(String.format(Locale.ENGLISH, "%s", hasMine ? "\uD83D\uDCA3" : data.map[position] == 0 ? "" : data.map[position]));
    }

    @Override
    public int getItemCount() {
        return data.map.length;
    }

    private void update() {
        scoreUpdateListener.update();
        notifyDataSetChanged();
    }
}
