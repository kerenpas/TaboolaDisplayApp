package com.example.tabooladisplayapp.domain.usecase;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import com.example.tabooladisplayapp.domain.repository.FeedRepository;
import com.example.tabooladisplayapp.utils.Callback;

import java.util.List;

import javax.inject.Inject;


public class GetFeedUseCase {
    private final FeedRepository feedRepository;

    @Inject
    public GetFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public void invoke(int count, Callback<List<FeedItem>> callback) {
        feedRepository.getFeedItems(count, callback);
    }
}
