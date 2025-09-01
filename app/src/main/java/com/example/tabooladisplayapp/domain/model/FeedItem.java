package com.example.tabooladisplayapp.domain.model;

import androidx.annotation.NonNull;

public class FeedItem {
    private final Long id;
    private final String name;
    private final String description;
    private final String thumbnailUrl;

    public FeedItem(@NonNull Long id, String name, String description, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    @NonNull
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
}
