package com.example.tabooladisplayapp.di;

import com.example.tabooladisplayapp.data.repo.FeedRepositoryImpl;
import com.example.tabooladisplayapp.domain.repository.FeedRepository;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class FeedModule {

    @Binds
    public abstract FeedRepository bindFeedRepository(FeedRepositoryImpl impl);
}
