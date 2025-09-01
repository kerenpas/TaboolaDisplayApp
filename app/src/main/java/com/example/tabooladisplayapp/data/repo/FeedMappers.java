package com.example.tabooladisplayapp.data.repo;

import com.example.tabooladisplayapp.data.local.entity.FeedEntity;
import com.example.tabooladisplayapp.data.remote.dto.FeedDto;
import com.example.tabooladisplayapp.domain.model.FeedItem;
import java.util.Objects;

public class FeedMappers {
    public static FeedItem entityToDomain(FeedEntity entity) {
        return new FeedItem(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getThumbnailUrl()
        );
    }

    public static FeedEntity dtoToEntity(FeedDto dto) {
        String thumbnailUrl = dto.getThumbnailUrl();
        return new FeedEntity(
            dto.getName(),
            dto.getDescription(),
            thumbnailUrl
        );
    }
}
