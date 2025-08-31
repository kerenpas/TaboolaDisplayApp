package com.example.tabooladisplayapp.domain.model;

import androidx.annotation.NonNull;

public class FeedItem {
    private final String key;
    private final String name;
    private final String description;
    private final String thumbnailUrl;

    public FeedItem(@NonNull String key, String name, String description, String thumbnailUrl) {
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
