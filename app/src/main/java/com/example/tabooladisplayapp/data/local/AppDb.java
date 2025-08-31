package com.example.tabooladisplayapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.tabooladisplayapp.data.local.entity.FeedEntity;

@Database(entities = {FeedEntity.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract FeedDao feedDao();
}
