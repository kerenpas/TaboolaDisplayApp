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
import com.example.tabooladisplayapp.data.repo.InMemoryCellColorRepositoryImpl;
import com.example.tabooladisplayapp.domain.usecase.UpdateCellColorUseCase;

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

    @Provides
    @Singleton
    public static UpdateCellColorUseCase provideUpdateCellColorUseCase(CellColorRepository repository) {
        return new UpdateCellColorUseCase(repository);
    }
}
