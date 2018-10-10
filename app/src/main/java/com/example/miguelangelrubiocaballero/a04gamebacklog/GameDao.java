package com.example.miguelangelrubiocaballero.a04gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    public List<Games> getAllGames();

    @Insert
    public void insertGame(Games games);

    @Delete
    public void deleteGame(Games games);

    @Update
    public void updateGame(Games games);

}
