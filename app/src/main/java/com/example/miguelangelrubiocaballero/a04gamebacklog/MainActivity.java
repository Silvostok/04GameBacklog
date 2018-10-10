package com.example.miguelangelrubiocaballero.a04gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.miguelangelrubiocaballero.a04gamebacklog.Adapters.GameAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private List<Games> mGames = new ArrayList<>();
    static AppDatabase db;


    public final static int TASK_GET_ALL_REMINDERS = 0;
    public final static int TASK_DELETE_REMINDER = 1;
    public final static int TASK_UPDATE_REMINDER = 2;
    public final static int TASK_INSERT_REMINDER = 3;

    @BindView(R.id.add_game_floating_button)
    FloatingActionButton mFloatingButton;
    @BindView(R.id.gameList)
    RecyclerView mGameList;

    private GameAdapter mAdapter;

    static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        db = AppDatabase.getInstance(this);
        new GameAsyncTask(TASK_GET_ALL_REMINDERS).execute();
        mGameList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mGameList.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        new GameAsyncTask(TASK_DELETE_REMINDER).execute(mGames.get(position));
                        mAdapter.notifyItemRemoved(position);
                    }


                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGameList);






    }



    @OnClick(R.id.add_game_floating_button)
    public void goToAddPage() {
        startActivityForResult(new Intent(MainActivity.this, AddGameActivity.class), REQUEST_CODE);

    }


    void onReminderDbUpdated(List list) {
        mGames = list;
        updateUI();

    }

    private void updateUI() {
//        mGames = db.reminderDao().getAllGames();
        if (mAdapter == null) {
            mAdapter = new GameAdapter(this, mGames);
            mGameList.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mGames);
        }

    }



    public class GameAsyncTask extends AsyncTask<Games, Void, List> {

        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Games... games) {
            switch (taskCode) {
                case TASK_DELETE_REMINDER:
                    db.reminderDao().deleteGame(games[0]);
                    break;
                case TASK_UPDATE_REMINDER:
                    db.reminderDao().updateGame(games[0]);
                    break;
                case TASK_INSERT_REMINDER:
                    db.reminderDao().insertGame(games[0]);
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db.reminderDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onReminderDbUpdated(list);
        }


    }
}
