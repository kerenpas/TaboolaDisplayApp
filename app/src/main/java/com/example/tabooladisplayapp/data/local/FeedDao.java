package com.example.tabooladisplayapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.tabooladisplayapp.data.local.entity.FeedEntity;
import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feed_items LIMIT :limit")
    List<FeedEntity> getFeedItems(int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FeedEntity> items);

    @Query("DELETE FROM feed_items")
    void deleteAll();
}
