package com.example.tabooladisplayapp.di;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import com.example.tabooladisplayapp.domain.repository.CellColorRepository;
import com.example.tabooladisplayapp.data.repo.InMemoryCellColorRepositoryImpl;;

@Module
@InstallIn(SingletonComponent.class)
public abstract class CellColorModule {

    @Binds
    @Singleton
    public abstract CellColorRepository bindCellColorRepository(InMemoryCellColorRepositoryImpl impl);

    @Provides
    @Singleton
    @IoExecutor
    public static Executor provideIoExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
