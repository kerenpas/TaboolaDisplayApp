package com.example.tabooladisplayapp.data.repo;

import android.os.Handler;
import android.os.Looper;
import com.example.tabooladisplayapp.data.local.FeedDao;
import com.example.tabooladisplayapp.data.local.entity.FeedEntity;
import com.example.tabooladisplayapp.data.remote.FeedApi;
import com.example.tabooladisplayapp.data.remote.dto.FeedDto;
import com.example.tabooladisplayapp.domain.model.FeedItem;
import com.example.tabooladisplayapp.domain.usecase.GetFeedUseCase;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;

@Singleton
public class FeedRepositoryImpl implements GetFeedUseCase {
    private final FeedApi api;
    private final FeedDao dao;
    private final Executor ioExecutor;
    private final Executor mainExecutor;
    private List<FeedItem> cachedItems;

    @Inject
    public FeedRepositoryImpl(FeedApi api, FeedDao dao) {
        this.api = api;
        this.dao = dao;
        this.ioExecutor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        this.mainExecutor = command -> mainHandler.post(command);
    }

    @Override
    public void execute(int count, Callback callback) {
        // First emit from cache if available
        ioExecutor.execute(() -> {
            List<FeedEntity> cachedEntities = dao.getFeedItems(count);
            if (!cachedEntities.isEmpty()) {
                cachedItems = cachedEntities.stream()
                    .map(FeedMappers::entityToDomain)
                    .collect(Collectors.toList());
                mainExecutor.execute(() -> callback.onResult(cachedItems));
            }

            // Then fetch from network
            try {
                Response<List<FeedDto>> response = api.getFeed().execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<FeedEntity> entities = response.body().stream()
                        .map(FeedMappers::dtoToEntity)
                        .collect(Collectors.toList());

                    dao.deleteAll();
                    dao.insertAll(entities);

                    List<FeedEntity> freshEntities = dao.getFeedItems(count);
                    List<FeedItem> items = freshEntities.stream()
                        .map(FeedMappers::entityToDomain)
                        .collect(Collectors.toList());

                    mainExecutor.execute(() -> callback.onResult(items));
                } else {
                    mainExecutor.execute(() ->
                        callback.onError(new Exception("Network request failed"))
                    );
                }
            } catch (Exception e) {
                mainExecutor.execute(() -> callback.onError(e));
            }
        });
    }
}
