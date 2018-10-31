package com.example.miguelangelrubiocaballero.a04gamebacklog.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miguelangelrubiocaballero.a04gamebacklog.Games;
import com.example.miguelangelrubiocaballero.a04gamebacklog.R;

import java.util.Calendar;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{

    private List<Games> mGames;
    private Context mContext;

    public GameAdapter(Context mContext, List<Games> mGames) {
        this.mContext = mContext;
        this.mGames = mGames;
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.backlog_card, parent, false);
        return new GameAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH) + 1;
        int cYear = calendar.get(Calendar.YEAR);
        Games games = mGames.get(position);
        holder.mGameName.setText(games.mGameTitle);
        holder.mConsoleName.setText(games.mPlatformTitle);
        holder.mGameStatus.setText(games.mStatus);
        holder.mDate.setText(cDay + "/ " + cMonth + "/ " + cYear);

    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public void swapList(List<Games> newList) {
        mGames = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

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
}
