package com.example.tabooladisplayapp.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.tabooladisplayapp.domain.model.CellColorUpdate;
import com.example.tabooladisplayapp.domain.repository.CellColorRepository;

import java.util.List;

import javax.inject.Inject;

public class ObserveCellsColorAndVisibilityUseCase {
    private final CellColorRepository repo;

    @Inject
    public ObserveCellsColorAndVisibilityUseCase(CellColorRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<CellColorUpdate>> execute() {
        return repo.observeCells();
    }
}
