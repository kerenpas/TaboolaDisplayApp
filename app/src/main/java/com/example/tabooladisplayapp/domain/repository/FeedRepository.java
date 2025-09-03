package com.example.tabooladisplayapp.domain.repository;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import com.example.tabooladisplayapp.utils.Callback;


import java.util.List;

public interface FeedRepository {

    void getFeedItems(int count, Callback<List<FeedItem>> callback);
}
