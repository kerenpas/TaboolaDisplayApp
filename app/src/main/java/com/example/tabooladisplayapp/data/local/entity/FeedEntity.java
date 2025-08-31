package com.example.tabooladisplayapp.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feed_items")
public class FeedEntity {
    @PrimaryKey
    @NonNull
    private String key;
    private String name;
    private String description;
    private String thumbnailUrl;

    public FeedEntity(@NonNull String key, String name, String description, String thumbnailUrl) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    @NonNull
    public String getKey() { return key; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}
