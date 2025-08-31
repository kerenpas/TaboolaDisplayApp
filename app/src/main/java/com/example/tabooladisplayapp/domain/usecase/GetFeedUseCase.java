package com.example.tabooladisplayapp.domain.usecase;

import com.example.tabooladisplayapp.domain.model.FeedItem;
import java.util.List;

public interface GetFeedUseCase {
    interface Callback {
        void onResult(List<FeedItem> items);
        void onError(Throwable error);
    }

    void execute(int count, Callback callback);
}
