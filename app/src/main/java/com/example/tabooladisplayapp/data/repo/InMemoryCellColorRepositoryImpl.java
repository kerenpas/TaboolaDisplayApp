package com.example.tabooladisplayapp.data.repo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.os.Handler;
import android.os.Looper;


import com.example.tabooladisplayapp.domain.model.CellColorUpdate;

import com.example.tabooladisplayapp.di.IoExecutor;
import com.example.tabooladisplayapp.domain.repository.CellColorRepository;

@Singleton
public class InMemoryCellColorRepositoryImpl implements CellColorRepository {
    private final MutableLiveData<List<CellColorUpdate>> cellsLive = new MutableLiveData<>(new ArrayList<>());
    private final LiveData<List<CellColorUpdate>> _cellsList = cellsLive;
    private final Executor io;
    private final Handler main = new Handler(Looper.getMainLooper());

    @Inject
    public InMemoryCellColorRepositoryImpl(@IoExecutor Executor io) {
        this.io = io;
        List<CellColorUpdate> seed = new ArrayList<>();
        for (int i = 0; i < 10; i++) seed.add(new CellColorUpdate(i, 0x00000000)); // transparent
        cellsLive.setValue(seed);
    }

    @Override
    public LiveData<List<CellColorUpdate>> observeCells() {
        return _cellsList;
    }

    @Override
    public void updateCellColor(CellColorUpdate update) {
        io.execute(() -> {
            List<CellColorUpdate> current = cellsLive.getValue();
            if (current == null) return;

            List<CellColorUpdate> updated = new ArrayList<>(current);
            // Ensure the list is large enough
            while (updated.size() <= update.getPosition()) {
                updated.add(new CellColorUpdate(updated.size(), 0, false)); // default values
            }
            updated.set(update.getPosition(), update);

            // post on main for LiveData
            main.post(() -> cellsLive.setValue(Collections.unmodifiableList(updated)));
        });
    }

}
