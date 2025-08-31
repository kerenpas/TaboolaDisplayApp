package com.example.tabooladisplayapp.di;

import com.example.tabooladisplayapp.data.repo.FeedRepositoryImpl;
import com.example.tabooladisplayapp.domain.usecase.GetFeedUseCase;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract GetFeedUseCase bindFeedRepository(FeedRepositoryImpl impl);
}
