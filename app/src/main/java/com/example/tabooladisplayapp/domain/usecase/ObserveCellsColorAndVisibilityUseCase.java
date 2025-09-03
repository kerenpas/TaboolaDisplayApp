package com.example.tabooladisplayapp.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.tabooladisplayapp.domain.model.CellColorUpdate;
import com.example.tabooladisplayapp.domain.repository.CellColorRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ObserveCellsColorAndVisibilityUseCase {
    private final CellColorRepository repo;

    @Inject
    public ObserveCellsColorAndVisibilityUseCase(CellColorRepository repo) {
        this.repo = repo;
    }

    public Observable<List<CellColorUpdate>> execute() {
        return repo.observeCells();
    }
}
