package com.example.miguelangelrubiocaballero.a04gamebacklog.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.miguelangelrubiocaballero.a04gamebacklog.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView mGameName;
    public TextView mConsoleName;
    public TextView mGameStatus;
    public TextView mDate;

    public ViewHolder(View itemView) {
        super(itemView);
        mGameName = itemView.findViewById(R.id.game_name);
        mConsoleName = itemView.findViewById(R.id.console_type);
        mGameStatus = itemView.findViewById(R.id.game_status);
        mDate = itemView.findViewById(R.id.date);
    }


}
