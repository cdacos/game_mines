package com.cdacos.mines;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by carlos on 06/03/2016.
 */
public class MapViewHolder extends RecyclerView.ViewHolder {
    public TextView label;
    public Button button;

    public MapViewHolder(View itemView, int itemWidth, View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
        super(itemView);
        this.label = (TextView)itemView.findViewById(R.id.map_item_label);
        this.button = (Button)itemView.findViewById(R.id.map_item_button);
        this.button.setOnClickListener(clickListener);
        this.button.setOnLongClickListener(longClickListener);

        FrameLayout container = (FrameLayout)itemView.findViewById(R.id.map_item_container);
        ViewGroup.LayoutParams lp = container.getLayoutParams();
        lp.width = itemWidth;
        lp.height = itemWidth;
        container.setLayoutParams(lp);
    }
}
