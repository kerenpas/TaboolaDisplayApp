package com.example.tabooladisplayapp.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class FeedDto {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName(value = "thumbnailUrl", alternate = {
        "thumbnailurl",
        "thumbnail",
        "thumbnail_url",
        "thubnailurl"
    })
    private String thumbnailUrl;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
