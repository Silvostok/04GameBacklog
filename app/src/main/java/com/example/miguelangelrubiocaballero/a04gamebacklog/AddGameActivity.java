package com.example.miguelangelrubiocaballero.a04gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddGameActivity extends AppCompatActivity {

    private static final String TAG = AddGameActivity.class.getSimpleName();
    static AppDatabase db;
    private String mSelectedStatus;

    //find and automatically cast the corresponding view in the layout
    @BindView(R.id.txtTitle)
    EditText mGameTitle;
    @BindView(R.id.txtPlatform)
    EditText mGamePlatform;
    @BindView(R.id.txtNotes)
    EditText mGameNotes;
    @BindView(R.id.status_spinner)
    Spinner mStatusSpinner;

    public final static int TASK_INSERT_GAME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //bind all views on this activity
        ButterKnife.bind(this);
        db = AppDatabase.getInstance(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_of_game, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(adapter);

        // save button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GamesAsyncTask(TASK_INSERT_GAME).execute(new Games(mGameTitle.getText().toString(), mGamePlatform.getText().toString(), mGameNotes.getText().toString(), mStatusSpinner.getSelectedItem().toString()));
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });
    }

    public static class GamesAsyncTask extends AsyncTask<Games, Void, List> {

        private int taskCode;

        GamesAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Games... games) {
            switch (taskCode) {
                case TASK_INSERT_GAME:
                    db.gameDao().insertGame(games[0]);
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db.gameDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);

        }

    }

}
