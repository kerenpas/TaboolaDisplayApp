package com.example.tabooladisplayapp.data.repo;


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

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.core.Observable;

@Singleton
public class InMemoryCellColorRepositoryImpl implements CellColorRepository {
    private final BehaviorSubject<List<CellColorUpdate>> cellsSubject = BehaviorSubject.create();
    private final Executor io;
    private final Handler main = new Handler(Looper.getMainLooper());

    @Inject
    public InMemoryCellColorRepositoryImpl(@IoExecutor Executor io) {
        this.io = io;
        List<CellColorUpdate> seed = new ArrayList<>();
        for (int i = 0; i < 10; i++) seed.add(new CellColorUpdate(i, 0x00000000)); // transparent
        cellsSubject.onNext(Collections.unmodifiableList(seed));
    }

    @Override
    public Observable<List<CellColorUpdate>> observeCells() {
        return cellsSubject.hide();
    }

    @Override
    public void updateCellColor(CellColorUpdate update) {
        io.execute(() -> {
            List<CellColorUpdate> current = cellsSubject.getValue();
            if (current == null) return;

            List<CellColorUpdate> updated = new ArrayList<>(current);
            // Ensure the list is large enough
            while (updated.size() <= update.getPosition()) {
                updated.add(new CellColorUpdate(updated.size(), 0, false)); // default values
            }
            updated.set(update.getPosition(), update);

            // post on main for thread safety
            main.post(() -> cellsSubject.onNext(Collections.unmodifiableList(updated)));
        });
    }

}
