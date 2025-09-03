package com.example.tabooladisplayapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CellColorDao {
    @Query("SELECT * FROM cellColor")
    List<CellColorEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CellColorEntity cellColor);

    @Update
    void update(CellColorEntity cellColor);

    @Query("UPDATE cellColor SET color = :color, isVisible = :isVisible WHERE position = :position")
    void updateColorAndVisibility(int position, int color, boolean isVisible);
}

